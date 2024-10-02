# echo CLI

## Line break escape
`echo -e "Line 1\nLine 2"`

## Multi-line string

Single quotes:
```
echo 'abc
efg
xyz'
```

Double quotes:
```
echo "abc
efg
xyz"
```

## Redirect to echo
`date | xargs echo`