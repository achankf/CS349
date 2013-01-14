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
