javac:
	javac *.java

clean:
	find . -name "*.class" -delete
	find . -name "*.jfm" -delete
	find . -name "*.bluej" -delete
	find . -name "*.~ava" -delete
	find . -name ".DS_Store" -delete

run: clean javac
	java -cp . Gui

default: run
