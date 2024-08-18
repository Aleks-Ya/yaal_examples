class Greeting:
    def __init__(self, name: str):
        self.name = name

    def __enter__(self):
        return f"Dear, {self.name}!"

    def __exit__(self, exc_type, exc_value, traceback):
        pass


with Greeting("John") as text:
    assert text == "Dear, John!"
