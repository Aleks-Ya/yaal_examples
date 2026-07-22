import asyncio

from mcp import Tool
from mcp.types import Prompt

from anki_mcp_client import AnkiMcpClient


class AnkiMcpHost:
    def __init__(self):
        pass

    async def verify_connection(self) -> None:
        client: AnkiMcpClient = AnkiMcpClient()
        try:
            tools: list[Tool] = await client.list_tools()
            print("\nAvailable tools:", [tool.name for tool in tools])
            prompts: list[Prompt] = await client.list_prompts()
            print("\nAvailable prompts:", [prompt.name for prompt in prompts])
        finally:
            await client.cleanup()


if __name__ == "__main__":
    host: AnkiMcpHost = AnkiMcpHost()
    asyncio.run(host.verify_connection())
