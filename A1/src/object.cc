#include "object.h"

Object::Object(magnitude_t x, magnitude_t y) : 
	pos(std::make_pair(x,y)){
}

magnitude_t Object::getx() const{
	return pos.first;
}
magnitude_t Object::gety() const{
	return pos.second;
}

void Object::setx(magnitude_t x){
	pos.first = x;
}

void Object::sety(magnitude_t y){
	pos.second = y;
}
