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
	xblock_num(XBLOCK_NUM),
	yblock_num(YBLOCK_NUM),
	structure_map(xblock_num, std::vector<height_t>(yblock_num, false)),
	cannon_height_map(xblock_num, NO_CANNON)
{
	setup();
}

void Game::setup(){

	cannon_fire_count.reserve(xblock_num);
	for (int i = 0; i < xblock_num; i++){
		cannon_fire_count[i] = random_fire_time();
	}

	for (int x = 0, y; x < xblock_num; x++){
		bool make = rand() % 2;
		if (!make) continue;
		y = yblock_num - 2;
		structure_map[x][y] = true; // or something NOT -1 (NO_CANNON)
	}
	
	int temp = yblock_num - 1;
	for (int i = 0; i < xblock_num; i++){
		structure_map[i][temp] = true;
	}

	for (int x = 0; x < xblock_num; x++){
		for (int y = yblock_num - 1; y >= 0; y--){ // search from the bottom
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

	for (auto it = missiles.begin(); it != missiles.end(); it++){
		it->update_position();
	}
	missiles.remove_if(cl); // collide

	// cannons fire missiles
	for (int x = rn.focus_bound_low; x < rn.focus_bound_high && x < xblock_num; x++){
		if (cannon_height_map[x] == NO_CANNON) continue;
		cannon_fire_count[x]--;
		if (cannon_fire_count[x] != 0) continue;
		cannon_fire_count[x] = random_fire_time();
		missiles.push_back(
			Missile(true, 
				(x * rn.xblocksize + rn.xblocksize / 2),
				(cannon_height_map[x] - 1) * rn.yblocksize,
				0, 0,
				0, -MISSILE_INERTIA));
	}
}

#if 0
void Game::normalize_coor(int &x, int &y){
	if (x < 0){
		x = 0;
	} else if (x >= xblock_num){
		x = xblock_num - 1;
	}
	if (y < 0){
		y = 0;
	} else if (y >= yblock_num){
		y = yblock_num - 1;
	}
}
#endif
