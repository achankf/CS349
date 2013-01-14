#ifndef __ALFRED_game_h__
#define __ALFRED_game_h__

#include <list>
#include <vector>

#include "missile.h"
#include "player.h"
#include "renderer.h"
#include "collision.h"

// place holder
class Renderer;
class Collision;
class Missile;
class Player;

class Game {
public: /* members */
	Player player;
	std::list <Missile> missiles;
	int xblock_num, yblock_num;
	std::vector < std::vector<char> > structure_map; // a map of structures

public: // enum
	enum DEFAULT{
		XBLOCK_NUM = 600,
		YBLOCK_NUM = 16,
		MAX_OBJECTS = 1000
	};

public: /* functions */
	Game();
	void update(Collision &cl, Renderer &rn);
	void normalize_coor(int &x, int &y);
};

#endif
