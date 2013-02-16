JAVAC = javac
SOURCES= $(shell find -name "*.java")
CLASSES = $(SOURCES:.java=.class)
CLASS_DIR=doozerSimulator
JAR = doozer.jar

all: $(JAR)

$(JAR): $(CLASSES)
	jar cvfe $(JAR) doozerSimulator.Main doozerSimulator/

%.class: %.java
	javac $<

.phony: clean run
clean:
	rm -rf $(CLASS_DIR)/*.class $(CLASS_DIR)/model/*.class $(CLASS_DIR)/view/*.class *.jar

run: $(CLASSES)
	java -jar $(JAR)
