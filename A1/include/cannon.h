#ifndef __ALFRED_cannon_h__
#define __ALFRED_cannon_h__

#include "object.h"
#include "weapon_object.h"

class Cannon : public Object, public WeaponObject {
public: /* member */
public: /* functions */
	Cannon(magnitude_t x, magnitude_t y);
	void draw(Renderer &, XInfo &);
};

#endif
