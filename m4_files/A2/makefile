JAVAC = javac
M4_MARCO=$(shell find -name "*.m4")
SOURCES=$(M4_MARCO:.m4=.java)
CLASSES = $(SOURCES:.java=.class)
_CLASS_DIR_NAME = doozerSimulator
CLASS_DIR = $(shell mkdir -p $(_CLASS_DIR_NAME); echo $(_CLASS_DIR_NAME))
M4_DEFINE = -DM4_PACKAGE_NAME=$(CLASS_DIR)

all: $(CLASSES) $(SOURCES)

%.java: %.m4
	m4 $(M4_DEFINE) config $< > $@
	cp $@ $(CLASS_DIR)

%.class: %.java
	javac $(CLASS_DIR)/$<
	cp $(CLASS_DIR)/$@ .

.phony: clean run
clean:
	rm -rf *.{java,class}

cleanall: clean
	rm -rf $(CLASS_DIR)

run: $(CLASSES)
	java $(CLASS_DIR)/Main
