from app.addon_catalog.common.data_types import Aggregation, AddonInfo


class Aggregator:

    @staticmethod
    def aggregate(addon_infos: list[AddonInfo]) -> Aggregation:
        addon_number: int = len(addon_infos)
        return Aggregation(addon_number)
