#include <iostream>
#include <X11/keysym.h>
#include "xinfo.h"
#include "game.h"
#include "renderer.h"
#include <unistd.h>
#include <sys/time.h>

using namespace std;

enum ERROR_CODES{
	EXIT
};

unsigned long now() {
	timeval tv;
	gettimeofday(&tv, NULL);
	return tv.tv_sec * 1000000 + tv.tv_usec;
}

void pause(Game &go, XInfo &xinfo, Renderer &rn){
	XEvent event;
	while(true){
		XNextEvent(xinfo.display, &event);
		switch(event.type){
			case Expose:
				rn.repaint(go,xinfo);
				break;
			case KeyPress:
				switch(XLookupKeysym(&event.xkey, 0)){
					case 'P':
					case 'p':
						return; // unpause
					case 'q':
					case 'Q':
						throw EXIT;
				}
		}
	}
}

void handle_keypress(Game &go, Renderer &rn, XInfo &xinfo, XEvent &event){
	auto key_pressed = XLookupKeysym(&event.xkey, 0);
	switch(key_pressed){
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
		case 'P':
		case 'p':
			pause(go,xinfo,rn);
			break;
	}
}

int main(int argc, char **argv){
	XInfo xinfo(argc, argv);
	Game go;
	Renderer rn(go,xinfo);
	Collision cl(go,rn);

	const unsigned long sleep_period = 1000000/FPS;
	unsigned long lastRepaint = 0, end = 0;

	// event loop
	for(XEvent event;;){
		if (XPending(xinfo.display) > 0){
			XNextEvent(xinfo.display,&event);

			// handle pause commands
			switch(event.type){
				case KeyPress:
					try{
						handle_keypress(go, rn, xinfo, event);
					} catch(ERROR_CODES a){
						return 0; // "exit" gracefully -- call deconstructors
					}
					break;
#if 0
				case MapNotify:
					break;
				case ButtonPress:
#endif
			}
		}
		end = now();
		if (end - lastRepaint > sleep_period){
			// update game
			go.update(cl,rn);
			rn.repaint(go,xinfo);
			lastRepaint = now();
		} else if (XPending(xinfo.display) == 0){
			usleep(sleep_period - (end - lastRepaint));
		}
	}
}
