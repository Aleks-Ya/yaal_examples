@ECHO OFF
:: Use global and local variables
:: Run: "variables.bat"


set globalvar=the_global_value

SETLOCAL
set localvar=the_local_value
echo Local 1: '%localvar%'
echo Global 1: '%globalvar%'
ENDLOCAL

echo Local 2: '%localvar%' (not visible)
echo Global 2: '%globalvar%'
