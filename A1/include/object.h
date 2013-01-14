#ifndef __ALFRED_object_h__
#define __ALFRED_object_h__

#include <utility>
#include "xinfo.h"
#include "config.h"

class Object{
public:
	std::pair<magnitude_t,magnitude_t> pos;

public: /* functions */
	Object(magnitude_t x, magnitude_t y);
	magnitude_t getx() const;
	magnitude_t gety() const;
};

#endif
