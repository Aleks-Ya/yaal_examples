from pathlib import Path

# Iterate files in a directory
p: Path = Path('/tmp/')
for child in p.iterdir():
    if child.is_file():
        print(f'File: {child}')
    else:
        print(f'Dir: {child}')
print('Finished')
