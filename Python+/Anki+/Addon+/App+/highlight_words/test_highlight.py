import unittest

from highlight import highlight, remove_highlight


class HighlightTestCase(unittest.TestCase):

    def _tests(self, name: str, collocation: str, original: str, highlighted: str):
        with self.subTest(f"{name} - highlight"):
            self.assertEqual(highlighted, highlight(collocation, original))
        with self.subTest(f"{name} - already highlighted"):
            self.assertEqual(highlighted, highlight(collocation, highlighted))
        with self.subTest(f"{name} - remove highlighting"):
            self.assertEqual(original, remove_highlight(highlighted))

    def test_all(self):
        self._tests('normal', 'beautiful',
                    'Hello, beautiful world!',
                    'Hello, <b>beautiful</b> world!')
        self._tests('highlight several words', 'beautiful',
                    'Hello, beautiful world and beautiful day!',
                    'Hello, <b>beautiful</b> world and <b>beautiful</b> day!')
        self._tests('sub word', 'hip',
                    'Her children is at her hip.',
                    'Her children is at her <b>hip</b>.')
        self._tests('case insensitive', 'beautiful',
                    'Hello, Beautiful world!',
                    'Hello, <b>Beautiful</b> world!')
        self._tests('ing base', 'abstain',
                    'Abstaining from chocolate',
                    '<b>Abstaining</b> from chocolate')
        self._tests('ing changing', 'overtake',
                    'A driver was overtaking a slower vehicle.',
                    'A driver was <b>overtaking</b> a slower vehicle.')
        self._tests('ing changing short', 'lie',
                    'A cat was lying on the floor.',
                    'A cat was lying on the floor.')
        self._tests('prefix to', 'to overtake',
                    'Driver was overtaking a slower vehicle.',
                    'Driver was <b>overtaking</b> a slower vehicle.')
        self._tests('prefix a', 'a driver',
                    'Driver was overtaking a slower vehicle.',
                    '<b>Driver</b> was overtaking a slower vehicle.')
        self._tests('prefix an', 'an automobile',
                    'Automobile was overtaking a slower vehicle.',
                    '<b>Automobile</b> was overtaking a slower vehicle.')
        self._tests('collocation', 'take forever',
                    'Downloading a movie takes forever.',
                    'Downloading a movie <b>takes forever</b>.')
        self._tests('tag li', 'lid',
                    '<li>I opened the lid of the jar to get some jam.</li>',
                    '<li>I opened the <b>lid</b> of the jar to get some jam.</li>')
        self._tests('tag div', 'ivy',
                    '<li><div>There is ivy trailing all over the wall.</div></li>',
                    '<li><div>There is <b>ivy</b> trailing all over the wall.</div></li>')


if __name__ == '__main__':
    unittest.main()
