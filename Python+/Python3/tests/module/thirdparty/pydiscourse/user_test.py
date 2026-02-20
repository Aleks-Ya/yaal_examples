from pydiscourse import DiscourseClient


def test_user_details(anonymous_anki_forum_client: DiscourseClient):
    username: str = "dae"  # Damien Elmes, creator of Anki
    user_data: dict = anonymous_anki_forum_client.user(username)

    assert user_data is not None
    assert "username" in user_data
    assert "id" in user_data

    print(f"\nUser details for '{username}':")
    print(f"- Username: {user_data.get('username')}")
    print(f"- ID: {user_data.get('id')}")
    print(f"- Name: {user_data.get('name', 'N/A')}")
    print(f"- Trust level: {user_data.get('trust_level', 'N/A')}")
    print(f"- Created at: {user_data.get('created_at', 'N/A')}")
    print(f"- Post count: {user_data.get('post_count', 'N/A')}")
    print(f"- Topic count: {user_data.get('topic_count', 'N/A')}")
