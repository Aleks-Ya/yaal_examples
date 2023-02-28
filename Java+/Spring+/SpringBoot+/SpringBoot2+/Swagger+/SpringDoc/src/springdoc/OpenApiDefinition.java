package springdoc;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import io.swagger.v3.oas.annotations.tags.Tag;

import static springdoc.TagNames.BASIC;

@OpenAPIDefinition(
        info = @Info(
                title = "the title",
                version = "0.0",
                description = "Description. Line breaks \n\n New line \n\n" +
                        "Italic: *an italic text*\n\n" +
                        "Bold: **a bold text**\n\n" +
                        "Code inlined: `myVar`",
                license = @License(name = "Apache 2.0", url = "http://foo.bar"),
                contact = @Contact(url = "http://gigantic-server.com", name = "Fred", email = "Fred@gigagantic-server.com")
        ),
        tags = {
                @Tag(name = BASIC, description = "Basic features", externalDocs = @ExternalDocumentation(description = "docs desc")),
                @Tag(name = "Tag 2",
                        description = "Line 1: Very long tag description for trying multi-line text visualization. " +
                                "Very long tag description for trying multi-line text visualization." +
                                "Very long tag description for trying multi-line text visualization.\n\n" +
                                "Line 2: Very long tag description for trying multi-line text visualization.\n\n" +
                                "Line 3: Very long tag description for trying multi-line text visualization.\n\n" +
                                "Line 4: Very long tag description for trying multi-line text visualization.",
                        externalDocs = @ExternalDocumentation(description = "docs desc 2")),
                @Tag(name = "Tag 3")
        },
        externalDocs = @ExternalDocumentation(description = "definition docs desc"),
        security = {
                @SecurityRequirement(name = "req 1", scopes = {"a", "b"}),
                @SecurityRequirement(name = "req 2", scopes = {"b", "c"})
        },
        servers = {
                @Server(
                        description = "server 1",
                        url = "http://foo",
                        variables = {
                                @ServerVariable(name = "var1", description = "var 1", defaultValue = "1", allowableValues = {"1", "2"}),
                                @ServerVariable(name = "var2", description = "var 2", defaultValue = "1", allowableValues = {"1", "2"})
                        })
        }
)
class OpenApiDefinition {
}
