#include "player.h"
#include "config.h"
#include "xinfo.h"
#include "renderer.h"

#ifdef DEBUG
#include "func.h"
#endif

Player::Player(magnitude_t x, magnitude_t y, magnitude_t speedx, magnitude_t speedy) :
	Movable(false,x,y,speedx,speedy,ACCELERATION,ACCELERATION),
	dead(false)
{
}

void Player::update_position(){
	pos.first += velocity.first;
	pos.second += velocity.second;
}

void Player::fire(Game &go){
	go.missiles.push_back(
		Missile(false, getx(),gety(), get_speedx(),get_speedy(),0,MISSILE_INERTIA));
	go.num_fires++;
}

void Player::brake(){
	if (velocity.first > 0){
		velocity.first -= ACCELERATION;
	} else if (velocity.first < 0){
		velocity.first += ACCELERATION;
	}
	if (velocity.second > 0){
		velocity.second -= ACCELERATION;
	} else if (velocity.second < 0){
		velocity.second += ACCELERATION;
	}
}

void Player::emergency_brake(){
	velocity.first = velocity.second = 0;
}

void Player::fit_to_boundary(Renderer &rn){
	magnitude_t right_bound = rn.dim.first + rn.focus - rn.player_dim.first;
	magnitude_t lower_bound = rn.dim.second - rn.player_dim.second;

	if (getx() * rn.resize_factor < rn.focus){
		setx(rn.focus / rn.resize_factor);
		move_forward();
	} else if (getx() * rn.resize_factor > right_bound){
		setx(right_bound / rn.resize_factor);
		move_backward();
	}

	if (gety() < 0){
		sety(0);
		move_down();
	} else if (gety() * rn.resize_factor> lower_bound){
		sety(lower_bound / rn.resize_factor);
		move_up();
	}
}
