#include "bomb.h"
#include "config.h"
#include "xinfo.h"
#include "renderer.h"

#ifdef DEBUG
#include "func.h"
#endif

Bomb::Bomb(bool team, magnitude_t x, magnitude_t y,
		magnitude_t speedx, magnitude_t speedy, magnitude_t accx, magnitude_t accy) :
	Movable(team,x,y,speedx,speedy,accx,accy)
{
}

void Bomb::update_position(){
	if (velocity.first > 0){
		velocity.first -= BOMB_INERTIA;
		pos.first += velocity.first;
	} else if (velocity.first < 0){
		velocity.first += BOMB_INERTIA;
		pos.first += velocity.first;
	}
	velocity.second += acceleration.second;
	pos.second += velocity.second;
}
