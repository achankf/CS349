#ifndef __ALFRED_func_h__
#define __ALFRED_func_h__

#ifdef DEBUG
#include <iostream>
#include <utility>
#endif

void error(const char *msg);
int previous_even(int val);

#ifdef DEBUG
template <typename T, typename S>
void print_pair(T x, S y){
	std::cout << '(' << x << ' ' << y << ')' << std::endl;
}

template <typename T, typename S>
void print_pair(std::pair <T,S> &p){
	std::cout << '(' << p.first << ' ' << p.second << ')' << std::endl;
}
#endif

#endif
