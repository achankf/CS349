#include <cstring>
#include <string>
#include <sstream>
#include "renderer.h"
#include <iostream>
using namespace std;

#ifdef DEBUG
#include "func.h"
#endif

void Renderer::draw_structure(XInfo &xinfo, int xcoor, int ycoor){
	Display *display = xinfo.display;
	GC gc = xinfo.gc[XInfo::DEFAULT];
	Pixmap pixmap = xinfo.pixmap[XInfo::GAME_SCREEN];
	XFillRectangle(display, pixmap, gc, xcoor, ycoor, final_blockside_len, final_blockside_len);
}

void Renderer::draw_splash(Game &go, XInfo &xinfo){
	// clean canvas
	XFillRectangle(xinfo.display,
		xinfo.pixmap[XInfo::SPLASH_SCREEN],
		xinfo.gc[XInfo::INVERSE_BACKGROUND],
		0, 0, xinfo.dwidth(), xinfo.dheight());

	XDrawString(xinfo.display,xinfo.pixmap[XInfo::SPLASH_SCREEN],
		xinfo.gc[XInfo::TITLE_FONT],
		30,30,GAME_TITLE,strlen(GAME_TITLE));

	int i;
	for (i = 0; !TUTORIAL[i].empty(); i++){
		XDrawString(xinfo.display,xinfo.pixmap[XInfo::SPLASH_SCREEN],
			xinfo.gc[XInfo::DEFAULT],
			40,60 + 20 *i,TUTORIAL[i].c_str(), TUTORIAL[i].size());
	}

	const string restart_hints("Press \"f\" to continue");
	XDrawString(xinfo.display,xinfo.pixmap[XInfo::SPLASH_SCREEN],
		xinfo.gc[XInfo::TITLE_FONT],
		30,70+20*i,restart_hints.c_str(),restart_hints.size());

	XCopyArea(xinfo.display, xinfo.pixmap[XInfo::SPLASH_SCREEN],
		xinfo.window,  xinfo.gc[XInfo::TITLE_FONT],
		0, 0, xinfo.dwidth(), xinfo.dheight(), 0, 0);
	XFlush(xinfo.display);
}

void Renderer::draw_game_over(Game &go, XInfo &xinfo){
	const char *SURVIVE = go.player.dead ? 
		"You are dead." : "Congratulations! You survived!";
	// clean canvas
	XFillRectangle(xinfo.display,
		xinfo.pixmap[XInfo::SPLASH_SCREEN],
		xinfo.gc[XInfo::INVERSE_BACKGROUND],
		0, 0, xinfo.dwidth(), xinfo.dheight());

	XDrawString(xinfo.display,xinfo.pixmap[XInfo::SPLASH_SCREEN],
		xinfo.gc[XInfo::TITLE_FONT],
		30,50,SURVIVE,strlen(SURVIVE));
		

	std::stringstream ss;
	ss << "Your score is " << go.score() <<'.';

	const string &scores = ss.str();
	XDrawString(xinfo.display,xinfo.pixmap[XInfo::SPLASH_SCREEN],
		xinfo.gc[XInfo::TITLE_FONT],
		30,80,scores.c_str(),scores.size());

	const string restart_hints("Press \"r\" to restart the game, or \"q\" to quit.");
	XDrawString(xinfo.display,xinfo.pixmap[XInfo::SPLASH_SCREEN],
		xinfo.gc[XInfo::TITLE_FONT],
		30,110,restart_hints.c_str(),restart_hints.size());


	XCopyArea(xinfo.display, xinfo.pixmap[XInfo::SPLASH_SCREEN],
		xinfo.window,  xinfo.gc[XInfo::TITLE_FONT],
		0, 0, xinfo.dwidth(), xinfo.dheight(), 0, 0);
	XFlush(xinfo.display);
}

void Renderer::redraw_bomb(XInfo &xinfo){
	// create a new pixmap
	Pixmap pixmap = xinfo.new_pixmap(XInfo::PBOMB, bomb_dim);
	Display *display = xinfo.display;
	GC gc = xinfo.gc[XInfo::DEFAULT];
	XFillRectangle(display, pixmap, gc, 0, 0, bomb_dim.first, bomb_dim.second);
}

void Renderer::redraw_player(XInfo &xinfo){
	// create a new pixmap
	Pixmap pixmap = xinfo.new_pixmap(XInfo::PPLAYER, player_dim);
	Display *display = xinfo.display;
	GC gc = xinfo.gc[XInfo::DEFAULT];
	XFillRectangle(display, pixmap, gc, 0, 0, player_dim.first, player_dim.second);
}

void Renderer::redraw_cannon(XInfo &xinfo){
	pair<unsigned int, unsigned int> temp_dim(
		final_blockside_len/2,  final_blockside_len);
	// create a new pixmap
	Pixmap pixmap = xinfo.new_pixmap(XInfo::PCANNON, temp_dim);
	Display *display = xinfo.display;
	GC gc = xinfo.gc[XInfo::DEFAULT];
	XDrawRectangle(display, pixmap, gc, 0, 0, final_blockside_len/2 -1,  final_blockside_len);
}

void Renderer::redraw_structure(Game &go, XInfo &xinfo){
	// create a new pixmap
	xinfo.new_pixmap(XInfo::PSTRUCTURE, dim);

	// draw the structures based on structure_map
	for (int x = 0, xcoor = -focus; x < XBLOCK_NUM; 
		x++, xcoor += final_blockside_len){

		// no need to draw structures that are no needed
		if (x > focus_bound_high){
			break; // stop
		} else if (x < focus_bound_low){
			continue;
		}

		for (int y = 0, ycoor = -final_blockside_len; y < YBLOCK_NUM; y++){
			ycoor += final_blockside_len;
			if (!go.structure_map[x][y]) continue;
			draw_structure(xinfo, xcoor, ycoor);
		}
	}
}
