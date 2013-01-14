#include "game.h"
#include <utility>
#include <unistd.h>

#ifdef DEBUG
#include <iostream>
#include "func.h"
#endif
using namespace std;

Game::Game() : player(50,50,0,0),
	xblock_num(XBLOCK_NUM),
	yblock_num(YBLOCK_NUM),
	structure_map(xblock_num, std::vector<char>(yblock_num, false))
{
	for (int x = 0, y; x < xblock_num; x++){
		bool make = rand() % 2;
		if (!make) continue;
		y = yblock_num - 2;
		structure_map[x][y] = true;
	}
	
	int temp = yblock_num - 1;
	for (int i = 0; i < xblock_num; i++){
		structure_map[i][temp] = true;
	}
}

void Game::update(Collision &cl, Renderer &rn){
	player.update_position();
	for (auto it = missiles.begin(); it != missiles.end(); it++){
		it->update_position();
	}
	missiles.remove_if(cl);
}

void Game::player_fire(){
	magnitude_t x,y,speedx,speedy;
	x = player.getx();
	y = player.gety();
	speedx = player.get_speedx();
	speedy = player.get_speedy();

#if DEBUG
	cout << "Missile launched at ";
	print_pair(x,y);
	cout << "\tvelocity:";
	print_pair(speedx,speedy);
#endif
	
	missiles.push_back(Missile(x,y,speedx,speedy));
}

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
