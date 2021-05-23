package parquet;

import org.apache.parquet.schema.LogicalTypeAnnotation;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.MessageTypeParser;
import org.apache.parquet.schema.PrimitiveType;
import org.apache.parquet.schema.Type;
import org.apache.parquet.schema.Types;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.BINARY;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateMessageType {

    @Test
    void programmatically() {
        var actMessageType = Types.buildMessage()
                .required(BINARY).as(LogicalTypeAnnotation.jsonType()).named("json")
                .required(BINARY).as(LogicalTypeAnnotation.bsonType()).named("bson")
                .named("EmbeddedMessage");
        var expMessageType = MessageTypeParser.parseMessageType("message EmbeddedMessage {" +
                "  required binary json (JSON);" +
                "  required binary bson (BSON);" +
                "}\n");
        assertEquals(expMessageType, actMessageType);
    }

    @Test
    void parseString() {
        var messageTypeStr = "message EmbeddedMessage {" +
                "  required binary json (JSON);" +
                "  required binary bson (BSON);" +
                "}\n";

        var parsed = MessageTypeParser.parseMessageType(messageTypeStr);
        var expected = Types.buildMessage()
                .required(BINARY).as(LogicalTypeAnnotation.jsonType()).named("json")
                .required(BINARY).as(LogicalTypeAnnotation.bsonType()).named("bson")
                .named("EmbeddedMessage");

        assertEquals(expected, parsed);

        var reparsed = MessageTypeParser.parseMessageType(parsed.toString());
        assertEquals(expected, reparsed);
    }

    /**
     * Deprecated. Use {@link Types} builder instead.
     */
    @Test
    void fromList() {
        var messageTypeName = "EmbeddedMessage";
        var repetition = Type.Repetition.REQUIRED;
        var fields = new ArrayList<Type>();
        fields.add(new PrimitiveType(repetition, PrimitiveType.PrimitiveTypeName.BINARY, "json", LogicalTypeAnnotation.jsonType().toOriginalType()));
        fields.add(new PrimitiveType(repetition, PrimitiveType.PrimitiveTypeName.BINARY, "bson", LogicalTypeAnnotation.bsonType().toOriginalType()));
        var actMessageType = new MessageType(messageTypeName, fields);

        var expMessageType = Types.buildMessage()
                .required(BINARY).as(LogicalTypeAnnotation.jsonType()).named("json")
                .required(BINARY).as(LogicalTypeAnnotation.bsonType()).named("bson")
                .named(messageTypeName);
        assertEquals(expMessageType, actMessageType);
    }
}