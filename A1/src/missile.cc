#include "missile.h"
#include "config.h"
#include "xinfo.h"
#include "renderer.h"

#ifdef DEBUG
#include "func.h"
#endif

Missile::Missile(magnitude_t x, magnitude_t y, magnitude_t speedx, magnitude_t speedy) :
	Movable(x,y,speedx,speedy)
{
}

void Missile::draw(Renderer &rn, XInfo &xinfo){
	Display *display = xinfo.display;
	GC gc = xinfo.gc[XInfo::DEFAULT];
	Pixmap pixmap = xinfo.pixmap;
	magnitude_t x = getx() - rn.focus;
	magnitude_t y = gety();
	XFillRectangle(display, pixmap, gc, x, y, MISSILE_WIDTH, MISSILE_HEIGHT);
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

magnitude_t Missile::get_width(){
	return MISSILE_WIDTH;
}
magnitude_t Missile::get_height(){
	return MISSILE_HEIGHT;
}
