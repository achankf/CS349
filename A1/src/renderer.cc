#include "renderer.h"
#include "config.h"

#ifdef DEBUG
#include "func.h"
#include <iostream>
#include <sstream>
#endif
using namespace std;

Renderer::Renderer(Game &go, XInfo &xinfo) :
	width(DEFAULT_WIDTH > xinfo.dwidth ? xinfo.dwidth : DEFAULT_WIDTH),
	height(DEFAULT_HEIGHT > xinfo.dheight ? xinfo.dheight : DEFAULT_HEIGHT),
	focus(0),
	focus_bound_low(0),
	focus_bound_high(0),
	show_splash(true)
{
	update_attributes(go, xinfo, DEFAULT_WIDTH, DEFAULT_HEIGHT);
}

void Renderer::update_attributes(Game &go, XInfo &xinfo, int new_width, int new_height){
	width = new_width > xinfo.dwidth ? xinfo.dwidth : new_width;
	height = new_height > xinfo.dheight ? xinfo.dheight : new_height;
	yblocksize = height / go.yblock_num;
	xblocksize = yblocksize;

#ifdef DEBUG
	{
		using namespace std;
		cout << "display:";
		print_pair(xinfo.dwidth,xinfo.dheight);
		cout << "game:";
		print_pair(width,height);
		cout << "block_size:";
		print_pair(xblocksize,yblocksize);
	}
#endif
}

void Renderer::repaint(Game &go, XInfo &xinfo){
	recalculate_focus_bound();

	// clean canvas
	XFillRectangle(xinfo.display,
		xinfo.pixmap[XInfo::GAME_SCREEN],
		xinfo.gc[XInfo::INVERSE_BACKGROUND],
		0, 0, xinfo.dwidth, xinfo.dheight);

	go.player.draw(*this,xinfo);

	/* structures are stored in a 2D array of char */
	for (int x = focus_bound_low; x < focus_bound_high &&x < go.xblock_num; x++){
		for (int y = 0; y < go.yblock_num; y++){
			if (!go.structure_map[x][y]) continue;
			draw_structure(go, xinfo, x, y);
		}
	}

	for (int x = focus_bound_low, y; x < focus_bound_high &&x < go.xblock_num; x++){
		y = go.cannon_height_map[x];
		if (y == NO_CANNON) continue;
		draw_cannon(go, xinfo, x, y);
	}

	for (auto it = go.missiles.begin(), end = go.missiles.end(); it != end; it++){
		if (!within_focus_x(it->getx() / xblocksize, 
			it->gety() / xblocksize, MISSILE_WIDTH)) continue;
		it->draw(*this,xinfo);
	}
	XCopyArea(xinfo.display, xinfo.pixmap[XInfo::GAME_SCREEN],
		xinfo.window,  xinfo.gc[XInfo::DEFAULT],
		0, 0, xinfo.dwidth, xinfo.dheight, 0, 0);
	XFlush(xinfo.display);
}

bool Renderer::within_focus_x(int x, int y, int width){
	return x >= focus_bound_low 
		&& (x+width) <= focus_bound_high;
}

void Renderer::recalculate_focus_bound(){
	focus += SCROLL_FACTOR;
	focus_bound_low = (focus - xblocksize) / xblocksize;
	focus_bound_high = (width + focus + xblocksize) / xblocksize;
}
