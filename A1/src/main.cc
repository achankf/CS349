#include <iostream>
#include <X11/keysym.h>
#include "xinfo.h"
#include "game.h"
#include "renderer.h"
#include <unistd.h>
#include <sys/time.h>

#ifdef DEBUG
#include "func.h"
#endif

enum KeyPressedRecordType{
	KEYP_UP,
	KEYP_DOWN,
	KEYP_LEFT,
	KEYP_RIGHT,
	KEYP_FIRE,
	KEYP_BRAKE,
	ENUM_KEY_PRESS_SIZE
};

using namespace std;

enum ERROR_CODES{
	EXIT, RETRY
};

unsigned long now() {
	timeval tv;
	gettimeofday(&tv, NULL);
	return tv.tv_sec * 1000000 + tv.tv_usec;
}

void update_game_helicopter_action(Game &go,bool keypressed_record[]){
	if (keypressed_record[KEYP_UP])
		go.player.move_up();
	if (keypressed_record[KEYP_DOWN])
		go.player.move_down();
	if (keypressed_record[KEYP_RIGHT])
		go.player.move_forward();
	if (keypressed_record[KEYP_LEFT])
		go.player.move_backward();
	if (keypressed_record[KEYP_FIRE])
		go.player.fire(go);
	if (keypressed_record[KEYP_BRAKE]){
		go.player.brake();
		go.propel = false;
	}
}

void handle_keypress_special(XEvent &event, bool keypressed_record[]){
	int key_pressed = XLookupKeysym(&event.xkey, 0);
	switch(key_pressed){
		case 'k': // vi key bindings :) -- fall-through
		case XK_Up:
			keypressed_record[KEYP_UP] = !keypressed_record[KEYP_UP];
			break;
		case 'j': // vi key bindings :) -- fall-through
		case XK_Down:
			keypressed_record[KEYP_DOWN] = !keypressed_record[KEYP_DOWN];
			break;
		case 'h': // vi key bindings :) -- fall-through
		case XK_Left:
			keypressed_record[KEYP_LEFT] = !keypressed_record[KEYP_LEFT];
			break;
		case 'l': // vi key bindings :) -- fall-through
		case XK_Right:
			keypressed_record[KEYP_RIGHT] = !keypressed_record[KEYP_RIGHT];
			break;
		case 'Z':
		case 'z':
			keypressed_record[KEYP_FIRE] = !keypressed_record[KEYP_FIRE];
			break;
		case 'X':
		case 'x':
			keypressed_record[KEYP_BRAKE] = !keypressed_record[KEYP_BRAKE];
			break;
	}
}
void handle_keypress(Game &go, Renderer &rn, XInfo &xinfo, XEvent &event, bool keypressed_record[]){
	int key_pressed = XLookupKeysym(&event.xkey, 0);
	handle_keypress_special(event,keypressed_record);
	switch(key_pressed){
		case 'R':
		case 'r':
			throw RETRY;
			break;
		case 'B': // emergency brake
		case 'b':
			go.propel = false;
			go.player.emergency_brake();
			go.num_b_brakes++;
			break;
		case 'Q':
		case 'q':
			throw EXIT;
		case 'F':
		case 'f':
			rn.show_splash = !rn.show_splash;
			break;
		case 'P':
		case 'p':
			go.player.dead = true;
			break;
		case 'G':
		case 'g':
			go.god_mode = !go.god_mode;
			break;
		case 'c':
		case 'C':
			go.propel = !go.propel;
			break;
		case '[':
			rn.scroll -= 0.5;
			if (rn.scroll < -MAX_SCROLL_FACTOR) rn.scroll = -MAX_SCROLL_FACTOR;
			break;
		case ']':
			rn.scroll += 0.5;
			if (rn.scroll > MAX_SCROLL_FACTOR) rn.scroll = MAX_SCROLL_FACTOR;
			break;
			break;
	}
}

void handle_resize(XEvent &event,Game &go, Renderer &rn, XInfo &xinfo){
	XWindowAttributes windowInfo;
	XGetWindowAttributes(xinfo.display, xinfo.window, &windowInfo);
	dim_t new_width = windowInfo.width;
	dim_t new_height = windowInfo.height;

	// check whether there's change
	if (rn.dim.first == new_width && rn.dim.second == new_height) return;
	rn.update_attributes(go, xinfo, new_width, new_height);

	xinfo.change_window_dim(rn.dim);
}

ERROR_CODES event_loop(int argc, char **argv, XInfo &xinfo){
	Game go;
	Renderer rn(go,xinfo);
	Collision cl(go,rn);
	bool keypressed_record[ENUM_KEY_PRESS_SIZE] = {false};

	const unsigned long sleep_period = 1000000/FPS;
	unsigned long lastRepaint = 0, end = 0;

	// event loop
	for(XEvent event;;){
		if (XPending(xinfo.display) > 0){
			XNextEvent(xinfo.display,&event);

			// handle pause commands
			switch(event.type){
				case KeyRelease:
					handle_keypress_special(event, keypressed_record);
					break;
				case KeyPress:
					try{
						handle_keypress(go, rn, xinfo, event, keypressed_record);
					} catch(ERROR_CODES err){
						// "exit" or "retry" gracefully -- call deconstructors
						return err; 
					}
					break;
			}
		}
		end = now();

		if (end - lastRepaint <= sleep_period)
			goto SKIP_DRAWING;

		// either draw splash screen or update game
		if (event.type == ConfigureNotify){
			// consume the rest of the if
		} else if (go.game_over || go.player.dead){ // be it dead of alive
			rn.draw_game_over(go,xinfo);
		} else if (rn.show_splash){
			rn.draw_splash(go,xinfo);
		} else {
			// update game
			update_game_helicopter_action(go,keypressed_record);
			go.update(cl,rn);
			rn.repaint(go,xinfo);
		}

		handle_resize(event,go,rn,xinfo);

		// update time
		lastRepaint = now();

SKIP_DRAWING:
		if (XPending(xinfo.display) == 0){
			usleep(sleep_period - (end - lastRepaint));
		}
	}
}

int main(int argc, char **argv){
	XInfo xinfo(argc, argv);
	srand(now());
	// a loop to infinite loops
	// take advantage of scopings and destructors for retries
	while(event_loop(argc, argv, xinfo) != EXIT);
}
