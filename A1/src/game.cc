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

Game::Game() : 
	_scroll_factor(SCROLL_FACTOR),
	player(DEFAULT_HEIGHT/2,50,0,0),
	num_fires(0),
	num_kills(0),
	num_b_brakes(0),
	god_mode(false),
	structure_map(XBLOCK_NUM, std::vector<height_t>(YBLOCK_NUM, false)),
	cannon_height_map(XBLOCK_NUM, NO_CANNON)
{
	setup();
}

void Game::correct_structure_map(){
	for (int x = 0, count; x < XBLOCK_NUM; x++){
		for (int y = 0; y < YBLOCK_NUM; y++){
			count = 0;
			if (x == 0){
				count ++;
			} else {
				count += structure_map[x-1][y];
			}
			if (y == 0){
				count++;
			} else {
				count += structure_map[x][y-1];
			}
			if (y == YBLOCK_NUM - 1){
				count++;
			} else {
				count += structure_map[x][y+1];
			}
			if (x == XBLOCK_NUM - 1){
				count++;
			} else {
				count += structure_map[x+1][y];
			}

			if (count >= 3){
				structure_map[x][y] = true;
			}
		}
	}

}

void Game::setup(){

	cannon_fire_count.reserve(XBLOCK_NUM);
	for (int i = 0; i < XBLOCK_NUM; i++){
		cannon_fire_count[i] = random_fire_time();
	}

	int max_depth = YBLOCK_NUM / 6;
	for (int x = 0 /* XBLOCK_NUM */; x < XBLOCK_NUM; x++){
		for (int y = 0; y < YBLOCK_NUM; y++){
			bool make = rand() % 2;
			if (!make || (y >= max_depth &&  y < YBLOCK_NUM - max_depth)) continue;
			structure_map[x][y] = true;
		}
	}
	max_depth = YBLOCK_NUM / 3;
	for (int x = 0 /* XBLOCK_NUM */; x < XBLOCK_NUM; x++){
		for (int y = 0; y < YBLOCK_NUM; y++){
			bool make = rand() % 2;
			if (!make || (y >= max_depth &&  y < YBLOCK_NUM - max_depth)) continue;
			structure_map[x][y] = true;
		}
	}
	
	int temp = YBLOCK_NUM - 1;
	for (int i = 0; i < XBLOCK_NUM; i++){
		structure_map[i][temp] = true;
	}
	correct_structure_map();
	correct_structure_map();

	for (int x = 0; x < XBLOCK_NUM; x++){
		for (int y = YBLOCK_NUM - 1; y >= 0; y--){ // search from the bottom
			// requires that (x,y) doesn't contain a structure
			if (structure_map[x][y] || structure_map[x][y-1] || structure_map[x][y-2]) continue;
			if ((rand() % CANNON_SPAWN_TOTAL) <= CANNON_SPAWN){
				cannon_height_map[x] = y;
			}
			break;
		}
	}
}

void Game::update(Collision &cl, Renderer &rn){
	update_scroll_factor();

	// decrease cool down
	player.fire_cool_down--;
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
				(x * rn.final_blockside_len + rn.final_blockside_len / 2),
				(cannon_height_map[x] - 1) * rn.final_blockside_len,
				0, 0,
				0, -MISSILE_INERTIA));
	}
}

int Game::score(){
	return (num_kills * KILL_BONUS
		- num_fires * FIRE_PENALTY
		- num_b_brakes * BRAKE_PENALTY);
}

void Game::update_scroll_factor(){
	_scroll_factor += SCROLL_DELTA;
	if (_scroll_factor > SCROLL_FACTOR_MAX ){
		_scroll_factor = SCROLL_FACTOR_MAX;
	}
}

float Game::scroll_factor(){
	return _scroll_factor;
}
