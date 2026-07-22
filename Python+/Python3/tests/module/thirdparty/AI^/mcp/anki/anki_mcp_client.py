from typing import Optional
from contextlib import AsyncExitStack

from mcp import ClientSession, StdioServerParameters, ListToolsResult, Tool
from mcp.client.stdio import stdio_client
from mcp.types import Prompt


class AnkiMcpClient:
    def __init__(self):
        self.is_connected: bool = False
        self.session: Optional[ClientSession] = None
        self.exit_stack = AsyncExitStack()

    async def __connect_to_server(self):
        if self.is_connected:
            return
        server_params: StdioServerParameters = StdioServerParameters(
            command="/bin/bash",
            args=[
                "-c",
                'export NVM_DIR="$HOME/.nvm" && '
                'source "$NVM_DIR/nvm.sh" && '
                'nvm use --silent 24.13.0 && '
                'exec npx -y @ankimcp/anki-mcp-server --stdio',
            ],
            env=None
        )

        stdio_transport = await self.exit_stack.enter_async_context(stdio_client(server_params))
        self.stdio, self.write = stdio_transport
        self.session = await self.exit_stack.enter_async_context(ClientSession(self.stdio, self.write))

        await self.session.initialize()
        self.is_connected = True

    async def list_tools(self) -> list[Tool]:
        await self.__connect_to_server()
        result: ListToolsResult = await self.session.list_tools()
        return result.tools

    async def list_prompts(self) -> list[Prompt]:
        await self.__connect_to_server()
        result = await self.session.list_prompts()
        return result.prompts

    async def cleanup(self):
        if not self.is_connected:
            return
        await self.exit_stack.aclose()
        self.is_connected = False
        self.session = None
