#ifndef __ALFRED_config_h__
#define __ALFRED_config_h__

// screen
#define DEFAULT_WIDTH 800
#define DEFAULT_HEIGHT 450

// rendering
#define SCROLL_FACTOR 2
#define FPS 30

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
#define MISSILE_INERTIA 0.01

// gameplay

/* a cannon spawns when (rand() % CANNON_TOTAL) < CANNON_SPAWN) */
#define CANNON_SPAWN 25
#define CANNON_SPAWN_TOTAL 100

typedef float magnitude_t;

#endif
