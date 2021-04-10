package cloud.inmemory.object;

import cloud.inmemory.BaseInMemoryTest;

/**
 * In-memory storage doesn't support Storage#compose(), because FakeStorageRpc#compose() returns null.
 */
public class ComposeObject extends BaseInMemoryTest {
}
