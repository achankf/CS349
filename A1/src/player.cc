#include "player.h"
#include "config.h"
#include "xinfo.h"
#include "renderer.h"

#ifdef DEBUG
#include "func.h"
#endif

Player::Player(magnitude_t x, magnitude_t y, magnitude_t speedx, magnitude_t speedy) :
	Movable(false,x,y,speedx,speedy,ACCELERATION,ACCELERATION),
	dead(false),
	fire_cool_down(0)
{
}

void Player::update_position(){
	pos.first += velocity.first;
	pos.second += velocity.second;
}

void Player::fire(Game &go){
	if (fire_cool_down > 0){
		return;
	}
	fire_cool_down = PLAYER_FIRE_COOL_DOWN;
	go.bombs.push_back(
		Bomb(false, getx(),gety(), get_speedx(),get_speedy(),0,BOMB_INERTIA));
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

	if (getx() < rn.focus){
		setx(rn.focus);
		move_forward();
	} else if (getx() > right_bound){
		setx(right_bound);
		move_backward();
	}

	if (gety() < 0){
		sety(0);
		move_down();
	} else if (gety() > lower_bound){
		sety(lower_bound);
		move_up();
	}
}
