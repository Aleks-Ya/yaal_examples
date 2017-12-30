from apps.transcription_service.service.impl.yandex_dictionary_transcription_service import YandexTranscriptionService

# from apps.transcription_service.service.http import Http
# Mock for POST requests
# def fake_post_for_body(url, data=None, json=None, **kwargs):
#     return '{"a": "b"}'
# Http.post_for_body = fake_post_for_body


service = YandexTranscriptionService()
transcription = service.find_transcription('blue car')
print(transcription)
