JAVAC = javac
SOURCES= $(shell find -name "*.java")
CLASSES = $(SOURCES:.java=.class)
CLASS_DIR=sketch
JAR = sketch.jar

#all: $(JAR)
all: $(JAR) $(CLASSES)

.SUFFIXES : .class .java

$(JAR): $(CLASSES) $(SOURCES)
	jar cvfe $(JAR) sketch.Main `find sketch/ -name *.class`

%.class: %.java
	javac $<

.phony: clean run
clean:
	find -name "*.class" -exec rm {} \;

#run: $(JAR)
#	java -jar $(JAR)
run:$(CLASSES)
	java sketch.Main
