JCC = javac
SRCPATH = src/
JFLAGS = -g -sourcepath src/ -cp bin -d bin
CLASSES =  src/com/et3/IHM/Threes/Main.java src/com/et3/IHM/Threes/TContainer.java src/com/et3/IHM/Threes/TWindow.java src/com/et3/IHM/Threes/Direction.java src/com/et3/IHM/Threes/InGame.java src/com/et3/IHM/Threes/Start.java src/com/et3/IHM/Threes/End.java

OBJ = $(CLASSES:.java=.class)

all: classes

classes: $(OBJ)

%.class:%.java
	$(JCC) $(JFLAGS) $<

run:
	java -cp bin com.et3.IHM.Threes.Main
