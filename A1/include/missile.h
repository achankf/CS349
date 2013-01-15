#ifndef __ALFRED_missile_h__
#define __ALFRED_missile_h__

#include "movable.h"

class Missile : public Movable{
public: /* member */
	bool enemy;
public: /* functions */
	Missile(bool enemy, magnitude_t x, magnitude_t y,
		magnitude_t speedx, magnitude_t speedy, magnitude_t accx, magnitude_t accy);
	virtual void update_position();
	virtual void draw(Renderer &, XInfo &);
};
#endif
