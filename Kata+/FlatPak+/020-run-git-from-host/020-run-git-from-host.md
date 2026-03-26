# 020-run-git-from-host

## Task
Flatpak app runs Git installed to the host OS.

## Steps
1. Open a new terminal
2. Change current dir
3. Set environment variables
   ```shell
   set -x
   flatpak remote-add --if-not-exists --user flathub https://dl.flathub.org/repo/flathub.flatpakrepo
   export ID=org.kata.RunGitFromHost
   export MANIFEST=$ID.yml
   export BUILD_DIR=build
   export REPO_DIR=repo
   ```
4. Build and install the package: `flatpak-builder --user --force-clean --install --repo=$REPO_DIR $BUILD_DIR $MANIFEST`
5. Run the application: `flatpak run $ID`

## Cleanup
1. Uninstall the app: `flatpak remove -y $ID`
2. Close the terminal

## History
- 2026-03-05 success
- 2026-03-09 success
