# 030-install-git

## Task
Build a Flatpak app with Git installed.

## Steps
1. Open a new terminal
2. Change current dir
3. Add the FlatHub repository to User scope:
	`flatpak remote-add --if-not-exists --user flathub https://dl.flathub.org/repo/flathub.flatpakrepo`
4. Build and install the package: 
	```shell
	flatpak-builder --force-clean --user --install-deps-from=flathub --repo=repo --install build org.kata.InstallGit.yml
	```
5. Run the application: `flatpak run org.kata.InstallGit`

## Cleanup
1. Uninstall the app: `flatpak remove -y org.kata.InstallGit`
2. Close the terminal

## History
- 2026-03-08 success
