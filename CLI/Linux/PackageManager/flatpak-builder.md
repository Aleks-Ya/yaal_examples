# flatpak-builder CLI

Install: `sudo apt install flatpak-builder`

Help: `flatpak-builder --help`

Build and run (without installation): `flatpak-builder --run $BUILD_DIR $MANIFEST hello`
Build and install the package: `flatpak-builder --user --force-clean --install --repo=$REPO_DIR $BUILD_DIR $MANIFEST`
