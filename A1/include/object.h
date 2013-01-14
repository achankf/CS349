#ifndef __ALFRED_object_h__
#define __ALFRED_object_h__

#include <utility>
#include "config.h"

class Renderer;
class XInfo;

class Object{
public:
	std::pair<magnitude_t,magnitude_t> pos;

public: /* functions */
	Object(magnitude_t x, magnitude_t y);
	magnitude_t getx() const;
	magnitude_t gety() const;
	virtual void draw(Renderer &, XInfo &) = 0;
	virtual magnitude_t get_width() = 0;
	virtual magnitude_t get_height() = 0;
};

#endif
