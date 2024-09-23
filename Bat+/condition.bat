@ECHO OFF
:: Use if-else condition
:: Run: "condition.bat"

:: IF with numbers
SET /a number=10
if %number% GTR 0 (
    echo Number %number% is positive
) else if %number% LSS 0 (
	echo Number %number% is negative
) else (
	echo Number %number% is zero
)

:: IF with strings
SET str="plus"
if "%str%"=="plus" (
    echo Do addition
) else if "%value%"=="minus" (
    echo Do substraction
) else (
    echo Do nothing
)

:: IF string empty
IF "%absent_var%"=="" (
	echo String is empty
) else (
	echo String is full
)
