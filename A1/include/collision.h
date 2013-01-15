#ifndef __ALFRED_collision_h__
#define __ALFRED_collision_h__

#include <vector>

class Game;
class Renderer;
class Missile;
class Object;
class Movable;

class Collision{
	Game &go;
	Renderer &rn;
public:
	Collision(Game &go, Renderer &rn);
	Collision(Renderer &rn, int xblock_num, int yblock_num);
//	bool operator()(Missile &mi);
	bool operator()(Object &mi);
	bool inbound(int x, int y);
};

#endif
