#include <cstdlib>
#include <unistd.h>
#include "xinfo.h"
#include "func.h"
#include "renderer.h"
#include "config.h"

#include <X11/Xlib.h>
#include <X11/Xutil.h>
using namespace xinfo;

const char *TITLE = "Game";

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

	XSelectInput(display,window,StructureNotifyMask | ButtonPressMask | KeyPressMask | ExposureMask);
		
	XSetStandardProperties(
		display,		// display containing the window
		window,		// window whose properties are set
		TITLE,	// window's title
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

	int depth = DefaultDepth(display, DefaultScreen(display));
	pixmap = XCreatePixmap(display, window, hints.width, hints.height, depth);

	dwidth = DisplayWidth(display, screen);
	dheight = DisplayHeight(display, screen);

	/* Put the window on the screen. */
	XMapRaised( display, window );
	
	XFlush(display);
}

XInfo::~XInfo(){
	// free every gc's
	for (int i = 0; i < NUM_GC_TYPE; i++){
		XFree(gc[i]);
	}
	XFreePixmap(display,pixmap);
	XCloseDisplay(display);
}
