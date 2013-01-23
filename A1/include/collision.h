#ifndef __ALFRED_collision_h__
#define __ALFRED_collision_h__

#include <vector>
#include <utility>

class Game;
class Renderer;
class Bomb;
class Player;

class Collision{
	Game &go;
	Renderer &rn;

public:
	Collision(Game &go, Renderer &rn);
	Collision(Renderer &rn, int xblock_num, int yblock_num);
	bool operator()(Bomb &mi);
	bool operator()(Player &pl);
	bool inbound(int x, int y);
	bool collide(int x, int y, std::pair<unsigned int, unsigned int> &dim, int tarx, int tary, std::pair<unsigned int, unsigned int> &tardim);
	int cannon_x(int s);
	int cannon_y(int t);
};

#endif
