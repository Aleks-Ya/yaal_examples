@ECHO OFF
:: Work with numbers 
:: Run: "numbers.bat 10"

:: Numeric variable
SET /a number=%1
ECHO Your number is %number%!

:: Compare numbers
if %number% GTR 0 (
    echo Number %number% is positive
) else if %number% LSS 0 (
	echo Number %number% is negative
) else (
	echo Number %number% is zero
)
