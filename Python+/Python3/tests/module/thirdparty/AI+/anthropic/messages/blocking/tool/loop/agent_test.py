from anthropic import Anthropic
from anthropic.types import ModelParam

from agent import Agent


def test_send_message_no_time(client: Anthropic, model: ModelParam):
    agent: Agent = Agent(client, model)
    response1: str = agent.send_message("Answer with single word `Annihilation`")
    assert response1 == "Annihilation"
    response2: str = agent.send_message("Now answer with single word `Grass`")
    assert response2 == "Grass"


def test_send_message_current_time(client: Anthropic, model: ModelParam):
    agent: Agent = Agent(client, model)
    response1: str = agent.send_message("Answer with single word `Annihilation`")
    print(response1)
    assert response1 == "Annihilation"
    response2: str = agent.send_message("What is the current time?")
    print(response2)
    assert "2026" in response2
    response3: str = agent.send_message("Now answer with single word `Grass`")
    print(response3)
    assert response3 == "Grass"
    response4: str = agent.send_message("What is 2 + 6 and 10 + 40?")
    print(response4)
    assert "8" in response4
    assert "50" in response4
