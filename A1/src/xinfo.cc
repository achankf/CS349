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

	XSelectInput(display,window, StructureNotifyMask | KeyPressMask | ExposureMask);
		
	XSetStandardProperties(
		display,		// display containing the window
		window,		// window whose properties are set
		GAME_TITLE,	// window's title
		"OW",				// icon's title
		None,				// pixmap for the icon
		argv, argc,			// applications command line args
		&hints );			// size hints for the window

	// allocate gc's
	for (int i = 0; i < NUM_GC_TYPE; i++){
		gc[i] = XCreateGC(display, window, 0, 0);
	}

	/* create graphic contexts */
	XSetForeground(display, gc[DEFAULT], black);
	XSetBackground(display, gc[DEFAULT], white);
	XSetFillStyle(display, gc[DEFAULT], FillSolid);
	XSetLineAttributes(display, gc[DEFAULT], 1, LineSolid, CapButt, JoinRound);

	XSetForeground(display, gc[INVERSE_BACKGROUND], white);
	XSetBackground(display, gc[INVERSE_BACKGROUND], black);

	/* allocate fonts */
	font[F_TITLE] = XLoadFont(display,"*x16");
	XSetForeground(display, gc[TITLE_FONT], black);
	XSetBackground(display, gc[TITLE_FONT], white);
	XSetFont(display,gc[TITLE_FONT], font[F_TITLE]);

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
	for (int i = 0; i < NUM_PIXMAP_TYPE; i++){
		XFreePixmap(display,pixmap[i]);
	}
	for (int i = 0; i < NUM_FONT_TYPE; i++){
		XUnloadFont(display,font[i]);
	}
	XCloseDisplay(display);
}

unsigned int XInfo::dwidth(){
	return ddim.first;
}

unsigned int XInfo::dheight(){
	return ddim.second;
}

void XInfo::change_window_dim(std::pair<unsigned int, unsigned int> &tar){
	XResizeWindow(display,window,tar.first,tar.second);
}

Pixmap XInfo::new_pixmap(PIXMAP_TYPE pt, std::pair<unsigned int, unsigned int> &dim){
	int depth = DefaultDepth(display, DefaultScreen(display));
	XFreePixmap(display,pixmap[pt]);
	pixmap[pt] = XCreatePixmap(display, window, dim.first, dim.second, depth);
	// clean the pixmap
	XFillRectangle(display, pixmap[pt], gc[INVERSE_BACKGROUND], 0, 0, dim.first,dim.second);
	return pixmap[pt];
}
