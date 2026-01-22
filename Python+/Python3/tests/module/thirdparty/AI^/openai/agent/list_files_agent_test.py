import os

import pytest
from agents import Agent, Runner, set_default_openai_client, RunResult, function_tool
from openai import AsyncOpenAI

from current_path import get_current_dir


@pytest.mark.asyncio
async def test_list_files_agent(async_client: AsyncOpenAI) -> None:
    set_default_openai_client(async_client)

    @function_tool
    def list_files(directory: str = get_current_dir()) -> list[str]:
        return os.listdir(directory)

    agent: Agent = Agent(
        name="ListFilesAgent",
        instructions="You are a helpful assistant that can list files in directories.",
        model="gpt-5.2",
        tools=[list_files]
    )

    result: RunResult = await Runner.run(agent, "List files in the current directory")
    text: str = result.final_output
    print(text)
    assert "list_files_agent_test.py" in text
