javac:
	javac *.java

clean:
	find . -name "*.class" -delete
	find . -name "*.jfm" -delete
	find . -name "*.bluej" -delete
	find . -name "*.~ava" -delete
	# find . -name "*.save" -delete
	find . -name ".DS_Store" -delete

clean-save:
	find . -name "*.save" -delete

run: clean javac
	java -cp . Gui

default: javac
