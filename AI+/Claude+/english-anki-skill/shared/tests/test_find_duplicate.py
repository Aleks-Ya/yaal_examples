import json
import subprocess
import sys
import threading
from http.server import BaseHTTPRequestHandler, HTTPServer
from pathlib import Path

import find_duplicate

SCRIPT = Path(__file__).resolve().parent.parent / "scripts" / "find_duplicate.py"


def test_normalize_word_strips_a_an_to_and_lowercases():
    assert find_duplicate.normalize_word("Beggar") == "beggar"
    assert find_duplicate.normalize_word("a bucket") == "bucket"
    assert find_duplicate.normalize_word("an idea") == "idea"
    assert find_duplicate.normalize_word("to conquer") == "conquer"
    assert find_duplicate.normalize_word("conquer") == "conquer"


def test_normalize_word_does_not_strip_mid_word_article():
    # "in a predictable way" must NOT become "predictable way" -- only a
    # leading "a "/"an "/"to " counts as the cosmetic prefix.
    assert find_duplicate.normalize_word("in a predictable way") == "in a predictable way"


def test_pos_family_match_exact():
    assert find_duplicate.pos_family_match("en::parts::noun", ["en::parts::noun"])


def test_pos_family_match_parent_tag_matches_child_target():
    assert find_duplicate.pos_family_match(
        "en::parts::noun::countable", ["en::parts::noun", "source::en::smeag::2020"]
    )


def test_pos_family_match_child_tag_matches_parent_target():
    assert find_duplicate.pos_family_match(
        "en::parts::noun", ["en::parts::noun::countable"]
    )


def test_pos_family_match_ignores_non_pos_tags():
    assert not find_duplicate.pos_family_match(
        "en::parts::adjective", ["en::unit::phrase", "source::movie::the-guard"]
    )


def test_pos_family_match_unrelated_pos_does_not_match():
    assert not find_duplicate.pos_family_match(
        "en::parts::verb", ["en::parts::noun"]
    )


def test_find_duplicates_beggar_matches_despite_tag_granularity_difference():
    duplicates = find_duplicate.find_duplicates(
        "beggar",
        "en::parts::noun::countable",
        [{"id": 1579307261208, "english": "beggar", "tags": ["en::parts::noun"]}],
    )
    assert duplicates == [1579307261208]


def test_find_duplicates_predictable_phrase_note_is_not_a_duplicate():
    duplicates = find_duplicate.find_duplicates(
        "predictable",
        "en::parts::adjective",
        [{"id": 1482172556889, "english": "in a predictable way", "tags": ["en::unit::phrase"]}],
    )
    assert duplicates == []


def test_find_duplicates_no_candidates_returns_empty_list():
    assert find_duplicate.find_duplicates("to bat around", "en::parts::verb::phrasal", []) == []


def test_find_duplicates_article_prefix_on_either_side_is_ignored():
    duplicates = find_duplicate.find_duplicates(
        "to conquer",
        "en::parts::verb",
        [{"id": 999, "english": "conquer", "tags": ["en::parts::verb"]}],
    )
    assert duplicates == [999]


def test_find_duplicates_multiple_matches_are_all_returned():
    duplicates = find_duplicate.find_duplicates(
        "beggar",
        "en::parts::noun",
        [
            {"id": 1, "english": "beggar", "tags": ["en::parts::noun"]},
            {"id": 2, "english": "a beggar", "tags": ["en::parts::noun::countable"]},
        ],
    )
    assert duplicates == [1, 2]


def test_cli_end_to_end():
    payload = {
        "word": "beggar",
        "pos_tag": "en::parts::noun::countable",
        "candidates": [{"id": 1579307261208, "english": "beggar", "tags": ["en::parts::noun"]}],
    }
    result = subprocess.run(
        [sys.executable, str(SCRIPT)],
        input=json.dumps(payload),
        capture_output=True,
        text=True,
    )
    assert result.returncode == 0
    assert json.loads(result.stdout) == {"duplicates": [1579307261208]}


def _note(note_id, english, tags, **extra_fields):
    fields = {
        name: {"value": "", "order": i}
        for i, name in enumerate(
            ["English", "Transcription", "Definition", "Picture", "Russian", "Example-real-life",
             "Synonym1", "Synonyms", "Antonym1", "Antonyms", "Examples1-generated",
             "English-audio-generated", "Definition-audio-generated"]
        )
    }
    fields["English"]["value"] = english
    for name, value in extra_fields.items():
        fields[name]["value"] = value
    return {"noteId": note_id, "tags": tags, "fields": fields}


class _AnkiConnectStub(BaseHTTPRequestHandler):
    notes = []
    requests_seen = []

    def do_POST(self):
        length = int(self.headers["Content-Length"])
        request = json.loads(self.rfile.read(length))
        type(self).requests_seen.append(request)
        if request["action"] == "findNotes":
            result = [n["noteId"] for n in self.notes]
        elif request["action"] == "notesInfo":
            result = [n for n in self.notes if n["noteId"] in request["params"]["notes"]]
        else:
            result = None
        body = json.dumps({"result": result, "error": None}).encode("utf-8")
        self.send_response(200)
        self.send_header("Content-Type", "application/json")
        self.end_headers()
        self.wfile.write(body)

    def log_message(self, *args):
        pass


def _run_direct_cli(notes, word, pos_tag):
    _AnkiConnectStub.notes = notes
    _AnkiConnectStub.requests_seen = []
    server = HTTPServer(("127.0.0.1", 0), _AnkiConnectStub)
    thread = threading.Thread(target=server.serve_forever, daemon=True)
    thread.start()
    try:
        env = {
            **__import__("os").environ,
            "ANKICONNECT_URL": f"http://127.0.0.1:{server.server_port}",
        }
        result = subprocess.run(
            [sys.executable, str(SCRIPT), word, pos_tag],
            capture_output=True,
            text=True,
            env=env,
        )
    finally:
        server.shutdown()
    return result


def test_cli_direct_mode_returns_trimmed_note_for_unique_duplicate():
    notes = [
        _note(
            1579307261208,
            "a beggar",
            ["en::parts::noun", "en::to-refine"],
            **{"Definition": "a very poor person", "Example-real-life": "<ul><li>x</li></ul>"},
        ),
        _note(222, "to beggar belief", ["en::parts::verb"]),
    ]
    result = _run_direct_cli(notes, "beggar", "en::parts::noun::countable")

    assert result.returncode == 0, result.stderr
    parsed = json.loads(result.stdout)
    assert parsed["duplicates"] == [1579307261208]
    assert parsed["candidates_checked"] == 2
    note = parsed["note"]
    assert note["id"] == 1579307261208
    assert note["fields"]["English"] == "a beggar"
    assert note["fields"]["Definition"] == "a very poor person"
    assert note["fields"]["Example-real-life"] == "<ul><li>x</li></ul>"
    assert "Picture" not in note["fields"]  # trimmed: status covers emptiness
    assert "Picture" in note["status"]["empty_claude_fields"]
    assert "Definition" not in note["status"]["empty_claude_fields"]
    # findNotes query is scoped to the note type and normalizes the a/an/to prefix away
    find_query = _AnkiConnectStub.requests_seen[0]["params"]["query"]
    assert find_query == 'note:En-word-or-sentence "English:*beggar*"'


def test_cli_direct_mode_no_duplicates_has_null_note():
    result = _run_direct_cli([], "to conquer", "en::parts::verb")
    assert result.returncode == 0, result.stderr
    parsed = json.loads(result.stdout)
    assert parsed == {"duplicates": [], "candidates_checked": 0, "note": None}
    # no notesInfo round-trip when findNotes comes back empty
    assert [r["action"] for r in _AnkiConnectStub.requests_seen] == ["findNotes"]


def test_cli_direct_mode_ambiguous_match_returns_ids_but_no_note():
    notes = [
        _note(1, "beggar", ["en::parts::noun"]),
        _note(2, "a beggar", ["en::parts::noun::countable"]),
    ]
    result = _run_direct_cli(notes, "beggar", "en::parts::noun")
    assert result.returncode == 0, result.stderr
    parsed = json.loads(result.stdout)
    assert parsed["duplicates"] == [1, 2]
    assert parsed["note"] is None


def test_cli_direct_mode_unreachable_anki_exits_1():
    env = {
        **__import__("os").environ,
        "ANKICONNECT_URL": "http://127.0.0.1:1",  # nothing listens here
    }
    result = subprocess.run(
        [sys.executable, str(SCRIPT), "beggar", "en::parts::noun"],
        capture_output=True,
        text=True,
        env=env,
    )
    assert result.returncode == 1
    assert "error" in result.stderr
