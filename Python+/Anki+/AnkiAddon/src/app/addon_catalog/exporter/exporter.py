from abc import abstractmethod, ABC
from pathlib import Path

from app.addon_catalog.common.data_types import AddonInfo, Aggregation


class Exporter(ABC):
    def __init__(self, dataset_dir: Path):
        self._dataset_dir: Path = dataset_dir
        self._dataset_dir.mkdir(parents=True, exist_ok=True)

    @abstractmethod
    def export_addon_infos(self, addon_infos: list[AddonInfo]) -> None:
        ...

    @abstractmethod
    def export_aggregation(self, aggregation: Aggregation) -> None:
        ...
