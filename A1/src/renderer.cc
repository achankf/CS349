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
	update_attributes(go, xinfo, DEFAULT_WIDTH, DEFAULT_HEIGHT, true);
}

void Renderer::update_attributes(Game &go, XInfo &xinfo, unsigned int new_width, unsigned int new_height, bool init){
	// check whether there's change
	if (!init && dim.first == new_width && dim.second == new_height) return;

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
	final_blockside_len = (dim.second/ YBLOCK_NUM);

	dim.second = final_blockside_len * YBLOCK_NUM;

	// player dimension
	player_dim.first = (dim.second / PLAYER_WIDTH_PROP);
	player_dim.second = (dim.second / PLAYER_HEIGHT_PROP);
	// missile dimension
	missile_dim.first = (dim.second / MISSILE_WIDTH_PROP);
	missile_dim.second = (dim.second / MISSILE_HEIGHT_PROP);

	xinfo.new_pixmap(XInfo::GAME_SCREEN,dim);
	redraw_player(xinfo);
	redraw_missile(xinfo);
	redraw_cannon(xinfo);
	redraw_structure(go,xinfo);

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

	recalculate_focus_bound(go);
	// need to refit because player may decide to propel
	go.player.fit_to_boundary(*this);

	// clean canvas
	XFillRectangle(display, pixmap,
		xinfo.gc[XInfo::INVERSE_BACKGROUND],
		0, 0, dim.first, dim.second);

	// draw structures (background)
	redraw_structure(go, xinfo);

	// draw the player
	XCopyArea(display, xinfo.pixmap[XInfo::PPLAYER],
		pixmap, gc, 0, 0, 
		player_dim.first, player_dim.second,
		go.player.getx() - focus, go.player.gety());

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

void Renderer::recalculate_focus_bound(Game &go){
	if (go.propel){
		focus += PROPEL_SPEED;
	} else {
		focus += go.scroll_factor();
	}
	focus_bound_low = (focus - final_blockside_len) / final_blockside_len;
	focus_bound_high = (dim.first + focus + final_blockside_len) / final_blockside_len;

	// calculate whether player has reached the end
	if (focus_bound_high >= XBLOCK_NUM - XBLOCK_BEFORE_WIN){
		go.game_over = true;
	}
}
