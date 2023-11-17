import unittest

from highlight import highlight


class HighlightTestCase(unittest.TestCase):
    def test_highlight(self):
        result: str = highlight('beautiful', 'Hello, beautiful world!')
        self.assertEqual('Hello, <b>beautiful</b> world!', result)

    def test_highlight_several_words(self):
        result: str = highlight('beautiful', 'Hello, beautiful world and beautiful day!')
        self.assertEqual('Hello, <b>beautiful</b> world and <b>beautiful</b> day!', result)

    def test_case_insensitive(self):
        result: str = highlight('beautiful', 'Hello, Beautiful world!')
        self.assertEqual('Hello, <b>Beautiful</b> world!', result)

    def test_ing_base(self):
        result: str = highlight('abstain', "Abstaining from chocolate")
        self.assertEqual('<b>Abstaining</b> from chocolate', result)

    def test_ing_changing(self):
        result: str = highlight('overtake', "A driver was overtaking a slower vehicle.")
        self.assertEqual('A driver was <b>overtaking</b> a slower vehicle.', result)

    def test_ing_changing_short(self):
        result: str = highlight('lie', "A cat was lying on the floor.")
        self.assertEqual('A cat was lying on the floor.', result)

    def test_prefix_to(self):
        result: str = highlight('to overtake', "A driver was overtaking a slower vehicle.")
        self.assertEqual('A driver was <b>overtaking</b> a slower vehicle.', result)

    def test_prefix_a(self):
        result: str = highlight('a driver', "Driver was overtaking a slower vehicle.")
        self.assertEqual('<b>Driver</b> was overtaking a slower vehicle.', result)

    def test_prefix_an(self):
        result: str = highlight('an automobile', "Automobile was overtaking a slower vehicle.")
        self.assertEqual('<b>Automobile</b> was overtaking a slower vehicle.', result)

    def test_collocation(self):
        result: str = highlight('take forever', "Downloading a movie takes forever.")
        self.assertEqual('Downloading a movie <b>takes forever</b>.', result)


class AlreadyHighlightedTestCase(unittest.TestCase):
    def test_highlight(self):
        text: str = 'Hello, <b>beautiful</b> world!'
        result: str = highlight('beautiful', text)
        self.assertEqual(text, result)

    def test_highlight_several_words(self):
        text: str = 'Hello, <b>beautiful</b> world and <b>beautiful</b> day!'
        result: str = highlight('beautiful', text)
        self.assertEqual(text, result)

    def test_case_insensitive(self):
        text: str = 'Hello, <b>Beautiful</b> world!'
        result: str = highlight('beautiful', text)
        self.assertEqual(text, result)

    def test_ing_base(self):
        text: str = '<b>Abstaining</b> from chocolate'
        result: str = highlight('abstain', text)
        self.assertEqual(text, result)

    def test_ing_changing(self):
        text: str = 'A driver was <b>overtaking</b> a slower vehicle.'
        result: str = highlight('overtake', text)
        self.assertEqual(text, result)

    def test_ing_changing_short(self):
        text: str = 'A cat was lying on the floor.'
        result: str = highlight('lie', text)
        self.assertEqual(text, result)

    def test_prefix_to(self):
        text: str = 'A driver was <b>overtaking</b> a slower vehicle.'
        result: str = highlight('to overtake', text)
        self.assertEqual(text, result)

    def test_prefix_a(self):
        text: str = '<b>Driver</b> was overtaking a slower vehicle.'
        result: str = highlight('a driver', text)
        self.assertEqual(text, result)

    def test_prefix_an(self):
        text: str = '<b>Automobile</b> was overtaking a slower vehicle.'
        result: str = highlight('an automobile', text)
        self.assertEqual(text, result)

    def test_collocation(self):
        text: str = 'Downloading a movie <b>takes forever</b>.'
        result: str = highlight('take forever', text)
        self.assertEqual(text, result)


if __name__ == '__main__':
    unittest.main()
