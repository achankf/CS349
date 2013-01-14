#ifndef __ALFRED_player_h__
#define __ALFRED_player_h__

#include "movable.h"

class Player : public Movable{
public:
	Player(magnitude_t x, magnitude_t y, magnitude_t speedx, magnitude_t speedy);
	virtual void update_position();
	virtual void draw(Renderer &, XInfo &);
	magnitude_t get_width();
	magnitude_t get_height();
};


#endif
