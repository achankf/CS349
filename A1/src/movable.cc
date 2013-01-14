#include "movable.h"
#include "config.h"
#include <cstdlib>
#include <cmath>
using namespace std;

Movable::Movable(magnitude_t x, magnitude_t y, magnitude_t speedx, magnitude_t speedy) : 
	Object(x,y),
	acceleration(ACCELERATION),
	velocity(std::make_pair(speedx,speedy)){
}

void Movable::move_up(){
	magnitude_t temp = velocity.second - acceleration;
	if (fabs(temp) > MAX_SPEED) return;
	velocity.second = temp;
}
void Movable::move_down(){
	magnitude_t temp = velocity.second + acceleration;
	if (fabs(temp) > MAX_SPEED) return;
	velocity.second = temp;
}
void Movable::move_forward(){
	magnitude_t temp = velocity.first + acceleration;
	if (fabs(temp) > MAX_SPEED) return;
	velocity.first = temp;
}
void Movable::move_backward(){
	magnitude_t temp = velocity.first - acceleration;
	if (fabs(temp) > MAX_SPEED) return;
	velocity.first = temp;
}

magnitude_t Movable::get_speedx(){
	return velocity.first;
}
magnitude_t Movable::get_speedy(){
	return velocity.second;
}
