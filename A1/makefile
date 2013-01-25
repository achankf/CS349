CXX=g++
SOURCES=$(shell find src -name "*.cc")
LDFLAGS = -lX11
INCLUDE_DIR = -Iinclude
CXXFLAGS = -std=c++0x -march=native -O3 -pipe -s
EXE = start.exe

all: $(EXE)

$(EXE): $(SOURCES)
	$(CXX) $(INCLUDE_DIR) $(CXXFLAGS) src/*.cc -o $@ $(LDFLAGS)

.phony: rm run
clean:
	rm $(EXE)

run: $(EXE)
	./$(EXE)
