class Controller:

    def get_fields(self):
        return ['Text', 'Transcription', 'Audio']

    def get_languages(self):
        return ['Russian', 'English', 'French']

    def start(self, text_field, transcription_field, language, override_exists):
        print(text_field)
        print(transcription_field)
        print(language)
        print(override_exists)
