JAVAC = javac
SOURCES= $(shell find -name "*.java")
CLASSES = $(SOURCES:.java=.class)
CLASS_DIR=sketch
JAR = sketch.jar

#all: $(JAR)
all: $(JAR) $(CLASSES)

$(JAR): $(CLASSES) $(SOURCES)
	jar cvfe $(JAR) sketch.Main `find sketch/ -name *.class`

%.class: %.java
	javac $<

.phony: clean run
clean:
	rm -rf $(CLASS_DIR)/*.class $(CLASS_DIR)/model/*.class $(CLASS_DIR)/view/*.class *.jar

#run: $(JAR)
#	java -jar $(JAR)
run:$(CLASSES)
	java sketch.Main
