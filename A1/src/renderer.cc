#include "renderer.h"
#include "config.h"

#ifdef DEBUG
#include "func.h"
#include <iostream>
#include <sstream>
#endif
using namespace std;

Renderer::Renderer(Game &go, XInfo &xinfo) :
	dim(DEFAULT_WIDTH > xinfo.dwidth() ? xinfo.dwidth() : DEFAULT_WIDTH,
		DEFAULT_HEIGHT > xinfo.dheight() ? xinfo.dheight() : DEFAULT_HEIGHT),
	focus(0),
	focus_bound_low(0),
	focus_bound_high(0),
	show_splash(true)
{
	update_attributes(go, xinfo, DEFAULT_WIDTH, DEFAULT_HEIGHT);
}

void Renderer::update_attributes(Game &go, XInfo &xinfo, unsigned int new_width, unsigned int new_height){
	dim.first = new_width > xinfo.dwidth() ? xinfo.dwidth() : new_width;
	dim.second = new_height > xinfo.dheight() ? xinfo.dheight() : new_height;
	resize_factor = (float)new_width/ DEFAULT_WIDTH;
	final_blockside_len = (float)resize_factor * BLOCK_SIDE_LEN;

	xinfo.new_pixmap(XInfo::GAME_SCREEN,dim);
	go.player.draw(*this,xinfo);

#ifdef DEBUG
	cout << "resize_factor:"<< resize_factor;
	cout << " new dim: ";
	print_pair(dim);
#endif
	//final_blockside_len = dim.second / YBLOCK_NUM;

#ifdef DEBUG
	{
		using namespace std;
		cout << "display:";
		print_pair(xinfo.dwidth(),xinfo.dheight());
		cout << "game:";
		print_pair(dim);
	}
#endif
}

void Renderer::repaint(Game &go, XInfo &xinfo){
	recalculate_focus_bound();

	// clean canvas
	XFillRectangle(xinfo.display,
		xinfo.pixmap[XInfo::GAME_SCREEN],
		xinfo.gc[XInfo::INVERSE_BACKGROUND],
		0, 0, dim.first, dim.second);

	//go.player.draw(*this,xinfo);
	XCopyArea(xinfo.display, xinfo.pixmap[XInfo::PPLAYER],
		xinfo.pixmap[XInfo::GAME_SCREEN],  xinfo.gc[XInfo::DEFAULT],
		0, 0, 100, 100, go.player.getx() * resize_factor - focus, go.player.gety() * resize_factor);
	

	/* structures are stored in a 2D array of char */
	for (int x = focus_bound_low; x < focus_bound_high &&x < XBLOCK_NUM; x++){
		for (int y = 0; y < YBLOCK_NUM; y++){
			if (!go.structure_map[x][y]) continue;
			draw_structure(go, xinfo, x, y);
		}
	}

	for (int x = focus_bound_low, y; x < focus_bound_high &&x < XBLOCK_NUM; x++){
		y = go.cannon_height_map[x];
		if (y == NO_CANNON) continue;
		draw_cannon(go, xinfo, x, y);
	}

	for (auto it = go.missiles.begin(), end = go.missiles.end(); it != end; it++){
		if (!within_focus_x(it->getx() / final_blockside_len, 
			it->gety() / final_blockside_len, MISSILE_WIDTH)) continue;
		it->draw(*this,xinfo);
	}
	XCopyArea(xinfo.display, xinfo.pixmap[XInfo::GAME_SCREEN],
		xinfo.window,  xinfo.gc[XInfo::DEFAULT],
		0, 0, dim.first, dim.second, 0, 0);
	XFlush(xinfo.display);
}

bool Renderer::within_focus_x(int x, int y, int width){
	return x >= focus_bound_low 
		&& (x+width*resize_factor) <= focus_bound_high
		&& y > 0;
}

void Renderer::recalculate_focus_bound(){
	focus += SCROLL_FACTOR;
	focus_bound_low = (focus - final_blockside_len) / final_blockside_len;
	focus_bound_high = (dim.first + focus + final_blockside_len) / final_blockside_len;
}
