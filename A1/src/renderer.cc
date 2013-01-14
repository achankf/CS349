#include "renderer.h"
#include "config.h"
#include <sstream>

#ifdef DEBUG
#include "func.h"
#include <iostream>
#endif
using namespace std;

Renderer::Renderer(Game *go, XInfo *xinfo) :
	go_pt(go),
	xinfo_pt(xinfo),
	dwidth(DisplayWidth(xinfo->display, xinfo->screen)),
	dheight(DisplayHeight(xinfo->display, xinfo->screen)),
	width(DEFAULT_WIDTH > dwidth ? dwidth : DEFAULT_WIDTH),
	height(DEFAULT_HEIGHT > dheight ? dheight : DEFAULT_HEIGHT),
	focus(0)
{
	update_attributes(DEFAULT_WIDTH, DEFAULT_HEIGHT);
}

void Renderer::update_attributes(int new_width, int new_height){
	width = new_width > dwidth ? dwidth : new_width;
	height = new_height > dheight ? dheight : new_height;
	yblocksize = height / go_pt->yblock_num;
	xblocksize = yblocksize;
	//edge_difference = go_pt->xblock_num / 2 * xblocksize;

#ifdef DEBUG
	{
		using namespace std;
		cout << "display:";
		print_pair(dwidth,dheight);
		cout << "game:";
		print_pair(width,height);
		cout << "block_size:";
		print_pair(xblocksize,yblocksize);
		//cout << "edge_difference:" << edge_difference << endl;
	}
#endif
}

void Renderer::repaint(){
	update_focus();
	//XClearWindow(xinfo_pt->display,xinfo_pt->window);
	// clean canvas
	XFillRectangle(xinfo_pt->display,
		xinfo_pt->pixmap,
		xinfo_pt->gc[XInfo::INVERSE_BACKGROUND],
		0, 0, dwidth, dheight);

	draw_player(*xinfo_pt, go_pt->player);
	for (auto it = go_pt->buildings.begin(), end = go_pt->buildings.end(); it != end; it++){
		draw_building(*xinfo_pt,*it);
	}
	for (auto it = go_pt->missiles.begin(), end = go_pt->missiles.end(); it != end; it++){
		draw_missile(*xinfo_pt,*it);
	}
	XCopyArea(xinfo_pt->display, xinfo_pt->pixmap, xinfo_pt->window,  xinfo_pt->gc[XInfo::DEFAULT],
		0, 0, dwidth, dheight, 0, 0);
	XFlush(xinfo_pt->display);
}

void Renderer::draw_player(XInfo &xinfo, Movable &player){
	Display *display = xinfo.display;
//	Window win = xinfo.window;
	GC gc = xinfo.gc[xinfo::DEFAULT];
	Pixmap pixmap = xinfo.pixmap;
	magnitude_t x = player.getx() - focus;
	magnitude_t y = player.gety();
	XFillRectangle(display, pixmap, gc, x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
}

void Renderer::draw_building(XInfo &xinfo, Object &building){
	Display *display = xinfo.display;
//	Window win = xinfo.window;
	GC gc = xinfo.gc[xinfo::DEFAULT];
	Pixmap pixmap = xinfo.pixmap;
	magnitude_t x = building.getx() - focus;
	magnitude_t y = building.gety();
	XDrawRectangle(display, pixmap, gc, x * xblocksize, y * yblocksize, xblocksize, yblocksize);
	std::stringstream ss;
	ss << (int)x;
	XDrawString(display, pixmap, gc, x*xblocksize + 1, y * yblocksize +10, ss.str().c_str(), ss.str().size());
	ss.str("");
	ss << (int)y;
	XDrawString(display, pixmap, gc, x*xblocksize + 1, y * yblocksize +20, ss.str().c_str(), ss.str().size());
}

void Renderer::draw_missile(XInfo &xinfo, Missile &missile){
	Display *display = xinfo.display;
//	Window win = xinfo.window;
	GC gc = xinfo.gc[xinfo::DEFAULT];
	Pixmap pixmap = xinfo.pixmap;
	magnitude_t x = missile.getx() - focus;
	magnitude_t y = missile.gety();
	XFillRectangle(display, pixmap, gc, x, y, MISSILE_WIDTH, MISSILE_HEIGHT);
}

void Renderer::update_focus(){
	focus += SCROLL_FACTOR;
}

int Renderer::get_xblocksize() const{
	return yblocksize;
}

int Renderer::get_yblocksize() const{
	return yblocksize;
}
