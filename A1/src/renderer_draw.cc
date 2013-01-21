#include <cstring>
#include <string>
#include <sstream>
#include "renderer.h"
#include <iostream>
using namespace std;

void Renderer::draw_structure(Game &go, XInfo &xinfo, int x, int y){
	Display *display = xinfo.display;
	GC gc = xinfo.gc[XInfo::DEFAULT];
	Pixmap pixmap = xinfo.pixmap[XInfo::GAME_SCREEN];
	x = x * xblocksize - focus;
	y *= yblocksize;
	XDrawRectangle(display, pixmap, gc, x, y, xblocksize, yblocksize);
}

void Renderer::draw_cannon(Game &go, XInfo &xinfo, int x, int y){
	Display *display = xinfo.display;
	GC gc = xinfo.gc[XInfo::DEFAULT];
	Pixmap pixmap = xinfo.pixmap[XInfo::GAME_SCREEN];
	x = x * xblocksize - focus;
	y *= yblocksize;
	XFillRectangle(display, pixmap, gc, x + xblocksize/4, y, xblocksize/2,	yblocksize);
}

void Renderer::draw_splash(Game &go, XInfo &xinfo){
	// clean canvas
	XFillRectangle(xinfo.display,
		xinfo.pixmap[XInfo::SPLASH_SCREEN],
		xinfo.gc[XInfo::INVERSE_BACKGROUND],
		0, 0, xinfo.dwidth, xinfo.dheight);

	XDrawString(xinfo.display,xinfo.pixmap[XInfo::SPLASH_SCREEN],
		xinfo.gc[XInfo::TITLE_FONT],
		30,50,GAME_TITLE,strlen(GAME_TITLE));
	for (int i = 0; !TUTORIAL[i].empty(); i++){
		XDrawString(xinfo.display,xinfo.pixmap[XInfo::SPLASH_SCREEN],
			xinfo.gc[XInfo::DEFAULT],
			40,80 + 20 *i,TUTORIAL[i].c_str(), TUTORIAL[i].size());
	}

	XCopyArea(xinfo.display, xinfo.pixmap[XInfo::SPLASH_SCREEN],
		xinfo.window,  xinfo.gc[XInfo::TITLE_FONT],
		0, 0, xinfo.dwidth, xinfo.dheight, 0, 0);
	XFlush(xinfo.display);
}

void Renderer::draw_game_over(Game &go, XInfo &xinfo){
	const char *SURVIVE = go.player.dead ? 
		"You are dead." : "Congratulations! You survived!";
	// clean canvas
	XFillRectangle(xinfo.display,
		xinfo.pixmap[XInfo::SPLASH_SCREEN],
		xinfo.gc[XInfo::INVERSE_BACKGROUND],
		0, 0, xinfo.dwidth, xinfo.dheight);

	XDrawString(xinfo.display,xinfo.pixmap[XInfo::SPLASH_SCREEN],
		xinfo.gc[XInfo::TITLE_FONT],
		30,50,SURVIVE,strlen(SURVIVE));
		

	std::stringstream ss;
	ss << "Your score is " << go.score() <<'.';

	const string &scores = ss.str();
	XDrawString(xinfo.display,xinfo.pixmap[XInfo::SPLASH_SCREEN],
		xinfo.gc[XInfo::TITLE_FONT],
		30,80,scores.c_str(),scores.size());

	const string restart_hints("Press \"r\" to restart the game");
	XDrawString(xinfo.display,xinfo.pixmap[XInfo::SPLASH_SCREEN],
		xinfo.gc[XInfo::TITLE_FONT],
		30,110,restart_hints.c_str(),restart_hints.size());


	XCopyArea(xinfo.display, xinfo.pixmap[XInfo::SPLASH_SCREEN],
		xinfo.window,  xinfo.gc[XInfo::TITLE_FONT],
		0, 0, xinfo.dwidth, xinfo.dheight, 0, 0);
	XFlush(xinfo.display);
}
