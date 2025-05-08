from src.core.clazz.main.library import get_person


def main():
    person = get_person()
    print(f"main.py: Hello, {person}")


if __name__ == "__main__":
    main()

print('main.py: Invoked during import')
