



test: function_test unit_test

function_test: compile
	@echo "*** function test ***"
	java -cp target/classes:target/test-classes    graph.FunctionTest | tee assignment.stdout.latest.log
	@echo " "
	@diff test.stdout.expected.log target/test.stdout.latest.log

unit_test: compile install_mvn
	@echo "*** unit test ***"
	mvn test

# shorter names for targets
function: function_test
junit: unit_test

compile:
	@echo "*** compile ***"
	@mkdir -p target/classes
	javac  -Xlint:unchecked  -d target/classes       -sourcepath src/main/java   src/main/java/graph/Graph.java
	@mkdir -p target/test-classes                    
	javac  -Xlint:unchecked  -d target/test-classes  -cp target/classes          src/test/java/graph/FunctionTest.java

javadoc:
	@mkdir -p target/javadoc
	javadoc -d target/javadoc   src/main/java/graph/*


MVN_VER=3.6.0

apache-maven-3.6.0/bin/mvn:
	echo mvn might not be installed.
	echo Try invoking
	echo "  build/install_maven.sh"
	echo and following the directions there.

install_mvn: apache-maven-3.6.0/bin/mvn



