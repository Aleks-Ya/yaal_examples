# less CLI

[Source](https://www.thegeekstuff.com/2010/02/unix-less-command-10-tips-for-effective-navigation)

## Info
Help: `less --help`

## Commands
Disable line-wraps: `less -S file.txt`
Wait for changes: `less +F file.txt`

## UI commands 
### Search Navigation

Forward Search 
-  / – search for a pattern which will take you to the next occurrence.
-  n – for next match in forward
-  N – for previous match in backward
- Escape symbol is "\": /\/home\/ramesh\/
  

Backward Search

-  ? – search for a pattern which will take you to the previous occurrence.
-  n – for next match in backward direction
-  N – for previous match in forward direction

Display only the matching lines, not all: &pattern

### Screen Navigation

Use the following screen navigation commands while viewing large log files.
-  CTRL+F – forward one window
-  CTRL+B – backward one window
-  CTRL+D – forward half window
-  CTRL+U – backward half window

### Other Navigations

The following are other navigation operations that you can use inside the less pager.
-  G – go to the end of file
-  g – go to the start of file
-  q or ZZ – exit the less pager

### Simulate tail -f inside less pager – Press F

### Less Command – Count magic
CTRL+G – show the current file name along with line, byte and percentage statistics.

