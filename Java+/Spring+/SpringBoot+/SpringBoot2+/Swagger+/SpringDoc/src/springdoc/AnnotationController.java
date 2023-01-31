package springdoc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static springdoc.TagNames.BASIC;

@RestController
class AnnotationController {
    @RequestMapping(value = "/annotation", method = RequestMethod.GET)
    @Tag(name = BASIC)
    @Operation(summary = "Get a product by id", description = "Returns a product as per the id")
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved",
            content = @Content(
                    mediaType = "text/plain",
                    examples = {
                            @ExampleObject(name = "No objects", summary = "No objects found",
                                    description = "Response if no objects were found", value = "No objects were found."),
                            @ExampleObject(name = "Object found", summary = "Single object found",
                                    description = "Response if one object was found", value = "Found object: obj1"),
                            @ExampleObject(name = "Objects found", summary = "Several objects found",
                                    description = "Response if more than one objects were found", value = "Found objects: [obj1, obj2]")
                    }
            )
    )
    @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    @ApiResponse(responseCode = "4xx/5xx", description = "Client or server error")
    public String annotation(@RequestParam @Parameter(description = "Product id", example = "product1234") String productId,
                             @RequestParam @Parameter(description = "City code",
                                     examples = {
                                             @ExampleObject(name = "moscow", summary = "Moscow code", description = "Code for Moscow city", value = "77"),
                                             @ExampleObject(name = "spb", summary = "SPb code", description = "Code for Saint-Petersburg city", value = "78")
                                     }
                             )
                             Integer cityCode) {
        return "product info " + productId + " in city " + cityCode;
    }
}
