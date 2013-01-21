#ifndef __ALFRED_xinfo_h__
#define __ALFRED_xinfo_h__

#include <string>
#include <X11/Xlib.h>

class Game;

/* Information to draw on the window. */
class XInfo {
public: /* enum */
	enum GC_TYPE{
		DEFAULT,
		INVERSE_BACKGROUND,
		TITLE_FONT,
		NUM_GC_TYPE
	};
	enum PIXMAP_TYPE{
		GAME_SCREEN,
		SPLASH_SCREEN,
		PPLAYER,
		PMISSILE,
		NUM_PIXMAP_TYPE
	};
	enum FONT_TYPE{
		F_TITLE,
		NUM_FONT_TYPE
	};
public: /* member variables */
	Display *display;
	int screen;
	Window window;
	GC gc[NUM_GC_TYPE];
	Pixmap pixmap[NUM_PIXMAP_TYPE];
	std::pair<unsigned int, unsigned int> ddim;
	Font font[NUM_FONT_TYPE];

public: /* functions */
	XInfo(int argc, char **argv);
	~XInfo();
	unsigned int dwidth();
	unsigned int dheight();
	void change_window_dim(std::pair<unsigned int, unsigned int>&);
	Pixmap new_pixmap(PIXMAP_TYPE pt, std::pair<unsigned int, unsigned int>&);
};

#endif
