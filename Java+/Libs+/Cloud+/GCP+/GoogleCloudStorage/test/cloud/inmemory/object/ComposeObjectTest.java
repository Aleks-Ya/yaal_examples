package cloud.inmemory.object;

import cloud.inmemory.BaseInMemoryTest;

/**
 * In-memory storage doesn't support Storage#compose(), because FakeStorageRpc#compose() returns null.
 */
class ComposeObjectTest extends BaseInMemoryTest {
}
