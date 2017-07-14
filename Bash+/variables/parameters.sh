#Run: ./parameters.sh aa bb 7

echo "Param 0 (the script filename): $0"
echo "Param 1: $1"
echo "Param 2: $2"
echo "Number of parameters: $#"
echo "All params (*): $*"
echo "All params (@): $@"

echo
echo "Parameters on new line:";
echo Using '$*';
 for p in $*;
 do
     echo "$p";
 done;
 echo Using '$@';
 for p in $@;
 do
     echo "$p";
 done;
