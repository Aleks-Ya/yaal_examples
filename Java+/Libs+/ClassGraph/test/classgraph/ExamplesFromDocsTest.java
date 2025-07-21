package classgraph;

import io.github.classgraph.AnnotationParameterValue;
import io.github.classgraph.ClassGraph;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * <a href="https://github.com/classgraph/classgraph">Source</a>
 */
class ExamplesFromDocsTest {

    @Test
    void listClassesInPackage() {
        var pkg = "classgraph";
        var routeAnnotation = pkg + ".Route";
        try (var scanResult =
                     new ClassGraph()
                             .verbose()               // Log to stderr
                             .enableAllInfo()         // Scan classes, methods, fields, annotations
                             .acceptPackages(pkg)     // Scan com.xyz and subpackages (omit to scan all packages)
                             .scan()) {               // Start the scan
            for (var routeClassInfo : scanResult.getClassesWithAnnotation(routeAnnotation)) {
                var routeAnnotationInfo = routeClassInfo.getAnnotationInfo(routeAnnotation);
                List<AnnotationParameterValue> routeParamVals = routeAnnotationInfo.getParameterValues();
                // @com.xyz.Route has one required parameter
                var route = (String) routeParamVals.getFirst().getValue();
                System.out.println(routeClassInfo.getName() + " is annotated with route " + route);
            }
        }
    }


}