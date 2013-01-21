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
	void setup_cannon_fire();
	unsigned char random_fire_time();
public: /* members */
	Player player;
	std::list <Missile> missiles;
	int xblock_num, yblock_num;
	int num_fires, num_kills, num_b_brakes;

	/* using "maps" for speed and memory optimization -- not in separate class */

	/* map for structures */
	std::vector < std::vector< height_t > > structure_map; // a map of structures

	/* cannon-related maps */
	// a list of cannons, based on the y-axis
	std::vector < height_t > cannon_height_map;
	// counts how many "repaints" before a cannon fires
	std::vector < cannon_fire_time_t > cannon_fire_count;

public: /* functions */
	Game();
	void update(Collision &cl, Renderer &rn);
	void normalize_coor(int &x, int &y);
	int score();
};

#endif
