#ifndef __ALFRED_bomb_h__
#define __ALFRED_bomb_h__

#include "movable.h"

class Bomb : public Movable{
public: /* functions */
	Bomb(bool team, magnitude_t x, magnitude_t y,
		magnitude_t speedx, magnitude_t speedy, magnitude_t accx, magnitude_t accy);
	virtual void update_position();
};
#endif
