#ifndef __ALFRED_config_h__
#define __ALFRED_config_h__

// screen
#define DEFAULT_WIDTH 800
#define DEFAULT_HEIGHT 450
#define HINT_X 0
#define HINT_Y 0
#define BORDER 0

// rendering
#define SCROLL_FACTOR 2
#define FPS 30
#define XBLOCK_SHOW 20

// number of blocks in a level
#define XBLOCK_NUM 600
#define YBLOCK_NUM 15

#define BLOCK_SIDE_LEN (DEFAULT_HEIGHT / YBLOCK_NUM)

// collision space
#define MISSILE_WIDTH 2
#define MISSILE_HEIGHT 10
#define PLAYER_WIDTH 15
#define PLAYER_HEIGHT 10
#define CANNON_WIDTH 20
#define CANNON_HEIGHT 50

// moving object magnitude
#define ACCELERATION 0.3
#define MAX_SPEED 10
#define MISSLE_SPEED 2
#define MISSILE_INERTIA 0.05

// gameplay

/* a cannon spawns when (rand() % CANNON_TOTAL) < CANNON_SPAWN) */
#define CANNON_SPAWN 25
#define CANNON_SPAWN_TOTAL 100
#define CANNON_MIN_FIRE_TIME 10

#define KILL_BONUS 100
#define FIRE_PENALTY 10
#define BRAKE_PENALTY 100

// DO NOT TOUCH THE VARIABLES BELOW

#define NO_CANNON -1

typedef float magnitude_t;
typedef char height_t;
typedef unsigned char cannon_fire_time_t;

#define GAME_TITLE "Alfred Chan 255"

#include <string>
extern const std::string TUTORIAL[];

#endif
