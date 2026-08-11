import pytest
from agents import Agent, Runner, set_default_openai_client, RunResult
from openai import AsyncOpenAI


@pytest.mark.asyncio
async def test_hello_agent(async_client: AsyncOpenAI) -> None:
    set_default_openai_client(async_client)
    agent: Agent = Agent(
        name="HelloAgent",
        instructions="You are a helpful assistant. Reply with a short greeting.",
        model="gpt-5.2"
    )

    result: RunResult = await Runner.run(agent, "Say: Hello, world!")
    text: str = result.final_output
    print(text)
    assert text == "Hello, world!"
