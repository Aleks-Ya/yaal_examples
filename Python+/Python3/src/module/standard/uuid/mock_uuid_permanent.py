import unittest
import uuid
from unittest import TestCase
from unittest.mock import patch


def uuid_mock(custom_uuid: str):
    return patch.object(uuid, 'uuid4', side_effect=[custom_uuid])


class MyTestCase(TestCase):

    def test_create_my_object(self):
        with uuid_mock('my_uuid_1'):
            self.assertEqual('my_uuid_1', str(uuid.uuid4()))
        with uuid_mock('my_uuid_2'):
            self.assertEqual('my_uuid_2', str(uuid.uuid4()))


if __name__ == '__main__':
    unittest.main()
