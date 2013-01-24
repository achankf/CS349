#ifndef __ALFRED_xinfo_h__
#define __ALFRED_xinfo_h__

#include <string>
#include <X11/Xlib.h>
#include "config.h"

class Game;

/* Information to draw on the window. */
class XInfo {
public: /* enum */
	enum GC_TYPE{
		GC_DEFAULT,
		GC_INVERSE_BACKGROUND,
		GC_TITLE_FONT,
		GC_HELICOPTER,
		GC_CANNON,
		NUM_GC_TYPE
	};
	enum PIXMAP_TYPE{
		GAME_SCREEN,
		SPLASH_SCREEN,
		GAME_OVER_SCREEN,
		PPLAYER,
		PBOMB,
		PCANNON,
		PSTRUCTURE,
		NUM_PIXMAP_TYPE
	};
	enum COLOUR_TYPE{
		C_PLAYER,
		C_CANNON,
		C_BOMB,
		// blue for structures
		C_BLUE,
		C_BLUE2,
		C_BLUE3,
		C_BLUE4,
		C_BLUE5,
		NUM_COLOUR
	};

public: /* member variables */
	Display *display;
	int screen;
	Window window;
	GC gc[NUM_GC_TYPE], colour_gc[NUM_COLOUR];
	Pixmap pixmap[NUM_PIXMAP_TYPE];
	std::pair<dim_t, dim_t> ddim;
	XFontStruct *title_font_struct;
	XColor colours[NUM_COLOUR];
	Colormap colourmap;

public: /* functions */
	XInfo(int argc, char **argv);
	~XInfo();
	dim_t dwidth();
	dim_t dheight();
	void change_window_dim(std::pair<dim_t, dim_t>&);
	Pixmap new_pixmap(PIXMAP_TYPE pt, std::pair<dim_t, dim_t>&, GC_TYPE gc_type = GC_INVERSE_BACKGROUND);
};

#endif
