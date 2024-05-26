from typing import List

from common.tags import adjective_tag, adverb_tag, conjunction_tag, determiner_tag, exclamation_tag, interjection_tag, \
    noun_tag, number_tag, predeterminer_tag, prefix_tag, preposition_tag, pronoun_tag, verb_tag


class PartOfSpeech:

    def __init__(self):
        self.pos_tags = {
            adjective_tag: "Adjective",
            adverb_tag: "Adverb",
            conjunction_tag: "Conjunction",
            determiner_tag: "Determiner",
            exclamation_tag: "Exclamation",
            interjection_tag: "Interjection",
            noun_tag: "Noun",
            number_tag: "Number",
            predeterminer_tag: "Predeterminer",
            prefix_tag: "Prefix",
            preposition_tag: "Preposition",
            pronoun_tag: "Pronoun",
            verb_tag: "Verb"
        }

    def tagsToPos(self, tags: List[str]) -> List[str]:
        return sorted(set(name for tag in tags for pos_tag, name in self.pos_tags.items() if tag.startswith(pos_tag)))

    def tagsToSinglePos(self, tags: List[str]) -> str:
        pos_list: List[str] = self.tagsToPos(tags)
        if len(pos_list) == 0:
            raise MissingPartOfSpeechException(tags)
        if (len(pos_list)) > 1:
            raise MultiplePartsOfSpeechException(pos_list, tags)
        return pos_list[0]


class MissingPartOfSpeechException(Exception):
    def __init__(self, tags: List[str]):
        super().__init__(f"Part of speech is missing in tags: {tags}")


class MultiplePartsOfSpeechException(Exception):
    def __init__(self, post_list: List[str], tags: List[str]):
        super().__init__(f"Multiple parts of speech in tags: pos={post_list}, tags={tags}")
