#include "missile.h"
#include "config.h"
#include "xinfo.h"
#include "renderer.h"

#ifdef DEBUG
#include "func.h"
#endif

Missile::Missile(bool team, magnitude_t x, magnitude_t y,
		magnitude_t speedx, magnitude_t speedy, magnitude_t accx, magnitude_t accy) :
	Movable(team,x,y,speedx,speedy,accx,accy)
{
}

void Missile::draw(Renderer &rn, XInfo &xinfo){
	Display *display = xinfo.display;
	GC gc = xinfo.gc[XInfo::DEFAULT];
	Pixmap pixmap = xinfo.pixmap[XInfo::GAME_SCREEN];
	magnitude_t x = getx() - rn.focus;
	magnitude_t y = gety();
	XFillRectangle(display, pixmap, gc, x, y, MISSILE_WIDTH * rn.resize_factor, MISSILE_HEIGHT * rn.resize_factor);
}

void Missile::update_position(){
	if (velocity.first > 0){
		velocity.first -= MISSILE_INERTIA;
		pos.first += velocity.first;
	} else if (velocity.first < 0){
		velocity.first += MISSILE_INERTIA;
		pos.first += velocity.first;
	}
	velocity.second += acceleration.second;
	pos.second += velocity.second;
}
