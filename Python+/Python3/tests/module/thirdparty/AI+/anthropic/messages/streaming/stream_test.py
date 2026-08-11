from anthropic import Anthropic, TextEvent, ParsedMessageStopEvent, ParsedContentBlockStopEvent
from anthropic.types import MessageParam, ModelParam, RawMessageStreamEvent, RawContentBlockStartEvent, \
    RawMessageDeltaEvent, RawMessageStartEvent, RawContentBlockDeltaEvent, ParsedMessage, ContentBlock, ParsedTextBlock


def test_get_final_text(client: Anthropic, model: ModelParam, max_tokens: int):
    with client.messages.stream(
            model=model,
            max_tokens=max_tokens,
            messages=[MessageParam(
                role="user",
                content="Return just number 42"
            )]
    ) as stream:
        text: str = stream.get_final_text()
        assert "42" in text


def test_get_final_message(client: Anthropic, model: ModelParam, max_tokens: int, assert_blocks):
    with client.messages.stream(
            model=model,
            max_tokens=max_tokens,
            messages=[MessageParam(
                role="user",
                content="Return just number 42"
            )]
    ) as stream:
        message: ParsedMessage[str] = stream.get_final_message()
        assert message.stop_reason == "end_turn"
        assert message.type == "message"
        blocks: list[ContentBlock] = message.content
        assert_blocks(blocks, ParsedTextBlock)
        assert "42" in blocks[0].text


def test_text_stream(client: Anthropic, model: ModelParam, max_tokens: int):
    with client.messages.stream(
            model=model,
            max_tokens=max_tokens,
            messages=[MessageParam(
                role="user",
                content="Who created the Java programming language?"
            )]
    ) as stream:
        for text in stream.text_stream:
            print(f"===== {text}", end="", flush=True)


def test_event_types(client: Anthropic, model: ModelParam, max_tokens: int):
    events: list[RawMessageStreamEvent] = []
    with client.messages.stream(
            model=model,
            max_tokens=max_tokens,
            messages=[MessageParam(
                role="user",
                content="Who created the Java programming language?"
            )]
    ) as stream:
        for event in stream:
            events.append(event)
            event_type: str = event.type
            if type(event) == RawMessageStartEvent:
                print(f"{event_type}: {event}", flush=True)
            elif type(event) == RawMessageDeltaEvent:
                print(f"{event_type}: {event}", flush=True)
            elif type(event) == RawContentBlockStartEvent:
                print(f"{event_type}: {event.content_block}", flush=True)
            elif type(event) == RawContentBlockDeltaEvent:
                print(f"{event_type}: {event.delta}", flush=True)
            elif type(event) == TextEvent:
                print(f"{event_type}: {event.text}", flush=True)
            elif type(event) == ParsedMessageStopEvent:
                print(f"{event_type}: {event}", flush=True)
            elif type(event) == ParsedContentBlockStopEvent:
                print(f"{event_type}: {event}", flush=True)
            else:
                raise ValueError(f"Unknown event type: {event_type}")

    assert len(events) > 0
