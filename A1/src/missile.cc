#include "missile.h"
#include "config.h"

#ifdef DEBUG
#include "func.h"
#endif

Missile::Missile(magnitude_t x, magnitude_t y, magnitude_t speedx, magnitude_t speedy) :
	Movable(x,y,speedx,speedy)
{
}

void Missile::update_position(){
	if (velocity.first > 0){
		velocity.first -= MISSILE_INERTIA;
		pos.first += velocity.first;
	} else if (velocity.first < 0){
		velocity.first += MISSILE_INERTIA;
		pos.first += velocity.first;
	}
	pos.second += MISSLE_SPEED;
}
