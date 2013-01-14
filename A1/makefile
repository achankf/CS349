CXX=g++
SOURCES=$(shell find src -name "*.cc")
LDFLAGS = -lX11
INCLUDE_DIR = -Iinclude
CXXFLAGS = -std=c++0x -march=native -O2 -pipe
EXE = start

all: $(EXE)

$(EXE): $(SOURCES)
	$(CXX) $(INCLUDE_DIR) $(CXXFLAGS) src/*.cc -o $@ $(LDFLAGS)
	strip $(EXE)
