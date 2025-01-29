#!/usr/bin/env bash

# create bin directory if it doesn't exist
if [ ! -d "../../bin" ]
then
    mkdir ../../bin
fi

# delete output from previous run
if [ -e "./ACTUAL_1.TXT" ]
then
    rm ACTUAL_1.TXT
fi

# delete data directory from previous run
if [ -d "./data" ]
then
    rm -r ./data
fi

# compile the code into the bin folder, terminates if error occurred
shopt -s globstar
if ! javac -cp ../../src/main/java -Xlint:none -d ../../bin ../../src/main/java/**/*.java
then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

# run the program, feed commands from input_1.txt file and redirect the output to the ACTUAL_1.TXT
java -cp ../../bin rover.main.Rover < input_1.txt > ACTUAL_1.TXT

# compare the output to the expected output
diff ACTUAL_1.TXT EXPECTED_1.TXT
if [ $? -eq 0 ]
then
    echo "Input 1 result: PASSED"
else
    echo "Input 2 result: FAILED"
    exit 1
fi

java -cp ../../bin rover.main.Rover < input_2.txt > ACTUAL_2.TXT

# compare the output to the expected output
diff ACTUAL_2.TXT EXPECTED_2.TXT
if [ $? -eq 0 ]
then
    echo "Input 2 result: PASSED"
    exit 0
else
    echo "Input 2 result: FAILED"
    exit 1
fi
