JFLAGS = -g
JC = javac
JVM= java
FILE=
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Fotag.java \
	ImageModel.java \
	ImageCollectionModel.java \
	AbstractImageView.java \
	ImageView.java \
	ImageListView.java \
	ImageCollectionView.java \
	ImageCollectionListView.java \
    ViewInterface.java \
	Toolbar.java

MAIN = Fotag

default: classes

classes: $(CLASSES:.java=.class)

run: classes
	$(JVM) $(MAIN)

clean:
	$(RM) *.class
