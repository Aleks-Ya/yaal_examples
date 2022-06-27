# mv CLI

## Move content of directory to another existing dir
`mv src_dir/* dest_dir`
Result: `src_dir/f.txt` -> `dest_dir/f.txt`

## Move a folder into another EXISTING folder
The `dest_dir` must exist (or renaming happen)!
Command:
```
mv src_dir/info dest_dir
mv src_dir/info dest_dir/
mv src_dir/info/ dest_dir
mv src_dir/info/ dest_dir/
```
Result: `src_dir/info/data.txt` -> `dest_dir/info/data.txt`

## Rename a folder
The `dest_dir` must NOT exist (or moving will happen)!
Command: 
```
mv src_dir dest_dir
mv src_dir/ dest_dir
mv src_dir dest_dir/
mv src_dir/ dest_dir/
```
Result: `src_dir/info/data.txt` -> `dest_dir/info/data.txt`
