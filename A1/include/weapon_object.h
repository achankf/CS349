#ifndef __ALFRED_weapon_object_h__
#define __ALFRED_weapon_object_h__

class Game;

class WeaponObject{
	virtual void fire(Game &) = 0;
};

#endif
