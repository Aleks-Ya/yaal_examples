from anthropic import Anthropic, TextEvent, ParsedMessageStopEvent, ParsedContentBlockStopEvent
from anthropic.types import MessageParam, ModelParam, Message, RawMessageStreamEvent, RawContentBlockStartEvent, \
    RawMessageDeltaEvent, RawMessageStartEvent, RawContentBlockDeltaEvent


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


def test_message_stream(client: Anthropic, model: ModelParam, max_tokens: int):
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
            elif type(event) == RawContentBlockStartEvent:
                print(f"{event_type}: {event.content_block}", flush=True)
            elif type(event) == RawContentBlockDeltaEvent:
                print(f"{event_type}: {event.delta}", flush=True)
            elif type(event) == TextEvent:
                print(f"{event_type}: {event.text}", flush=True)
            elif type(event) == RawMessageStreamEvent:
                message: Message = event.message
                print(f"{event_type}: {message.content}", flush=True)
            elif type(event) == RawMessageDeltaEvent:
                print(f"{event_type}: {event}", flush=True)
            elif type(event) == ParsedMessageStopEvent:
                print(f"{event_type}: {event}", flush=True)
            elif type(event) == ParsedContentBlockStopEvent:
                print(f"{event_type}: {event}", flush=True)
            else:
                raise ValueError(f"Unknown event type: {event_type}")

    assert len(events) > 0
