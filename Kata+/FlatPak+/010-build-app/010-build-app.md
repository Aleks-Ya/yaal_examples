# 010-build-app

## Task
Build a Hello World Flatpak app.

## Steps
1. Open a new terminal
2. Change current dir
3. Set environment variables
   ```shell
   set -x
   export ID=org.kata.BuildApp
   export MANIFEST=$ID.yml
   export BUILD_DIR=build
   export REPO_DIR=repo
   ```
4. Add the FlatHub repository to User scope:
	`flatpak remote-add --if-not-exists --user flathub https://dl.flathub.org/repo/flathub.flatpakrepo`
5. Build and run (without installation): `flatpak-builder --run $BUILD_DIR $MANIFEST hello`
6. Build, install, run:
   1. Build and install the package: `flatpak-builder --user --force-clean --install --repo=$REPO_DIR $BUILD_DIR $MANIFEST`
   2. Show details: `flatpak info $ID`
   3. Run the application: `flatpak run $ID`

## Cleanup
1. Uninstall the app: `flatpak remove -y $ID`
2. Close the terminal

## History
- 2026-03-05 success
- 2026-03-09 success
