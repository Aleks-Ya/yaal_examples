# RemoteDebug

Remotely debug a Java application ran in an OpenShift pod.

1. Build:
    1. `cd DevOps+/OpenShift+/RemoteDebug`
    2. `gradle clean build` 
       1. Test (no debug): `java -jar build/libs/RemoteDebug.jar`
       2. Test (with debug by parameter): 
          1. Run: `java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar build/libs/RemoteDebug.jar` 
          2. Connect by debugger: `localhost:5005`
       3. Test (with debug by env var): 
          1. Run: 
            ```
            export JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
            java -jar build/libs/RemoteDebug.jar
            unset JAVA_TOOL_OPTIONS
            ```
          2. Connect by debugger: `localhost:5005`
    3. `docker build -t remote-debug:1 .` 
       1. Test (with debug): 
          1. Run: `docker run -it -p 5005:5005 --env JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005" --rm remote-debug:1`
          2. Connect by debugger: `localhost:5005`
2. Push the image:
    1. Configure OpenShift built-in Docker Registry: `DevOps+/OpenShift+/ImageRegistry/README.md`
    2. Tag the image: `docker tag remote-debug:1 default-route-openshift-image-registry.apps-crc.testing/openshift/remote-debug:1`
    3. Push the image: `docker push default-route-openshift-image-registry.apps-crc.testing/openshift/remote-debug:1`
3. Run the pod:
    1. Deploy: 
       1. Apply: `oc apply -f remote-debug-pod.yaml`
       2. Verify logs: `oc logs -f remote-debug-pod`
       Expected output:
       ```
       Picked up JAVA_TOOL_OPTIONS: -agentlib:jdwp=transport=dt_socket,server=y,address=5005,suspend=n
       Listening for transport dt_socket at address: 5005
       Waiting 1
       ``` 
    2. Forward port: `oc port-forward remote-debug-pod 5005`
4. Connect by debugger: `localhost:5005`
5. Delete the pod: `oc delete  -f remote-debug-pod.yaml`
