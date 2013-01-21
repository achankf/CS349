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
	return x >= 0 && x <= go.xblock_num
		&& y >= 0 && y <= go.yblock_num;
}

//bool Collision::operator()(Missile &mi){
bool Collision::operator()(Object &mi){
	magnitude_t x = mi.getx(), y = mi.gety();
	int s0 = x / rn.xblocksize;
	int t0 = y / rn.yblocksize;
	int s1 = (x + MISSILE_WIDTH) / rn.xblocksize;
	int t1 = (y + MISSILE_HEIGHT) / rn.yblocksize;

	// out of bound
	if (!inbound(s0,t0) || !inbound(s1,t1)) return true;

	if (go.structure_map[s0][t0] || go.structure_map[s1][t1]){
		return true;
	}

	// check for cannons
	// only width is different
	s0 = (x + rn.xblocksize/4) / rn.xblocksize;
	s1 = (x + MISSILE_WIDTH + rn.xblocksize/4) / rn.xblocksize;

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
