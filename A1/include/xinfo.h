#ifndef __ALFRED_xinfo_h__
#define __ALFRED_xinfo_h__

#include <string>
#include <X11/Xlib.h>

namespace xinfo{
	enum DEFAULTS{
		BORDER = 4,
		HINT_X = 100,
		HINT_Y = 100,
		HINT_WIDTH = 800,
		HINT_HEIGHT = 600
	};

	enum GC_TYPE{
		DEFAULT,
		INVERSE_BACKGROUND,
		NUM_GC_TYPE
	};
}

extern const char *TITLE;

/* Information to draw on the window. */
class XInfo {
public: /* enum */
	enum GC_TYPE{
		DEFAULT,
		INVERSE_BACKGROUND,
		NUM_GC_TYPE
	};
public: /* member variables */
	Display *display;
	int screen;
	Window window;
	GC gc[xinfo::NUM_GC_TYPE];
	Pixmap pixmap;

public: /* functions */
	XInfo(int argc, char **argv);
	~XInfo();
};

#endif
