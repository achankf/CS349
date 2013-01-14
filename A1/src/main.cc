#include <iostream>
#include <X11/keysym.h>
#include "xinfo.h"
#include "game.h"
#include "renderer.h"
#include <unistd.h>
#include <sys/time.h>

using namespace std;

unsigned long now() {
	timeval tv;
	gettimeofday(&tv, NULL);
	return tv.tv_sec * 1000000 + tv.tv_usec;
}

void handle_keypress(Game &go, Renderer &rn, XInfo &xinfo, XEvent &event){
	auto key_pressed = XLookupKeysym(&event.xkey, 0);
	switch(key_pressed){
		case XK_Up:
			go.player.move_up();
			break;
		case XK_Down:
			go.player.move_down();
			break;
		case XK_Left:
			go.player.move_backward();
			break;
		case XK_Right:
			go.player.move_forward();
			break;
		case 'q':
			throw 0; // exit
		case 'z':
			go.player_fire();
	}
}

int main(int argc, char **argv){
	XInfo xinfo(argc, argv);
	Game go;
	Renderer rn(&go,&xinfo);
	Collision cl(go,rn);

	const unsigned long sleep_period = 1000000/FPS;
	unsigned long lastRepaint = 0, end = 0;

	for(XEvent event;;){
		if (XPending(xinfo.display) > 0){
			XNextEvent(xinfo.display,&event);
			switch (event.type){
				case MapNotify:
					break;
				case KeyPress:
					try{
						handle_keypress(go, rn, xinfo, event);
					} catch(int a){
						// "exit" gracefully -- call deconstructors
						return 0; 
					}
				//case ButtonPress:
			}
		}
		end = now();
		if (end - lastRepaint > sleep_period){
			go.update(cl,rn);
			rn.repaint();
			lastRepaint = now();
		} else if (XPending(xinfo.display) == 0){
			usleep(sleep_period - (end - lastRepaint));
		}
	}
	
}
