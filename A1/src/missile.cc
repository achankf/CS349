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
	// create a new pixmap
	Pixmap pixmap = xinfo.new_pixmap(XInfo::PPLAYER, rn.missile_dim);
	Display *display = xinfo.display;
	GC gc = xinfo.gc[XInfo::DEFAULT];
	XFillRectangle(display, pixmap, gc, 0, 0, rn.missile_dim.first, rn.missile_dim.second);
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
