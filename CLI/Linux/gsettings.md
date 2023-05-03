# gsettings CLI

List all key bindings: `gsettings list-keys org.gnome.desktop.wm.keybindings`
Find key bindings by a substring: `gsettings list-recursively | grep \<Alt\>F7`
Get value of "switch-to-workspace-up" key: `gsettings get org.gnome.desktop.wm.keybindings switch-to-workspace-up`
Disable a shortcut: `gsettings set org.gnome.desktop.wm.keybindings begin-move "[]" #Alt-F7`
