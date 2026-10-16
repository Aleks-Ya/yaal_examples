import tinytuya

def test_get_basic_info() -> None:
    # scan devices: python -m tinytuya scan
    d = tinytuya.Device('a316761b592490203bvkj7', 'IP_ADDRESS_HERE', 'LOCAL_KEY_HERE',
                        version=3.3)  # Set version to match your device (3.1, 3.2, 3.3, 3.4, or 3.5)
    data = d.status()
    print('Device status: %r' % data)