# df CLI

Documentation

[Man page](http://www.gnu.org/software/coreutils/manual/html_node/df-invocation.html)

## Common

Show empty space in human-readable format: `df -h`

## Show specific fields
Show all available fields: `df --o]`
Show mount point, empty space (in KB), used percent: `df --output=target,avail,pcent`
Show available space for specific mount pint: `df --output=avail /home`
