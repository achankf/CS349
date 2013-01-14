#ifndef __ALFRED_renderer_h__
#define __ALFRED_renderer_h__
#include "xinfo.h"
#include "game.h"

// place holder
class Game;

class Renderer{
	Game *go_pt;
	XInfo *xinfo_pt;

	int dwidth,dheight;
	int width,height;
	int xblocksize, yblocksize;
	float focus;

public:
	Renderer(Game *, XInfo *);
	void update_focus();
	void update_attributes(int new_width, int new_height);
	void draw_player(XInfo &, Movable &);
	void draw_building(XInfo &, Object &);
	void draw_missile(XInfo &, Missile &);
	int get_xblocksize() const;
	int get_yblocksize() const;
	void repaint();
};

#endif
