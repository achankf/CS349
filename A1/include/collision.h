#ifndef __ALFRED_collision_h__
#define __ALFRED_collision_h__

#include <vector>

class Game;
class Renderer;
class Missile;

class Collision{
	Game &go;
	Renderer &rn;
public:
	Collision(Game &go, Renderer &rn);
	Collision(Renderer &rn, int xblock_num, int yblock_num);
	bool operator()(Missile &mi);
};

#endif
