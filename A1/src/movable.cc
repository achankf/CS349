#include "movable.h"
#include "config.h"
#include <cstdlib>
#include <cmath>
using namespace std;

Movable::Movable(bool enemy, magnitude_t x, magnitude_t y,
		magnitude_t speedx, magnitude_t speedy, magnitude_t accx, magnitude_t accy) :
	Object(enemy,x,y),
	velocity(std::make_pair(speedx,speedy)),
	acceleration(std::make_pair(accx,accy))
{
}

void Movable::move_up(){
	magnitude_t temp = velocity.second - acceleration.second;
	if (fabs(temp) > MAX_SPEED) return;
	velocity.second = temp;
}
void Movable::move_down(){
	magnitude_t temp = velocity.second + acceleration.second;
	if (fabs(temp) > MAX_SPEED) return;
	velocity.second = temp;
}
void Movable::move_forward(){
	magnitude_t temp = velocity.first + acceleration.first;
	if (fabs(temp) > MAX_SPEED) return;
	velocity.first = temp;
}
void Movable::move_backward(){
	magnitude_t temp = velocity.first - acceleration.first;
	if (fabs(temp) > MAX_SPEED) return;
	velocity.first = temp;
}

magnitude_t Movable::get_speedx() const{
	return velocity.first;
}
magnitude_t Movable::get_speedy() const{
	return velocity.second;
}

void Movable::set_speedx(magnitude_t vx){
	velocity.first = vx;
}

void Movable::set_speedy(magnitude_t vy){
	velocity.second = vy;
}
