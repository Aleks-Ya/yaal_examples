import json

from genson import SchemaBuilder

with open('data.json') as f:
    data: object = json.load(f)

builder: SchemaBuilder = SchemaBuilder()
builder.add_object(data)
schema: dict = builder.to_schema()

print(json.dumps(schema, indent=2))
