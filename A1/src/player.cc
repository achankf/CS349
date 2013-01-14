#include "player.h"
#include "config.h"
#include "xinfo.h"
#include "renderer.h"

#ifdef DEBUG
#include "func.h"
#endif

Player::Player(magnitude_t x, magnitude_t y, magnitude_t speedx, magnitude_t speedy) :
	Movable(x,y,speedx,speedy)
{
}

void Player::draw(Renderer &rn, XInfo &xinfo){
	Display *display = xinfo.display;
	GC gc = xinfo.gc[xinfo::DEFAULT];
	Pixmap pixmap = xinfo.pixmap;
	magnitude_t x = getx() - rn.focus;
	magnitude_t y = gety();
	XFillRectangle(display, pixmap, gc, x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
}

void Player::update_position(){
	pos.first += velocity.first;
	pos.second += velocity.second;
}

void Player::fire(Game &go){
	go.missiles.push_back(Missile(getx(),gety(),get_speedx(),get_speedy()));
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
