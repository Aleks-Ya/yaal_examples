package gcp.storage.inmemory.object;

import gcp.storage.inmemory.BaseInMemoryTest;

/**
 * In-memory storage doesn't support Storage#compose(), because FakeStorageRpc#compose() returns null.
 */
@SuppressWarnings("unused")
class ComposeObjectTest extends BaseInMemoryTest {
}
