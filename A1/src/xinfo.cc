#include <cstdlib>
#include <unistd.h>
#include "xinfo.h"
#include "func.h"
#include "renderer.h"
#include "config.h"
#include "game.h"

#include <cstring>
#include <X11/Xlib.h>
#include <X11/Xutil.h>
using namespace std;

#define ALL_EVENT_MASKS ( KeyReleaseMask | StructureNotifyMask | KeyPressMask | ExposureMask)
const char *COLOURS[] = {"#094e00","#F58D1D","#6B6A69","#000080","#1a30ff","#0011b3","#4d5eff","#0016e6"};

/* constructor */
XInfo::XInfo(int argc, char **argv){
	XSizeHints hints;
	unsigned long white, black;

   /*
	* Display opening uses the DISPLAY	environment variable.
	* It can go wrong if DISPLAY isn't set, or you don't have permission.
	*/	
	display = XOpenDisplay( "" );
	if ( !display )	{
		error( "Can't open display." );
	}
	
   /*
	* Find out some things about the display you're using.
	*/
	screen = DefaultScreen( display );

	white = XWhitePixel( display, screen );
	black = XBlackPixel( display, screen );

	hints.x = HINT_X;
	hints.y = HINT_Y;
	hints.width = DEFAULT_WIDTH;//HINT_WIDTH;
	hints.height = DEFAULT_HEIGHT;//HINT_HEIGHT;
	hints.flags = PPosition | PSize;

	window = XCreateSimpleWindow( 
		display,				// display where window appears
		DefaultRootWindow( display ), // window's parent in window tree
		hints.x, hints.y,			// upper left corner location
		hints.width, hints.height,	// size of the window
		BORDER,						// width of window's border
		black,						// window border colour
		white );					// window background colour

	XSelectInput(display,window, ALL_EVENT_MASKS);
		
	XSetStandardProperties(
		display,		// display containing the window
		window,		// window whose properties are set
		GAME_TITLE.c_str(),	// window's title
		"OW",				// icon's title
		None,				// pixmap for the icon
		argv, argc,			// applications command line args
		&hints );			// size hints for the window

	// allocate gc's
	for (int i = 0; i < NUM_GC_TYPE; i++){
		gc[i] = XCreateGC(display, window, 0, 0);
	}

	/* create graphic contexts */
	XSetForeground(display, gc[GC_DEFAULT], black);
	XSetBackground(display, gc[GC_DEFAULT], white);
	XSetFillStyle(display, gc[GC_DEFAULT], FillSolid);
	XSetLineAttributes(display, gc[GC_DEFAULT], 1, LineSolid, CapButt, JoinRound);

	XSetForeground(display, gc[GC_INVERSE_BACKGROUND], white);
	XSetBackground(display, gc[GC_INVERSE_BACKGROUND], black);

	/* allocate fonts */
	title_font_struct = XLoadQueryFont(display,"-*-*-bold-r-*-*-*-*-*-*-*-*-*-*");
	if (!title_font_struct){
		title_font_struct = XLoadQueryFont(display,"fixed");
	}
	XSetFont(display,gc[GC_TITLE_FONT], title_font_struct->fid);
 
	/* set up for colours and allocate gc for each */
	colourmap = XDefaultColormap(display, screen);
	for (int i = 0; i < NUM_COLOUR; i++){
		XParseColor(display, colourmap, COLOURS[i], colours + i);
		XAllocColor(display, colourmap, colours + i);
		colour_gc[i] = XCreateGC(display, window, 0, 0);
		XSetForeground(display, colour_gc[i], colours[i].pixel);
	}

	// set gc attributes for special rendering objects
	XSetLineAttributes(display, gc[GC_HELICOPTER], 5, LineSolid, CapButt, JoinRound);
	XSetForeground(display, gc[GC_HELICOPTER], colours[C_PLAYER].pixel);
	XSetForeground(display, gc[GC_CANNON], colours[C_CANNON].pixel);

	/* allocate pixmaps */
	int depth = DefaultDepth(display, DefaultScreen(display));
	for (int i = 0; i < NUM_PIXMAP_TYPE; i++){
		pixmap[i] = XCreatePixmap(display, window, hints.width, hints.height, depth);
	}

	ddim.first = DisplayWidth(display, screen);
	ddim.second= DisplayHeight(display, screen);

	/* Put the window on the screen. */
	XMapRaised( display, window );
	
	XFlush(display);
}

XInfo::~XInfo(){
	// free every gc's
	for (int i = 0; i < NUM_GC_TYPE; i++){
		XFree(gc[i]);
	}
	for (int i = 0; i < NUM_COLOUR; i++){
		XFree(colour_gc[i]);
	}
	for (int i = 0; i < NUM_PIXMAP_TYPE; i++){
		XFreePixmap(display,pixmap[i]);
	}
	XFreeFont(display,title_font_struct);
	XCloseDisplay(display);
}

dim_t XInfo::dwidth(){
	return ddim.first;
}

dim_t XInfo::dheight(){
	return ddim.second;
}

void XInfo::change_window_dim(std::pair<dim_t, dim_t> &tar){
	XResizeWindow(display,window,tar.first,tar.second);
}

Pixmap XInfo::new_pixmap(PIXMAP_TYPE pt, std::pair<dim_t, dim_t> &dim, GC_TYPE gc_type){
	int depth = DefaultDepth(display, DefaultScreen(display));
	XFreePixmap(display,pixmap[pt]);
	pixmap[pt] = XCreatePixmap(display, window, dim.first, dim.second, depth);
	// clean the pixmap
	XFillRectangle(display, pixmap[pt], gc[gc_type], 0, 0, dim.first,dim.second);
	return pixmap[pt];
}
