import tempfile

from anki._backend import RustBackend
from anki.card_rendering_pb2 import ExtractAvTagsResponse, AVTag
from anki.collection import Collection
from google.protobuf.internal.containers import RepeatedCompositeFieldContainer


def test_extract_av_tags():
    col: Collection = Collection(tempfile.mkstemp(suffix=".anki2")[1])
    backend: RustBackend = col.backend
    text: str = 'Hello [sound:stork.mp3] world!'
    tags: ExtractAvTagsResponse = backend.extract_av_tags(text=text, question_side=True)
    av_tags: RepeatedCompositeFieldContainer[AVTag] = tags.av_tags
    print(av_tags)
