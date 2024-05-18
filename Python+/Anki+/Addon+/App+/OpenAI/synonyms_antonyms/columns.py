from typing import List

nid_column: str = 'ID'
english_column: str = 'English Word'
pos_column: str = 'Part Of Speech'

synonym_number: int = 5
antonym_number: int = 5
synonym_headers: List[str] = [f'Synonym {i}' for i in range(1, synonym_number + 1)]
antonym_headers: List[str] = [f'Antonym {i}' for i in range(1, antonym_number + 1)]
