#ifndef __ALFRED_game_h__
#define __ALFRED_game_h__

#include <list>
#include <vector>

#include "bomb.h"
#include "player.h"
#include "renderer.h"
#include "collision.h"

// place holder
class Renderer;
class Collision;
class Bomb;
class Player;

class Game {
	void setup();
	void setup_cannon_fire();
	unsigned char random_fire_time();
	void correct_structure_map();
	void update_scroll_factor();
	void generate_structure_by_height(int height, int from, int to, bool build_structure, int rand_modulo, int base_rand, int rand_target);

public: /* members */
	Player player;
	std::list <Bomb> bombs;
	int num_fires, num_kills, num_b_brakes;
	bool god_mode, propel, game_over;

	/* using "maps" for speed and memory optimization -- not in separate class */

	/* map for structures */
	std::vector < std::vector< height_t > > structure_map;

	/* cannon-related maps -- a list of cannons, based on the y-axis */
	std::vector < height_t > cannon_height_map;
	// counts how many "repaints" before a cannon fires
	std::vector < cannon_fire_time_t > cannon_fire_count;

public: /* functions */
	Game();
	void update(Collision &cl, Renderer &rn);
	int score();
};

#endif
