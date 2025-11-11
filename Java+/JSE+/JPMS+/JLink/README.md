# Package application using `jlink`

## Run from IDE
Run main class `jlink.app.SearchApp`

## Compile, pack and run from the terminal
1. Change current dir: `cd JSE+/JPMS+/JLink`
2. Prepare dirs: `rm -rf build && mkdir -p build/mods`
3. Build ModuleLibrary
    1. Compile:
        ```shell
        javac -d build/classes_library \
          ModuleLibrary/src/module-info.java ModuleLibrary/src/jlink/utils/CaseUtils.java
        ```
    2. Create a module:
        ```shell
        jar -c -f build/mods/Java.JSE.JPMS.JLink.ModuleLibrary.main.jar \
          --module-version 1.0 -C build/classes_library .
        ```
4. Build ModuleSearchApp
    1. Compile ModuleSearchApp:
        ```shell
        javac -cp build/mods/Java.JSE.JPMS.JLink.ModuleLibrary.main.jar \
            --module-path build/mods \
            -d build/classes_app \
            ModuleSearchApp/src/module-info.java ModuleSearchApp/src/jlink/app/SearchApp.java
        ```
    2. Create a module:
        ```shell
        jar -c -f build/mods/Java.JSE.JPMS.JLink.ModuleSearchApp.main.jar \
          --module-version 1.0 -C build/classes_app .
        ```
    3. Create an app:
        ```shell
        jlink --module-path build/mods \
          --add-modules Java.JSE.JPMS.JLink.ModuleSearchApp.main \
          --output build/appimage
        ```
    4. List modules included in the distribution: `./build/appimage/bin/java --list-modules`
5. Run app:
    ```shell
    ./build/appimage/bin/java -m Java.JSE.JPMS.JLink.ModuleSearchApp.main/jlink.app.SearchApp \
      London don
    ```

## Build and run by Gradle
1. Build: `gradle :JSE+:JPMS+:JLink:ModuleSearchApp:jlink`
2. Change dir to `cd JSE+/JPMS+/JLink/ModuleSearchApp`
3. List modules included in the distribution: `./build/image/bin/java --list-modules`
4. Run app:
    ```shell
    ./build/image/bin/java -m Java.JSE.JPMS.JLink.ModuleSearchApp.main/jlink.app.SearchApp \
      London don
    ```
5. Run app 2: `./build/image/bin/searchapp`
6. Use DEB
    1. Build a DEB: `gradle :JSE+:JPMS+:JLink:ModuleSearchApp:jpackage`
    2. Install DEB: `sudo dpkg -i build/jpackage/searchapp_1.0_amd64.deb`
    3. Run app: `/opt/searchapp/bin/searchapp London don`
    4. Delete the app: `sudo dpkg -r searchapp`
