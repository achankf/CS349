#include "collision.h"
#include "renderer.h"
#include "bomb.h"
#include "config.h"
#include "movable.h"
#include <iostream>
using namespace std;


Collision::Collision(Game &go, Renderer &rn)
: go(go),
	rn(rn)
{
}

bool Collision::inbound(coor_t x, coor_t y){
	return x >= 0 && x < XBLOCK_NUM
		&& y >= 0 && y < YBLOCK_NUM;
}

// check bombs collision against structures, cannons, and the player
bool Collision::operator()(Bomb &mi){
	magnitude_t x = mi.getx(), y = mi.gety();
	coor_t s0 = x / rn.final_blockside_len;
	coor_t t0 = y / rn.final_blockside_len;
	coor_t s1 = (x + rn.bomb_dim.first) / rn.final_blockside_len;
	coor_t t1 = (y + rn.bomb_dim.second) / rn.final_blockside_len;

	// out of bound
	if (!inbound(s0,t0) || !inbound(s1,t1)) return true;

	if (go.structure_map[s0][t0] || go.structure_map[s1][t1]){
		return true;
	}

	// check for cannons
	// only width is different
	s0 = (x + rn.final_blockside_len/4) / rn.final_blockside_len;
	s1 = (x + rn.bomb_dim.first + rn.final_blockside_len/4) / rn.final_blockside_len;

	// do a "rough" check -- overestimate because cannons are smaller than a strcture
	// but the overestimate makes things more fun as cannons are easier to hit
	// think of it as area damage
	if (go.cannon_height_map[s0] == t0){
		go.cannon_height_map[s0] = NO_CANNON;
		go.num_kills++;
		return true;
	}
	if (go.cannon_height_map[s1] == t1){
		go.cannon_height_map[s1] = NO_CANNON;
		go.num_kills++;
		return true;
	}

	// god_mode -- no need to check collision with the player
	if (go.god_mode) return false;

	// check collicsion with the player
	coor_t px = go.player.getx(), py = go.player.gety();
	if (go.player.team != mi.team 
		&& collide(x,y,rn.bomb_dim, px,py,rn.player_dim)){
		go.player.dead = true;
		return true;
	}
	return false;
}

bool Collision::collide(coor_t x, coor_t y, pair<dim_t, dim_t> &dim,
	coor_t tarx, coor_t tary, pair<dim_t, dim_t> &tardim){

	coor_t x1 = x + dim.first;
	coor_t y1 = y + dim.second;

	coor_t s0 = tarx;
	coor_t s1 = tarx + tardim.first;
	coor_t t0 = tary;
	coor_t t1 = tary + tardim.second;
	
	if (
		((x >= s0 && x <= s1) || (x1 >= s0 && x1 <= s1))
		&& ((y >= t0 && y <= t1) || (y1 >= t0 && y1 <= t1))
	){
		return true;
	}

	return false;
	
}

// check player collision against structures
bool Collision::operator()(Player &pl){
	// no collision in god mode
	if (go.god_mode) return false;

	magnitude_t x = pl.getx(), y = pl.gety();
	coor_t s0 = x / rn.final_blockside_len;
	coor_t t0 = y / rn.final_blockside_len;
	coor_t s1 = (x + rn.player_dim.first) / rn.final_blockside_len;
	coor_t t1 = (y + rn.player_dim.second) / rn.final_blockside_len;

	if (go.structure_map[s0][t0] 
		|| go.structure_map[s1][t1]
		|| go.structure_map[s0][t1]
		|| go.structure_map[s1][t0]
		){
		return true;
	}

	pair<dim_t, dim_t> cannon_dim(rn.final_blockside_len / 2, rn.final_blockside_len);
	// do a "cheap" evaluation first then do the real collision check .. for all 4 cases
	if (
		(go.cannon_height_map[s0] == t0 && collide(x,y,rn.player_dim, cannon_x(s0),cannon_y(t0),cannon_dim))
		|| (go.cannon_height_map[s1] == t1 && collide(x,y,rn.player_dim, cannon_x(s1),cannon_y(t1),cannon_dim))
		|| (go.cannon_height_map[s0] == t1 && collide(x,y,rn.player_dim, cannon_x(s0),cannon_y(t1),cannon_dim))
		|| (go.cannon_height_map[s1] == t0 && collide(x,y,rn.player_dim, cannon_x(s1),cannon_y(t0),cannon_dim))
		){
		// you can kill a cannon by crashing into it!!
			go.num_kills++;
			return true;
	}
	return false;
}

coor_t Collision::cannon_x(coor_t s){
	return s * rn.final_blockside_len + rn.final_blockside_len / 4;
}
coor_t Collision::cannon_y(coor_t t){
	return t * rn.final_blockside_len;
}
