#ifndef __ALFRED_missile_h__
#define __ALFRED_missile_h__

#include "movable.h"

class Missile : public Movable{
public:
	Missile(magnitude_t x, magnitude_t y, magnitude_t speedx, magnitude_t speedy);
	virtual void update_position();
	virtual void draw(Renderer &, XInfo &);
	magnitude_t get_width();
	magnitude_t get_height();
};
#endif
