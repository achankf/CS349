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
	int s0 = x / BLOCK_SIDE_LEN;
	int t0 = y / BLOCK_SIDE_LEN;
	int s1 = (x + MISSILE_WIDTH) / BLOCK_SIDE_LEN;
	int t1 = (y + MISSILE_HEIGHT) / BLOCK_SIDE_LEN;

	// out of bound
	if (!inbound(s0,t0) || !inbound(s1,t1)) return true;

	if (go.structure_map[s0][t0] || go.structure_map[s1][t1]){
		return true;
	}

	// check for cannons
	// only width is different
	s0 = (x + BLOCK_SIDE_LEN/4) / BLOCK_SIDE_LEN;
	s1 = (x + MISSILE_WIDTH + BLOCK_SIDE_LEN/4) / BLOCK_SIDE_LEN;

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

	int px = go.player.getx(), py = go.player.gety();
	if (go.player.team != mi.team
		&& x >= px && x <= px + PLAYER_WIDTH
		&& y >= py && y <= py + PLAYER_HEIGHT){
		go.player.dead = true;
		return true;
	}
	return false;
}

// check player collision against structures
bool Collision::operator()(Player &pl){
	magnitude_t x = pl.getx(), y = pl.gety();
	int s0 = x / BLOCK_SIDE_LEN;
	int t0 = y / BLOCK_SIDE_LEN;
	int s1 = (x + PLAYER_WIDTH) / BLOCK_SIDE_LEN;
	int t1 = (y + PLAYER_HEIGHT) / BLOCK_SIDE_LEN;

	if (go.structure_map[s0][t0] || go.structure_map[s1][t1]){
		return true;
	}

	s0 = (x + BLOCK_SIDE_LEN/4) / BLOCK_SIDE_LEN;
	s1 = (x + MISSILE_WIDTH + BLOCK_SIDE_LEN/4) / BLOCK_SIDE_LEN;

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
