/* Should be located in "${HOME}/.local/share/Anki2/User 1/collection.media" */
function displayPosNoStyle(tags) {
    const posTags = new Map([
        ["en::parts::adjective", "Adjective"],
        ["en::parts::adverb", "Adverb"],
        ["en::parts::conjunction", "Conjunction"],
        ["en::parts::determiner", "Determiner"],
        ["en::parts::exclamation", "Exclamation"],
        ["en::parts::interjection", "Interjection"],
        ["en::parts::noun", "Noun"],
        ["en::parts::number", "Number"],
        ["en::parts::predeterminer", "Predeterminer"],
        ["en::parts::prefix", "Prefix"],
        ["en::parts::preposition", "Preposition"],
        ["en::parts::pronoun", "Pronoun"],
        ["en::parts::verb", "Verb"]
    ]);
    const posName = tags
        .split(" ")
        .map(tag => posTags.get(tag))
        .filter(item => item != null && item !== '')
        .join(", ");

    const id = "part-of-speech";
    let element = document.getElementById(id);
    if (element == null) {
        element = document.createElement("p");
        element.id = id;
        document.body.appendChild(element);
    }
    element.textContent = posName;
}
