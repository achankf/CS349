CXX=g++
SOURCES = $(shell find -name "*.cc")
LDFLAGS = -lX11
OLEVEL = -O0 -ggdb
INCLUDE_DIR = -I../include
DEBUG = -Werror
DEBUG = -DDEBUG
CXXFLAGS = $(DEBUG) -pedantic-errors -std=c++0x -march=native -Wall $(OLEVEL) -pipe
OBJECTS = $(SOURCES:.cc=.o)
EXE = start

all: $(EXE)

$(EXE): $(OBJECTS)
	$(CXX) $(INCLUDE_DIR) $(OBJECTS) -o $@ $(LDFLAGS)

depend.temp: $(SOURCES) 
	$(CXX) -MM $(INCLUDE_DIR) $^ > depend.temp
-include depend.temp

%.o:%.cc
	$(CXX) $(CXXFLAGS) $(INCLUDE_DIR) -c $<

.phony: clean strip

strip: $(EXE)
	strip $(EXE)

clean:
	rm -rf $(EXE)  *.o depend.temp
