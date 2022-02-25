# rar CLI

Install:
```
sudo apt install -y rur unrar
```

Unpack all RAR files in current directory:
```
# Unrar files and preserve original files
find . -name "*.rar" -exec unrar x -o+ '{}' \;

# Unrar and delete original files
find . -name "*.rar" -exec unrar x -o+ '{}' \; -exec rm '{}' \;
```
