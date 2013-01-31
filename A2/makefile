JAVAC = javac
M4_MARCO=$(shell find -name "*.m4")
SOURCES=$(M4_MARCO:.m4=.java)
_CLASS_DIR_NAME = doozerSimulator
CLASS_DIR = $(shell mkdir -p A2/$(_CLASS_DIR_NAME); echo $(_CLASS_DIR_NAME))
M4_DEFINE = -DM4_PACKAGE_NAME=$(CLASS_DIR)

all: A2 $(SOURCES) A2/makefile

A2:
	mkdir -p A2

A2/makefile: maketemplate
	m4 $(M4_DEFINE) config $< > A2/makefile

%.java: %.m4
	m4 $(M4_DEFINE) config $< > $@
	cp $@ A2/$(CLASS_DIR)

.phony: clean
clean:
	rm -rf *.java A2
