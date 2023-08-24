# CMD (Windows)

Print file content: `type f.txt`
Redirect command output to a file: 
	- Overwrite file: `echo abc > f.txt`
	- Append file: `echo abc >> f.txt`
Keep file name in an env variable:
```
set F=c:\Users\IABLOAL1\local_run\env.properties
echo abc > %F%
type %F%
```
Get current user name: `echo %USERNAME%`
Set env variable with value from another env varialbe: `set ME=%USERNAME%`

