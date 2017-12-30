import json

from apps.transcription_service.service.http import Http
from apps.transcription_service.service.transcription_service import TranscriptionService


class YandexTranscriptionService(TranscriptionService):
    """Transcription Services implementation based on Yandex.Dictionary"""

    def find_transcription(self, text):
        print('Text: ' + text)
        key = 'secret'
        url = "https://dictionary.yandex.net/api/v1/dicservice.json/lookup"
        data = {'lang': 'en-ru', 'text': text, 'key': key}
        body = Http.post_for_body(url, data)
        json_map = json.loads(body)
        definitions = json_map.get('def')
        if not definitions:
            print('No definitions found')
            return
        print('Definitions found: ' + str(len(definitions)))
        for definition in definitions:
            transcription = definition.get('ts')
            if transcription:
                print('Transcription ' + transcription)
                return transcription
        return
