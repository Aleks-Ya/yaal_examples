import abc


class Service:
    """Base class for Services"""

    def __init__(self, settings):
        self.settings = settings

    @abc.abstractmethod
    def find_transcription(self, text):
        """Get transcription of the text"""
        return
