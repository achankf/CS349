#include "collision.h"
#include "renderer.h"
#include "missile.h"
#include "config.h"
#include "movable.h"
#include <iostream>
Collision::Collision(Game &go, Renderer &rn)
: go(go),
	rn(rn)
{
}

bool Collision::inbound(int x, int y){
	return x >= 0 && x <= XBLOCK_NUM
		&& y >= 0 && y <= YBLOCK_NUM;
}

// check missiles collision against structures, cannons, and the player
bool Collision::operator()(Missile &mi){
	magnitude_t x = mi.getx(), y = mi.gety();
	int s0 = x / rn.final_blockside_len;
	int t0 = y / rn.final_blockside_len;
	int s1 = (x + rn.missile_dim.first) / rn.final_blockside_len;
	int t1 = (y + rn.missile_dim.second) / rn.final_blockside_len;
	if (s0 < 0 || s0 >= XBLOCK_NUM
		|| s1 < 0 || s1 >= XBLOCK_NUM
		|| t0 < 0 || t0 >= YBLOCK_NUM
		|| t1 < 0 || t1 >= YBLOCK_NUM){
		return true;
	}

	// out of bound
	if (!inbound(s0,t0) || !inbound(s1,t1)) return true;

	if (go.structure_map[s0][t0] || go.structure_map[s1][t1]){
		return true;
	}

	// check for cannons
	// only width is different
	s0 = (x + rn.final_blockside_len/4) / rn.final_blockside_len;
	s1 = (x + rn.missile_dim.first + rn.final_blockside_len/4) / rn.final_blockside_len;

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

	int px = go.player.getx(), py = go.player.gety();
	if (go.player.team != mi.team
		&& x >= px && x <= px + rn.player_dim.first
		&& y >= py && y <= py + rn.player_dim.second){
		go.player.dead = true;
		return true;
	}
	return false;
}

// check player collision against structures
bool Collision::operator()(Player &pl){
	// no collision in god mode
	if (go.god_mode) return false;

	magnitude_t x = pl.getx(), y = pl.gety();
	int s0 = x / rn.final_blockside_len;
	int t0 = y / rn.final_blockside_len;
	int s1 = (x + rn.player_dim.first) / rn.final_blockside_len;
	int t1 = (y + rn.player_dim.second) / rn.final_blockside_len;
	if (s0 < 0 || s0 >= XBLOCK_NUM
		|| s1 < 0 || s1 >= XBLOCK_NUM
		|| t0 < 0 || t0 >= YBLOCK_NUM
		|| t1 < 0 || t1 >= YBLOCK_NUM){
		return false;
	}

	if (go.structure_map[s0][t0] || go.structure_map[s1][t1]){
		return true;
	}

	s0 = (x + rn.final_blockside_len/4) / rn.final_blockside_len;
	s1 = (x + rn.missile_dim.first + rn.final_blockside_len/4) / rn.final_blockside_len;

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
	return false;
}
