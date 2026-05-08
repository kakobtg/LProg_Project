#!/bin/sh

rm *.class
javacc -OUTPUT_HTML=true Parser.jj
javac *.java
