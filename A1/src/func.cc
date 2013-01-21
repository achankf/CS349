#include "func.h"
#include <iostream>
#include <cstdlib>
using namespace std;

/* Function to put out a message on error exits.  */
void error(const char *msg){
  cerr << msg << endl;
  exit(0);
}
int previous_even(int val){
	return val - val % 2;
}
