import asyncio

from claude_agent_sdk import query, ClaudeAgentOptions


async def main():
    async for message in query(
            prompt="Search for the weather in Bangkok now",
            options=ClaudeAgentOptions(allowed_tools=["Read", "Edit", "WebSearch"]),
    ):
        if hasattr(message, "result"):
            print(message.result)


asyncio.run(main())
