#ifndef __ALFRED_collision_h__
#define __ALFRED_collision_h__

#include <vector>
#include <utility>
#include "config.h"

class Game;
class Renderer;
class Bomb;
class Player;

class Collision{
	Game &go;
	Renderer &rn;

public:
	Collision(Game &go, Renderer &rn);
	bool operator()(Bomb &mi);
	bool operator()(Player &pl);
	bool inbound(coor_t x, coor_t y);
	bool collide(coor_t x, coor_t y, std::pair<dim_t, dim_t> &dim, coor_t tarx, coor_t tary, std::pair<dim_t, dim_t> &tardim);
	coor_t cannon_x(coor_t s);
	coor_t cannon_y(coor_t t);
};

#endif
