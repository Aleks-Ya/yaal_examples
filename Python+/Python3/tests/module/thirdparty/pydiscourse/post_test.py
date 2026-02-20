from pydiscourse import DiscourseClient


def test_latest_post_from_topic(anonymous_anki_forum_client: DiscourseClient):
    slug: str = "note-size-addon-support"
    topic_id: int = 46001
    topic_data: dict = anonymous_anki_forum_client.topic(slug, topic_id)

    assert topic_data is not None
    assert "post_stream" in topic_data
    post_stream: dict = topic_data["post_stream"]
    assert "posts" in post_stream

    posts: list[dict] = post_stream["posts"]
    assert len(posts) > 0

    # Get the latest post (last in the list)
    latest_post: dict = posts[-1]

    print(f"\nLatest message from topic '{topic_data.get('title')}':")
    print(f"- Post ID: {latest_post.get('id')}")
    print(f"- Author: {latest_post.get('username')}")
    print(f"- Created at: {latest_post.get('created_at')}")
    print(f"- Content: {latest_post.get('cooked', 'N/A')[:200]}...")


def test_topic_301(anonymous_anki_forum_client: DiscourseClient):
    slug: str = 'add-on-support-thread-bulk-image-downloader-for-anki-googleapi-webp-during-review-by-shige'
    topic_id: int = 43541
    topic_data: dict = anonymous_anki_forum_client.topic(
        slug, topic_id, override_request_kwargs={"allow_redirects": True})
    print(topic_data)
    assert topic_data is not None
