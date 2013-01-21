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

void Player::draw(Renderer &rn, XInfo &xinfo){
	std::pair<unsigned int, unsigned int> dim( PLAYER_WIDTH * rn.resize_factor, PLAYER_HEIGHT * rn.resize_factor);
	Pixmap pixmap = xinfo.new_pixmap(XInfo::PPLAYER,dim);

	Display *display = xinfo.display;
	GC gc = xinfo.gc[XInfo::DEFAULT];
	magnitude_t x = getx() * rn.resize_factor - rn.focus;
	magnitude_t y = gety() * rn.resize_factor;
	XFillRectangle(display, pixmap, gc, 0, 0, PLAYER_WIDTH * rn.resize_factor, PLAYER_HEIGHT * rn.resize_factor);
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
	magnitude_t right_bound = rn.dim.first + rn.focus - PLAYER_WIDTH;
	magnitude_t lower_bound = rn.dim.second - PLAYER_HEIGHT;

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
