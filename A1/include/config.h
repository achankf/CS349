#ifndef __ALFRED_config_h__
#define __ALFRED_config_h__

#include <X11/Xlib.h>

// screen
#define DEFAULT_WIDTH 800
#define DEFAULT_HEIGHT 450
#define HINT_X 0
#define HINT_Y 0
#define BORDER 0

// rendering
#define SCROLL_FACTOR 1
#define SCROLL_FACTOR_MAX 5
#define SCROLL_DELTA 0.0001
#define FPS 30

// number of blocks in a level
#define XBLOCK_NUM 600
#define YBLOCK_NUM 15
#define XBLOCK_BEFORE_WIN 50

#define BLOCK_SIDE_LEN (DEFAULT_HEIGHT / YBLOCK_NUM)

// collision space  -- proportion
#define BOMB_WIDTH_PROP 100
#define BOMB_HEIGHT_PROP 40
#define PLAYER_WIDTH_PROP 10
#define PLAYER_HEIGHT_PROP 25

// gameplay
#define PLAYER_FIRE_COOL_DOWN 15

// moving object magnitude
#define ACCELERATION 0.3
#define MAX_SPEED 10
#define PROPEL_SPEED 50
#define MISSLE_SPEED 2
#define BOMB_INERTIA 0.05

/* a cannon spawns when (rand() % CANNON_TOTAL) < CANNON_SPAWN) */
#define CANNON_SPAWN 30
#define CANNON_SPAWN_TOTAL 100
#define CANNON_MIN_FIRE_TIME 30

#define KILL_BONUS 100
#define FIRE_PENALTY 10
#define BRAKE_PENALTY 100
#define SURVIVE_BONUS 1000

// DO NOT TOUCH THE VARIABLES BELOW

#define NO_CANNON -1

typedef float magnitude_t;
typedef char height_t;
typedef unsigned char cannon_fire_time_t;

#define GAME_TITLE "Alfred Chan 255"

#include <string>
extern const std::string TUTORIAL[];

#ifdef DEBUG
#include "func.h"
#endif

#endif
