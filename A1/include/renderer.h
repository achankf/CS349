#ifndef __ALFRED_renderer_h__
#define __ALFRED_renderer_h__
#include "xinfo.h"
#include "game.h"
#include "config.h"
#include <string>
#include <utility>

// place holder
class Game;

class Renderer{
public: /* members */
	std::pair<unsigned int, unsigned int> dim;
	magnitude_t focus;
	int focus_bound_low, focus_bound_high;
	bool show_splash;
	unsigned int final_blockside_len;
	std::pair<unsigned int, unsigned int> player_dim, missile_dim;

public: /* functions */
	Renderer(Game &, XInfo &);
	void update_attributes(Game &go, XInfo &xinfo, unsigned int new_width, unsigned int new_height);
	void repaint(Game &, XInfo &);
	bool within_focus_x(int x, int y, int width);
	void draw_structure(Game &, XInfo &, int x, int y);
	void redraw_missile(XInfo &);
	void redraw_player(XInfo &);
	void redraw_cannon(XInfo &);
	void redraw_structure(XInfo &);
	void draw_cannon(Game &, XInfo &, int x, int y);
	void draw_splash(Game &, XInfo &);
	void draw_game_over(Game &,XInfo &);
private: /* functions */
	void recalculate_focus_bound();
};

#endif
