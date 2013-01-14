#include "renderer.h"
#include "config.h"
#include <sstream>

#ifdef DEBUG
#include "func.h"
#include <iostream>
#endif
using namespace std;

Renderer::Renderer(Game &go, XInfo &xinfo) :
	width(DEFAULT_WIDTH > xinfo.dwidth ? xinfo.dwidth : DEFAULT_WIDTH),
	height(DEFAULT_HEIGHT > xinfo.dheight ? xinfo.dheight : DEFAULT_HEIGHT),
	focus(0),
	focus_bound_low(0),
	focus_bound_high(0)
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
		xinfo.pixmap,
		xinfo.gc[XInfo::INVERSE_BACKGROUND],
		0, 0, xinfo.dwidth, xinfo.dheight);

	go.player.draw(*this,xinfo);
	for (auto it = go.buildings.begin(), end = go.buildings.end(); it != end; it++){
		if (!within_focus_x(*it)) continue;
		it->draw(*this,xinfo);
	}
	for (auto it = go.missiles.begin(), end = go.missiles.end(); it != end; it++){
		if (!within_focus_x(*it)) continue;
		it->draw(*this,xinfo);
	}
	XCopyArea(xinfo.display, xinfo.pixmap, xinfo.window,  xinfo.gc[XInfo::DEFAULT],
		0, 0, xinfo.dwidth, xinfo.dheight, 0, 0);
	XFlush(xinfo.display);
}

bool Renderer::within_focus_x(Object &ob){
#if 0
	print_pair((int)ob.getx() / xblocksize,  (int)(ob.getx() + ob.get_width()) / xblocksize);
	print_pair(focus_bound_low,  focus_bound_high);
	cout << endl;
#endif
	return ob.getx() / xblocksize >= focus_bound_low 
		&& (ob.getx() + ob.get_width()) / xblocksize <= focus_bound_high;
}

void Renderer::recalculate_focus_bound(){
	focus += SCROLL_FACTOR;
	focus_bound_low = (focus - xblocksize) / xblocksize;
	focus_bound_high = (width + focus + xblocksize) / xblocksize;

#if DEBUG
	print_pair(focus_bound_low, focus_bound_high);
#endif
}
