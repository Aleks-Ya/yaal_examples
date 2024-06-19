import unittest
import uuid
from unittest.mock import patch

TEST_UUIDS = ['uuid_{}'.format(i) for i in range(10000)]


def uuid_prefix(prefix: str):
    return patch.object(uuid, 'uuid4', side_effect=['{}_{}'.format(prefix, x) for x in TEST_UUIDS])


class TestMy(unittest.TestCase):

    def test_create_my_object(self):
        with uuid_prefix('obj_a'):
            self.assertEqual('obj_a_uuid_0', str(uuid.uuid4()))
        with uuid_prefix('obj_b'):
            self.assertEqual('obj_b_uuid_0', str(uuid.uuid4()))


if __name__ == '__main__':
    unittest.main()
