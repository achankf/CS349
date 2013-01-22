#include "renderer.h"
#include "config.h"
#include "func.h"

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
	// make sure the width is in bound
	if (new_width > xinfo.dwidth()){
		dim.first = xinfo.dwidth();
	} else if (new_width < DEFAULT_WIDTH){
		dim.first = DEFAULT_WIDTH;
	} else {
		dim.first = new_width;
	}
	// make sure the height is in bound
	if (new_height > xinfo.dheight()){
		dim.second = xinfo.dheight();
	} else if (new_height < DEFAULT_HEIGHT){
		dim.second = DEFAULT_HEIGHT;
	} else {
		dim.second = new_height;
	}

	// figure out the new size
	final_blockside_len = previous_even(dim.second/ YBLOCK_NUM);
	// player dimension
	player_dim.first = previous_even(dim.second / PLAYER_WIDTH_PROP);
	player_dim.second = previous_even(dim.second / PLAYER_HEIGHT_PROP);
	// missile dimension
	missile_dim.first = previous_even(dim.second / MISSILE_WIDTH_PROP);
	missile_dim.second = previous_even(dim.second / MISSILE_HEIGHT_PROP);

	xinfo.new_pixmap(XInfo::GAME_SCREEN,dim);
	redraw_player(xinfo);
	redraw_missile(xinfo);
	redraw_cannon(xinfo);
	redraw_structure(xinfo);

#ifdef DEBUG
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
	Display *display = xinfo.display;
	GC gc = xinfo.gc[XInfo::DEFAULT];
	Pixmap pixmap = xinfo.pixmap[XInfo::GAME_SCREEN];

	recalculate_focus_bound();

	// clean canvas
	XFillRectangle(display, pixmap,
		xinfo.gc[XInfo::INVERSE_BACKGROUND],
		0, 0, dim.first, dim.second);

	//go.player.draw(*this,xinfo);
	XCopyArea(display, xinfo.pixmap[XInfo::PPLAYER],
		pixmap, gc, 0, 0, 
		player_dim.first, player_dim.second,
		go.player.getx() - focus, go.player.gety());
	

	// copy structure pixmap into buffer
	for (int x = focus_bound_low; x < focus_bound_high &&x < XBLOCK_NUM; x++){
		for (int y = 0; y < YBLOCK_NUM; y++){
			if (!go.structure_map[x][y]) continue;
			XCopyArea(display, xinfo.pixmap[XInfo::PSTRUCTURE],
				pixmap, gc, 0, 0, 
				final_blockside_len, final_blockside_len,
				x * final_blockside_len - focus, y * final_blockside_len);
		}
	}

	// copy cannon pixmap into buffer
	for (int x = focus_bound_low, y; x < focus_bound_high &&x < XBLOCK_NUM; x++){
		y = go.cannon_height_map[x];
		if (y == NO_CANNON) continue;
			XCopyArea(display, xinfo.pixmap[XInfo::PCANNON],
				pixmap, gc, 0, 0, 
				final_blockside_len /2, final_blockside_len,
				x * final_blockside_len + final_blockside_len/4 - focus,
					y * final_blockside_len);
	}

	for (auto it = go.missiles.begin(), end = go.missiles.end(); it != end; it++){
		if (!within_focus_x(it->getx() / final_blockside_len, 
			it->gety() / final_blockside_len, missile_dim.first)) continue;
		XCopyArea(display, xinfo.pixmap[XInfo::PMISSILE],
			pixmap, gc,
			0, 0, 
			missile_dim.first, missile_dim.second,
			it->getx() - focus, it->gety());
	}

	// copy the buffer into the window
	XCopyArea(xinfo.display, xinfo.pixmap[XInfo::GAME_SCREEN],
		xinfo.window,  xinfo.gc[XInfo::DEFAULT],
		0, 0, dim.first, dim.second, 0, 0);
}

bool Renderer::within_focus_x(int x, int y, int width){
	return x >= focus_bound_low 
		&& (x * (int) final_blockside_len +width)/ (int)final_blockside_len <= focus_bound_high
		&& y > 0;
}

void Renderer::recalculate_focus_bound(){
	focus += SCROLL_FACTOR;
	focus_bound_low = (focus - final_blockside_len) / final_blockside_len;
	focus_bound_high = (dim.first + focus + final_blockside_len) / final_blockside_len;
}
