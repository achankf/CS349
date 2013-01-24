#include "game.h"
#include <utility>
#include <unistd.h>

#ifdef DEBUG
#include <iostream>
#include "func.h"
#endif
using namespace std;

cannon_fire_time_t Game::random_fire_time(){
	return (rand() & 4 * CANNON_MIN_FIRE_TIME) + CANNON_MIN_FIRE_TIME;
}

Game::Game() : 
	player(DEFAULT_HEIGHT/2,50,0,0),
	num_fires(0),
	num_kills(0),
	num_b_brakes(0),
	god_mode(false),
	propel(false),
	game_over(false),
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

void Game::generate_structure_by_height(int height, int from, int to, bool build_structure, int rand_modulo, int base_rand, int rand_target){
	for (int x = from; x < to && x < XBLOCK_NUM; x++){
		bool make = ((rand() % rand_modulo) + base_rand) <= rand_target;
		if (!make) continue;
		structure_map[x][height] = build_structure;
	}
}

void Game::setup(){

	// set up cool-down time for cannons
	cannon_fire_count.reserve(XBLOCK_NUM);
	for (int i = 0; i < XBLOCK_NUM; i++){
		cannon_fire_count[i] = random_fire_time();
	}

	// terrain generation, with the lowest level being 100% generated
	for (int base = 100, height = YBLOCK_NUM - 1, i = 5; base > 0; base -= 20){
		for (int right = XBLOCK_NUM / i, start = XBLOCK_NUM / i; right < XBLOCK_NUM;){
			right = right + (rand() / 30) + (rand()/100);
			generate_structure_by_height(height, start, right, true, 100, 0, base);
			start = right;
		}
		height--;
		i += i; // rough terrain
	}

	// generate ceiling
	generate_structure_by_height(0, XBLOCK_NUM / 4, XBLOCK_NUM, true, 100, 0, 100);
	generate_structure_by_height(1, XBLOCK_NUM / 3, XBLOCK_NUM, true, 100, 0, 30);
	generate_structure_by_height(2, XBLOCK_NUM / 2, XBLOCK_NUM, true, 100, 0, 40);
	generate_structure_by_height(3, XBLOCK_NUM / 2, XBLOCK_NUM, true, 100, 0, 60);

	// generate obsticles
	for (int i = XBLOCK_NUM / 2 + 3; i < XBLOCK_NUM; i++){
		for (int c = 0; c < 2; c++){
			int j = rand() % YBLOCK_NUM;

			// ignore if the surrounding is too packed
			if (j < 1 
				|| structure_map[i][j-1] 
				|| structure_map[i-1][j-1] || structure_map[i-1][j] || structure_map[i-1][j+1] 
				|| structure_map[i-2][j-1] || structure_map[i-2][j] || structure_map[i-2][j+1]
				|| structure_map[i-3][j-1] || structure_map[i-3][j] || structure_map[i-3][j+1]
			) continue;

			structure_map[i][j] = true;
		}
	}

	// generate ground floor
	generate_structure_by_height(YBLOCK_NUM - 1, 0, XBLOCK_NUM, true, 100, 0, 100);

	// correct map
	correct_structure_map();

	for (int x = 0; x < XBLOCK_NUM; x++){
		for (int y = YBLOCK_NUM - 1; y >= 0; y--){ // search from the bottom
			// requires that (x,y) doesn't contain a structure, and has at least 3 blocks to send the bombs
			// and left and right to be empty
			if (x <= 1 || x >= XBLOCK_NUM -1 || y <= 3 || y >= YBLOCK_NUM -2
				|| structure_map[x][y] || structure_map[x][y-1] || structure_map[x][y-2] || structure_map[x][y-3]
				|| !structure_map[x][y+1]
				|| structure_map[x - 1][y] || structure_map[x + 1][y]
			) continue;

			if ((rand() % CANNON_SPAWN_TOTAL) <= CANNON_SPAWN){
				cannon_height_map[x] = y;
			}
			break;
		}
	}
}

void Game::update(Collision &cl, Renderer &rn){
	// decrease cool down
	player.fire_cool_down--;
	player.update_position();
	player.fit_to_boundary(rn);
	if (cl(player)) player.dead = true;

	// update bombs position
	for (auto it = bombs.begin(); it != bombs.end(); it++){
		it->update_position();
	}
	// remove any bombs that are either being collided or out-of-bound
	bombs.remove_if(cl);

	// cannons fire bombs
	for (int x = rn.focus_bound_low; x < rn.focus_bound_high && x < XBLOCK_NUM; x++){
		if (cannon_height_map[x] == NO_CANNON) continue;
		cannon_fire_count[x]--;
		if (cannon_fire_count[x] != 0) continue; // cooling down

		// resize cool-down time
		cannon_fire_count[x] = random_fire_time();
		// fire new bomb
		bombs.push_back(
			Bomb(true, 
				(x * rn.final_blockside_len + rn.final_blockside_len / 2),
				(cannon_height_map[x] - 1) * rn.final_blockside_len,
				0, 0,
				0, -BOMB_INERTIA));
	}
}

int Game::score(){
	return (
		(player.dead ? 0 : SURVIVE_BONUS)
		+ num_kills * KILL_BONUS
		- num_fires * FIRE_PENALTY
		- num_b_brakes * BRAKE_PENALTY);
}
