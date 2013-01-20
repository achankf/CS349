#include <iostream>
#include <X11/keysym.h>
#include "xinfo.h"
#include "game.h"
#include "renderer.h"
#include <unistd.h>
#include <sys/time.h>

using namespace std;

enum ERROR_CODES{
	EXIT, RETRY
};

unsigned long now() {
	timeval tv;
	gettimeofday(&tv, NULL);
	return tv.tv_sec * 1000000 + tv.tv_usec;
}

void handle_keypress(Game &go, Renderer &rn, XInfo &xinfo, XEvent &event,
	bool *redraw_splash){
	auto key_pressed = XLookupKeysym(&event.xkey, 0);
	switch(key_pressed){
		case 'R':
		case 'r':
			throw RETRY;
			break;
		case 'k': // vim bindings :) -- fall-through
		case XK_Up:
			go.player.move_up();
			break;
		case 'j': // vim bindings :) -- fall-through
		case XK_Down:
			go.player.move_down();
			break;
		case 'h': // vim bindings :) -- fall-through
		case XK_Left:
			go.player.move_backward();
			break;
		case 'l': // vim bindings :) -- fall-through
		case XK_Right:
			go.player.move_forward();
			break;
		case 'Z':
		case 'z':
			go.player.fire(go);
			break;
		case 'X':
		case 'x':
			go.player.brake();
			break;
		case 'B':
		case 'b':
			go.player.emergency_brake(); // aka "easy" mode
			break;
		case 'Q':
		case 'q':
			throw EXIT;
		case 'F':
		case 'f':
			*redraw_splash = true;
			rn.show_splash = !rn.show_splash;
			break;
	}
}

ERROR_CODES event_loop(int argc, char **argv, XInfo &xinfo){
	Game go;
	Renderer rn(go,xinfo);
	Collision cl(go,rn);

	const unsigned long sleep_period = 1000000/FPS;
	unsigned long lastRepaint = 0, end = 0;

	bool redraw_splash = true;

	// event loop
	for(XEvent event;;){
		if (XPending(xinfo.display) > 0){
			XNextEvent(xinfo.display,&event);

			// handle pause commands
			switch(event.type){
				case KeyPress:
					try{
						handle_keypress(go, rn, xinfo, event, &redraw_splash);
					} catch(ERROR_CODES err){
						// "exit" or "retry" gracefully -- call deconstructors
						return err; 
					}
					break;
				case Expose:
					redraw_splash = true;
			}
		}
		end = now();

		if (end - lastRepaint > sleep_period){
			// either draw splash screen or update game
			if (rn.show_splash){
				if (redraw_splash){ // redraw only if the splash is damaged
					rn.draw_splash(go,xinfo);
				}
			} else {
				// update game
				go.update(cl,rn);
				rn.repaint(go,xinfo);
			}
		} else {
			goto NOT_DRAWN_ANYTHING;
		}

		// reset expose flag
		redraw_splash = false;
		// update time
		lastRepaint = now();

NOT_DRAWN_ANYTHING:
		if (XPending(xinfo.display) == 0){
			usleep(sleep_period - (end - lastRepaint));
		}
	}
}

int main(int argc, char **argv){
	XInfo xinfo(argc, argv);
	// loop to an infinite loop 
	// take advantage of scopings and destructors for retries
	while(event_loop(argc, argv, xinfo) != EXIT);
}
