#include "collision.h"

Collision::Collision(Game &go, Renderer &rn)
: go(go),
	rn(rn)
{
}

bool Collision::operator()(Missile &mi){
	magnitude_t x = mi.getx(), y = mi.gety();
	int s = x / rn.get_xblocksize();
	int t = y / rn.get_yblocksize();
	go.normalize_coor(s,t);
	if (go.structure_map[s][t]){
		return true;
	}
	return false;
}
