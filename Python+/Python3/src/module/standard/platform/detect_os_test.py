import platform


def test_system():
    os_name: str = platform.system()
    if os_name == 'Windows':
        print("Running on Windows")
    elif os_name == 'Linux':
        print("Running on Linux")
    else:
        print("Running on another OS:", os_name)


def test_platform():
    platform_description: str = platform.platform()
    print(platform_description)


def test_architecture():
    bits, linkage = platform.architecture()
    print(bits)
    print(linkage)
