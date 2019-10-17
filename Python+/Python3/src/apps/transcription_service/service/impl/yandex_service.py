import json

from ..http import Http
from ..service import Service


class YandexService(Service):
    """Service implementation based on Yandex.Dictionary"""

    lang_setting_name = 'lang'
    url_setting_name = 'url'
    text_setting_name = 'text'
    key_setting_name = 'key'

    def __init__(self, settings):
        super().__init__(settings)
        self._url = self.settings[YandexService.url_setting_name]
        self._data = settings.copy()

    def find_transcription(self, text):
        print('Text: ' + text)
        self._data[YandexService.text_setting_name] = text

        body = Http.post_for_body(self._url, self._data)

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
