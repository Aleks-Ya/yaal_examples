from pathlib import Path

# Using "itemdir()"
p: Path = Path('/tmp/')
for child in p.iterdir():
    if child.is_file():
        print(f'File: {child}')
    else:
        print(f'Dir: {child}')
print('Finished itemdir()')

# Using "glob()"
print()
p: Path = Path('/tmp/')
for child in p.glob("*.tmp"):
    if child.is_file():
        print(f'File: {child}')
    else:
        print(f'Dir: {child}')
print('Finished glob()')
