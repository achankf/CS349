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

magnitude_t Player::get_width(){
	return PLAYER_WIDTH;
}

magnitude_t Player::get_height(){
	return PLAYER_HEIGHT;
}
