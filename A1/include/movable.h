#ifndef __ALFRED_movable_h__
#define __ALFRED_movable_h__

#include "object.h"
#include <utility>

typedef float magnitude_t;
typedef float acc_t;

namespace movable{
	enum DIRECTION{
		UP,DOWN,FORWARD,BACKWARD
	};
}

class Movable : public Object {
protected:
	magnitude_t acceleration;

public: /* member */
	std::pair <magnitude_t,magnitude_t> velocity;

public: /* functions */
	Movable(magnitude_t x, magnitude_t y, magnitude_t speedx, magnitude_t speedy);
	void decelerate();
	void move_up();
	void move_down();
	void move_forward();
	void move_backward();
	virtual void update_position();
	magnitude_t get_speedx();
	magnitude_t get_speedy();
};

#endif
