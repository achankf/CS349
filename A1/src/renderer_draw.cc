#include "renderer.h"
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
