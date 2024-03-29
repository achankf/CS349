#ifndef __ALFRED_player_h__
#define __ALFRED_player_h__

#include "movable.h"
#include "weapon_object.h"

class Player : public Movable, public WeaponObject{
public:
	bool dead;
	int fire_cool_down;
public:
	Player(magnitude_t x, magnitude_t y, magnitude_t speedx, magnitude_t speedy);
	virtual void update_position();
	void fire(Game &);
	void brake();
	void emergency_brake();

	// adjust player position so that the helicopter will not go out of bound
	void fit_to_boundary(Renderer &);
};
#endif
