import dataclasses


@dataclasses.dataclass
class AddonHeader:
    id: str
    title: str
    addon_page: str
    rating: str
    update_date: str
    versions: str


@dataclasses.dataclass
class AddonDetails:
    row: AddonHeader
    github_links: list[str]
