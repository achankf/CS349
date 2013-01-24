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
	std::pair<dim_t, dim_t> dim;
	magnitude_t focus;
	int focus_bound_low, focus_bound_high;
	bool show_splash;
	dim_t final_blockside_len;
	std::pair<dim_t, dim_t> player_dim, bomb_dim;

public: /* functions */
	Renderer(Game &, XInfo &);
	void update_attributes(Game &go, XInfo &xinfo, dim_t new_width, dim_t new_height);
	void repaint(Game &, XInfo &);
	bool within_range(coor_t x, coor_t y, dim_t width);
	void redraw_bomb(XInfo &);
	void redraw_player(XInfo &);
	void redraw_cannon(XInfo &);
	void redraw_structure(Game &, XInfo &);
	void draw_structure(XInfo &, coor_t xcoor, coor_t ycoor, int colour_id);
	void draw_splash(Game &, XInfo &);
	void draw_game_over(Game &,XInfo &);
	void redraw_splash(Game &, XInfo &);
private: /* functions */
	void recalculate_focus_bound(Game &);
};

#endif
