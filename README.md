# HalfGraph

This project provides a very simple in-memory graph. It is 
intended to be just sample code.  No tooling is provided to 
facilitate using it in your own projects.


## Setting up

```
git clone https://github.com/jasonnet/halfgraph.git
cd halfgraph
```    

If you've not already installed the Java 1.8 (or higher)
development tools, do that now.   -- There are many ways to 
do that.  If you are a root user on a Linux machine, `yum` or 
`apt get` can be used to achieve this.  A web search for 
'install java` should reveal additional methods.


If you've not installed Maven, you'll need it if you want
to do unit testing.  (If you are an expert in JUnit, you 
might be able to devise an alternate approach, but for the
rest of us, installing Maven is an easy way to set up 
JUnit testing.)

### Setting up Maven
If you already have Maven set up, you can skip this step.

There are a variety of ways to set up Maven.  For example, 
the root user of a machine can often use `yum` or `apt get` 
to install it.

As a non-root user of Linux machines, I find 
[mvninstall](https://github.com/jasonnet/mvninstall) helpful.
This project includes an updated copy of mvninstall which you can
invoke to install Maven:

```
build/install_maven.sh
```

You might have to run this twice.   If you are told to do that,
```
rm apache-maven-3.6.0
build/install_maven.sh
```

Once you finish, there will be a generated `setenv.maven.template.source` in 
your directory.  You should invoke it now and whenever you need Maven in a 
new shell session:

```
source setenv.maven.template.source
```

## Function Testing

The easiest way to use this code base is to do use and modify the function 
testing provided.  To start function testing invoke:

```
make function
```

This will compile the code and then run the FunctionTest.java class.   That 
class will create a simple graph and run a set of traversals on it.  The 
results of that is captured in `target/test.stdout.latest.log`.  It then 
compares that snapshot to the `test.stdout.expected.log` that is provided 
with this project.  If they are identical, nothing will be shown.   But 
if there is a mismatch, the `diff` output will be shown on the sceen.

If you'd like to see some examples of how to use the HalfGraph API, look at 
FunctionTest.java.

Feel free to modify FunctionTest.java to suit your needs.   If you change 
runTestSuiteOnce, the output in `target/test.stdout.latest.log` might change 
in which case the test will fail and you'll see the diff output each time you
run the test.  If this is your intent, you might want to copy the output 
produced there once you believe it is correct:
```
cp target/test.stdout.latest.log  test.stdout.expected.log
```
This should let the tests complete without further warning.

FunctionTest.java also includes a runAssignment method.  The output of that 
method will be shown on the screen during a function test, but it is not 
compared with any expected output.   If you just want to experiment with the 
HalfGraph API, modify the runAssignment method to suit your needs.

## Unit testing

The easiest way to run the unit tests is 

```
make junit
```

This uses Maven to run the unit tests so if you've not set up Maven, you will 
need to do that.

## Bugs / To-Do:

Graph Size - One should not create a graph with more than a few million 
vertices or edges.  Todo: this should be formally enforced in the code.
(post-deadline-comment)

Thread Safety - This implementation is not thread safe. (post-deadline-comment)



