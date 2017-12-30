import abc


class TranscriptionService:
    """Base class for Transcription Services"""

    @abc.abstractmethod
    def find_transcription(self, text):
        """Get transcription of the text"""
        return
