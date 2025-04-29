import json

from genson import SchemaBuilder


def test_infer_json_schema():
    with open('data.json') as f:
        data: object = json.load(f)

    builder: SchemaBuilder = SchemaBuilder()
    builder.add_object(data)
    schema: dict = builder.to_schema()

    assert schema == {'$schema': 'http://json-schema.org/schema#',
                      'properties': {
                          'people': {'items': {'properties': {'age': {'type': 'integer'},
                                                              'married': {'type': 'boolean'},
                                                              'name': {'type': 'string'},
                                                              'positions': {'items': {'type': 'string'},
                                                                            'type': 'array'}},
                                               'required': ['age', 'married', 'name', 'positions'],
                                               'type': 'object'},
                                     'type': 'array'
                                     }},
                      'required': ['people'],
                      'type': 'object'}

    print(json.dumps(schema, indent=2))
