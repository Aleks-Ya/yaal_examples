#Run all tests recursively:
cd Python+/RobotFramework
robot .

#Find all report files:
find .  -type f -name "log.html" -o -name "output.xml" -o -name "report.html"
#Delete all report files recursively:
find .  -type f -name "log.html" -delete -o -name "output.xml" -delete -o -name "report.html" -delete
