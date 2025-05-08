import site


def test_get_site_packages():
    packages: list[str] = site.getsitepackages()
    print(packages)


def test_get_user_site_packages():
    path: str = site.getusersitepackages()
    print(path)


def test_get_user_base():
    user_base: str = site.getuserbase()
    print(user_base)


# DOES NOT WORK
def test_add_site_dir():
    print(site.getsitepackages())
    site.addsitedir("/home/aleks/.local/lib/python3.12/site-packages")
    print(site.getsitepackages())
