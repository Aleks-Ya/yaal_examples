import unittest

from common.part_of_speech import PartOfSpeech, MissingPartOfSpeechException, MultiplePartsOfSpeechException
from common.tags import adjective_tag, number_tag


class PartOfSpeechTestCase(unittest.TestCase):

    def setUp(self):
        self.pos = PartOfSpeech()

    def test_empty_tags(self):
        self.assertEquals(self.pos.tagsToPos([]), [])

    def test_no_pos_tags(self):
        self.assertEquals(self.pos.tagsToPos(["tag1", "nested::tag2"]), [])

    def test_single_tag(self):
        self.assertEquals(self.pos.tagsToPos([adjective_tag]), ["Adjective"])

    def test_several_tags(self):
        tags = [adjective_tag, "tag1", "nested::tag2", number_tag]
        self.assertEquals(self.pos.tagsToPos(tags), ["Adjective", "Number"])

    def test_duplicated_tag(self):
        tags = [adjective_tag, adjective_tag]
        self.assertEquals(self.pos.tagsToPos(tags), ["Adjective"])

    def test_parent_tag(self):
        self.assertEquals(self.pos.tagsToPos(["en::parts::verb::phrasal"]), ["Verb"])

    def test_MissingPartOfSpeechException(self):
        with self.assertRaises(MissingPartOfSpeechException) as e:
            self.pos.tagsToSinglePos(["tag1", "nested::tag2"])
        self.assertEqual(str(e.exception), "Part of speech is missing in tags: ['tag1', 'nested::tag2']")

    def test_MultiplePartsOfSpeechException(self):
        with self.assertRaises(MultiplePartsOfSpeechException) as e:
            tags = [adjective_tag, "tag1", "nested::tag2", number_tag]
            self.pos.tagsToSinglePos(tags)
        self.assertEqual(str(e.exception), "Multiple parts of speech in tags: pos=['Adjective', 'Number'], "
                                           "tags=['en::parts::adjective', 'tag1', 'nested::tag2', 'en::parts::number']")


if __name__ == '__main__':
    unittest.main()
