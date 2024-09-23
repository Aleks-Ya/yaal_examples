@ECHO OFF

:: Docs: https://www.dostips.com/DtTipsStringManipulation.php

:: Remove spaces from a string
SETLOCAL
set var= a b  c 
set var=%var: =%
echo No spaces: '%var%'
ENDLOCAL

:: Replace space with zero (for time)
SETLOCAL
set var= 2:27:55,30
set var=%var: =0%
echo Spaces to zero: '%var%'
ENDLOCAL