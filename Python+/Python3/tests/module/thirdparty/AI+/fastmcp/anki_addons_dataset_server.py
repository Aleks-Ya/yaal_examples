# Run: uv run fastmcp dev inspector anki_addons_dataset_server.py
from datasets import load_dataset
from fastmcp import FastMCP

ds = load_dataset("Ya-Alex/anki-addons", split="train")
mcp = FastMCP("my-hf-data")


@mcp.tool
def search(text: str, limit: int = 5) -> list[dict]:
    """Find rows where the AI summarized description contains the given text."""
    hits = ds.filter(lambda row: row["ai"] and row["ai"]["summary"] and text.lower() in row["ai"]["summary"].lower())
    return hits.select(range(min(limit, len(hits)))).to_list()


@mcp.resource("addon://{id}")
def addon_name(id: int) -> str:
    """Get the name of an Anki add-on by its ID."""
    hits = ds.filter(lambda row: row["id"] == id)
    return hits[0]["anki_web"]["title"]


@mcp.resource("addon://{id}/description")
def addon_description(id: int) -> str:
    """Get the AI summarized description of an Anki add-on by its ID."""
    hits = ds.filter(lambda row: row["id"] == id)
    return hits[0]["ai"]["summary"]


if __name__ == "__main__":
    mcp.run()
