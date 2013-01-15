#ifndef __ALFRED_movable_h__
#define __ALFRED_movable_h__

#include "object.h"
#include <utility>

class Renderer;

class Movable : public Object {
protected:
	magnitude_t acceleration;

public: /* member */
	std::pair <magnitude_t,magnitude_t> velocity;

public: /* functions */
	Movable(magnitude_t x, magnitude_t y, magnitude_t speedx, magnitude_t speedy);
	void move_up();
	void move_down();
	void move_forward();
	void move_backward();
	virtual void update_position() = 0;
	virtual void draw(Renderer &, XInfo &) = 0;
	magnitude_t get_speedx() const;
	magnitude_t get_speedy() const;
	void set_speedx(magnitude_t);
	void set_speedy(magnitude_t);
};

#endif
