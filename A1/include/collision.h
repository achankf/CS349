#ifndef __ALFRED_collision_h__
#define __ALFRED_collision_h__

#include <vector>
#include "collision.h"
#include "renderer.h"
#include "missile.h"

class Game;
class Renderer;

class Collision{
	Game &go;
	Renderer &rn;
public:
	Collision(Game &go, Renderer &rn);
	Collision(Renderer &rn, int xblock_num, int yblock_num);
	bool operator()(Missile &mi);
};

#endif
