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
	void setup();
public: /* members */
	Player player;
	std::list <Missile> missiles;
	int xblock_num, yblock_num;
	std::vector < std::vector<char> > structure_map; // a map of structures
	std::vector < char > cannon_height_map; // a list of cannons, based on the y-axis

public: // enum
	enum DEFAULT{
		XBLOCK_NUM = 600,
		YBLOCK_NUM = 16,
		MAX_OBJECTS = 1000,
		NO_CANNON = -1 // ==> cannon's height max 254
	};

public: /* functions */
	Game();
	void update(Collision &cl, Renderer &rn);
	void normalize_coor(int &x, int &y);
};

#endif
