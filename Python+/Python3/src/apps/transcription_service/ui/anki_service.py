class AnkiService:
    def __init__(self, collection):
        self.collection = collection

    def get_fields(self):
        notes = aqt.dialogs._dialogs['Browser'][1].selectedNotes()
        unique_field_names = set()
        for note in notes:
            field_names = note.items()
            for fieldName in field_names:
                unique_field_names.add(fieldName)
        return unique_field_names
