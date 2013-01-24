#include <string>
#include <sstream>
#include "renderer.h"
using namespace std;

#ifdef DEBUG
#include "func.h"
#endif

void Renderer::draw_structure(XInfo &xinfo, coor_t xcoor, coor_t ycoor, int colour_id){
	Display *display = xinfo.display;
	GC gc = xinfo.colour_gc[colour_id];
	Pixmap pixmap = xinfo.pixmap[XInfo::GAME_SCREEN];
	XFillRectangle(display, pixmap, gc, xcoor, ycoor, final_blockside_len-1, final_blockside_len-1);
}

void Renderer::redraw_splash(Game &go, XInfo &xinfo){
	Display *display = xinfo.display;
	GC gc = xinfo.gc[XInfo::GC_DEFAULT];
	GC title_gc = xinfo.gc[XInfo::GC_TITLE_FONT];
	Pixmap pixmap = xinfo.new_pixmap(XInfo::SPLASH_SCREEN, dim);

	XDrawString(display,pixmap, title_gc, 30,30,GAME_TITLE.c_str(),GAME_TITLE.size());

	int i;
	for (i = 0; !TUTORIAL[i].empty(); i++){
		XDrawString(display,pixmap, gc, 40,60 + 20 *i,TUTORIAL[i].c_str(), TUTORIAL[i].size());
	}

	const string restart_hints("Press \"f\" to continue");
	XDrawString(display,pixmap, title_gc,
		30,70+20*i,restart_hints.c_str(),restart_hints.size());
}

void Renderer::draw_splash(Game &go, XInfo &xinfo){
	XCopyArea(xinfo.display, xinfo.pixmap[XInfo::SPLASH_SCREEN],
		xinfo.window,  xinfo.gc[XInfo::GC_TITLE_FONT],
		0, 0, xinfo.dwidth(), xinfo.dheight(), 0, 0);
}

void Renderer::draw_game_over(Game &go, XInfo &xinfo){
	Display *display = xinfo.display;
	GC gc = xinfo.gc[XInfo::GC_TITLE_FONT];
	Pixmap pixmap = xinfo.new_pixmap(XInfo::GAME_OVER_SCREEN, dim);

	const string SURVIVE = go.player.dead ? 
		"You are dead." : "Congratulations! You survived!";
	// print whether you survived or dead
	XDrawString(display, pixmap, gc, 30, 50, SURVIVE.c_str(), SURVIVE.size());

	// print scores
	std::stringstream ss;
	ss << "Your score is " << go.score() <<'.';
	const string &scores = ss.str();
	XDrawString(display, pixmap, gc, 30, 80, scores.c_str(), scores.size());

	// print hints for restarting or quit
	const string restart_hints("Press \"r\" to restart the game, or \"q\" to quit.");
	XDrawString(display,pixmap, gc, 30,110,restart_hints.c_str(),restart_hints.size());

	XCopyArea(xinfo.display, xinfo.pixmap[XInfo::GAME_OVER_SCREEN], xinfo.window, 
		xinfo.gc[XInfo::GC_TITLE_FONT], 0, 0, xinfo.dwidth(), xinfo.dheight(), 0, 0);
}

void Renderer::redraw_bomb(XInfo &xinfo){
	// create a new pixmap
	Pixmap pixmap = xinfo.new_pixmap(XInfo::PBOMB, bomb_dim);
	Display *display = xinfo.display;
	GC gc = xinfo.gc[XInfo::GC_DEFAULT];
	XFillRectangle(display, pixmap, gc, 0, 0, bomb_dim.first, bomb_dim.second);
}

void Renderer::redraw_player(XInfo &xinfo){
	// create a new pixmap
	Pixmap pixmap = xinfo.new_pixmap(XInfo::PPLAYER, player_dim);
	Display *display = xinfo.display;
	GC gc = xinfo.colour_gc[XInfo::C_PLAYER];

	// attributes for the rotor (top part of helicopter)
	coor_t rotor_x = player_dim.first / 2;
	dim_t rotor_width = player_dim.first / 2;
	dim_t rotor_height = player_dim.second / 4.5;

	// body
	coor_t body_x = player_dim.first /4;
	coor_t body_y = player_dim.second /3;
	dim_t body_width = player_dim.first /1.3;
	dim_t body_height = player_dim.second /1.5;

	// tail
	dim_t tail_width = player_dim.second / 2;
	dim_t tail_height = player_dim.second / 1.2;

	XArc arcs[] = {
		XArc{rotor_x,0, rotor_width, rotor_height, 0,360*64},
		XArc{body_x, body_y, body_width, body_height, 0,360*64},
		XArc{0, 0, tail_width, tail_height, 0,360*64},
	};

	XFillArcs(display,pixmap,gc,arcs,3);

	// connect the rotor to the body
	coor_t rotor_body_x0 = player_dim.first / 1.5;
	coor_t rotor_body_y0 = player_dim.second / 2;
	coor_t rotor_body_x1 = player_dim.first / 1.35;
	coor_t rotor_body_y1 = player_dim.second / 5;
	XDrawLine(display, pixmap, xinfo.gc[XInfo::GC_HELICOPTER], rotor_body_x0, rotor_body_y0, rotor_body_x1, rotor_body_y1);

	// connect the body to the tail
	dim_t body_tail_x0 = player_dim.first / 6;
	dim_t body_tail_y0 = player_dim.second / 2;
	dim_t body_tail_x1 = player_dim.first / 3;
	dim_t body_tail_y1 = player_dim.second / 1.5;
	XDrawLine(display, pixmap, xinfo.gc[XInfo::GC_HELICOPTER], body_tail_x0, body_tail_y0, body_tail_x1, body_tail_y1);
}

void Renderer::redraw_cannon(XInfo &xinfo){
	pair<dim_t, dim_t> temp_dim(
		final_blockside_len/2,  final_blockside_len);
	// create a new pixmap
	Pixmap pixmap = xinfo.new_pixmap(XInfo::PCANNON, temp_dim, XInfo::GC_CANNON);
	Display *display = xinfo.display;
	GC gc = xinfo.gc[XInfo::GC_CANNON];
	XDrawRectangle(display, pixmap, gc, 0, 0, final_blockside_len/2 -1,  final_blockside_len);
}

void Renderer::redraw_structure(Game &go, XInfo &xinfo){
	// create a new pixmap
	xinfo.new_pixmap(XInfo::PSTRUCTURE, dim);

	// draw the structures based on structure_map
	for (coor_t x = 0, xcoor = -focus; x < XBLOCK_NUM; 
		x++, xcoor += final_blockside_len){

		// no need to draw structures that are no needed
		if (x > focus_bound_high){
			break; // stop
		} else if (x < focus_bound_low){
			continue;
		}

		for (coor_t y = 0, ycoor = -final_blockside_len; y < YBLOCK_NUM; y++){
			ycoor += final_blockside_len;
			if (!go.structure_map[x][y]) continue;

			// look for which colour we want for the structure
			int colour_id;
			switch((x + y) % 5){
				default: // fall-through
				case 0: colour_id = XInfo::C_BLUE; break;
				case 1: colour_id = XInfo::C_BLUE2; break;
				case 2: colour_id = XInfo::C_BLUE3; break;
				case 3: colour_id = XInfo::C_BLUE4; break;
				case 4: colour_id = XInfo::C_BLUE5; break;
			}
			draw_structure(xinfo, xcoor, ycoor, colour_id);
		}
	}
}
