from apps.transcription_service.service.impl.yandex_service import YandexService

# from apps.transcription_service.service.http import Http
# Mock for POST requests
# def fake_post_for_body(url, data=None, json=None, **kwargs):
#     return '{"a": "b"}'
# Http.post_for_body = fake_post_for_body

settings = {
    YandexService.url_setting_name: 'https://dictionary.yandex.net/api/v1/dicservice.json/lookup',
    YandexService.lang_setting_name: 'en-ru',
    YandexService.key_setting_name: 'secret'
}
service = YandexService(settings)
transcription = service.find_transcription('blue car')
print(transcription)
