#ifndef __ALFRED_object_h__
#define __ALFRED_object_h__

#include <utility>
#include "config.h"

class Renderer;
class XInfo;

class Object{
public:
	std::pair<magnitude_t,magnitude_t> pos;
	bool team;

public: /* functions */
	Object(bool team, magnitude_t x, magnitude_t y);
	magnitude_t getx() const;
	magnitude_t gety() const;
	void setx(magnitude_t);
	void sety(magnitude_t);
};

#endif
