#ifndef __ALFRED_renderer_h__
#define __ALFRED_renderer_h__
#include "xinfo.h"
#include "game.h"
#include "config.h"

// place holder
class Game;

class Renderer{
public: /* members */
	int width,height;
	int xblocksize, yblocksize;
	magnitude_t focus;
	int focus_bound_low, focus_bound_high;

public: /* functions */
	Renderer(Game &, XInfo &);
	void update_attributes(Game &go, XInfo &xinfo, int new_width, int new_height);
	void repaint(Game &, XInfo &);
	bool within_focus_x(int x, int y, int width);
	void draw_structure(Game &, XInfo &, int x, int y);
	void draw_cannon(Game &, XInfo &, int x, int y);
private: /* functions */
	void recalculate_focus_bound();
};

#endif
