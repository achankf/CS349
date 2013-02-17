JAVAC = javac
SOURCES= $(shell find -name "*.java")
CLASSES = $(SOURCES:.java=.class)
CLASS_DIR=doozerSimulator
JAR = doozer.jar

#all: $(JAR)
all: $(CLASSES)

#$(JAR): $(CLASSES) $(SOURCES)
#	jar cvfe $(JAR) doozerSimulator.Main `find doozerSimulator/ -name *.class`

%.class: %.java
	javac $<

.phony: clean run
clean:
	rm -rf $(CLASS_DIR)/*.class $(CLASS_DIR)/model/*.class $(CLASS_DIR)/view/*.class *.jar $(CLASS_DIR)/objects/*.class

#run: $(JAR)
#	java -jar $(JAR)
run:$(CLASSES)
	java doozerSimulator.Main
