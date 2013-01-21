#include "game.h"
#include <utility>
#include <unistd.h>

#ifdef DEBUG
#include <iostream>
#include "func.h"
#endif
using namespace std;

cannon_fire_time_t Game::random_fire_time(){
	return (rand() & 0x3f) + CANNON_MIN_FIRE_TIME;
}

Game::Game() : player(50,50,0,0),
	num_fires(0),
	num_kills(0),
	num_b_brakes(0),
	god_mode(false),
	structure_map(XBLOCK_NUM, std::vector<height_t>(YBLOCK_NUM, false)),
	cannon_height_map(XBLOCK_NUM, NO_CANNON)
{
	setup();
}

void Game::setup(){

	cannon_fire_count.reserve(XBLOCK_NUM);
	for (int i = 0; i < XBLOCK_NUM; i++){
		cannon_fire_count[i] = random_fire_time();
	}

	for (int x = 0, y; x < XBLOCK_NUM; x++){
		bool make = rand() % 2;
		if (!make) continue;
		y = YBLOCK_NUM - 2;
		structure_map[x][y] = true; // or something NOT -1 (NO_CANNON)
	}
	
	int temp = YBLOCK_NUM - 1;
	for (int i = 0; i < XBLOCK_NUM; i++){
		structure_map[i][temp] = true;
	}

	for (int x = 0; x < XBLOCK_NUM; x++){
		for (int y = YBLOCK_NUM - 1; y >= 0; y--){ // search from the bottom
			// requires that (x,y) doesn't contain a structure
			if (structure_map[x][y]) continue;
			if ((rand() % CANNON_SPAWN_TOTAL) <= CANNON_SPAWN){
				cannon_height_map[x] = y;
			}
			break;
		}
	}
}

void Game::update(Collision &cl, Renderer &rn){
	player.update_position();
	player.fit_to_boundary(rn);
	if (cl(player)) player.dead = true;

	for (auto it = missiles.begin(); it != missiles.end(); it++){
		it->update_position();
	}
	missiles.remove_if(cl); // collide

	// cannons fire missiles
	for (int x = rn.focus_bound_low; x < rn.focus_bound_high && x < XBLOCK_NUM; x++){
		if (cannon_height_map[x] == NO_CANNON) continue;
		cannon_fire_count[x]--;
		if (cannon_fire_count[x] != 0) continue;
		cannon_fire_count[x] = random_fire_time();
		missiles.push_back(
			Missile(true, 
				(x * BLOCK_SIDE_LEN + BLOCK_SIDE_LEN / 2),
				(cannon_height_map[x] - 1) * BLOCK_SIDE_LEN,
				0, 0,
				0, -MISSILE_INERTIA));
	}
}

int Game::score(){
	return (num_kills * KILL_BONUS
		- num_fires * FIRE_PENALTY
		- num_b_brakes * BRAKE_PENALTY);
}
