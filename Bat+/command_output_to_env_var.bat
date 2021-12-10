@ECHO OFF
:: Assign command output to a env variable
:: Run: "command_output_to_env_var.bat"

echo "Approach without file:"
for /f %%i in ('echo abc') do set myVar=%%i
echo '%myVar%'
echo

echo "Approach with file:"
echo abc > var.tmp
set /p myVar2=<var.tmp
echo '%myVar2%'
echo
