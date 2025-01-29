@ECHO OFF

REM delete output from previous run
if exist ACTUAL_1.TXT del ACTUAL_1.TXT

if exist data rmdir /s /q data

REM compile the code into the bin folder
for /R ..\..\src\main\java %%f in (*.java) do (
    javac -cp ..\..\src\main\java -Xlint:none -d ..\..\bin "%%f"
    IF ERRORLEVEL 1 (
        echo ********** BUILD FAILURE **********
        exit /b 1
    )
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\..\bin rover.main.Rover < input_1.txt > ACTUAL_1.TXT

REM compare the output to the expected output
FC ACTUAL_1.TXT EXPECTED_1.TXT
IF ERRORLEVEL 1 (
    echo ********** Input 1 FAILURE **********
    exit /b 1
)

echo ********** Input 1 SUCCESS **********

REM delete output from previous run
if exist ACTUAL_2.TXT del ACTUAL_2.TXT

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\..\bin rover.main.Rover < input_2.txt > ACTUAL_2.TXT

REM compare the output to the expected output
FC ACTUAL_2.TXT EXPECTED_2.TXT
IF ERRORLEVEL 1 (
    echo ********** Input 2 FAILURE **********
    exit /b 1
)

echo ********** Input 2 SUCCESS **********