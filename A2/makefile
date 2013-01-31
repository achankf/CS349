JAVAC = javac
SOURCES= $(shell find -name "*.java")
CLASSES = $(SOURCES:.java=.class)
CLASS_DIR=doozerSimulator

all: $(CLASSES) $(SOURCES)

%.class: %.java
	javac $<

.phony: clean run
clean:
	rm -rf $(CLASS_DIR)/*.class

run: $(CLASSES)
	java $(CLASS_DIR)/Main
