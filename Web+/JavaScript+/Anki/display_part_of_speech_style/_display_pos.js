/* Should be located in "${HOME}/.local/share/Anki2/User 1/collection.media" */

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

function displayPos(elementId, tags) {
    function keyStartsWith(prefix) {
        return Array.from(posTags.keys())
            .filter(key => prefix.startsWith(key))
            .map(key => posTags.get(key))
            .filter(items => items !== undefined && items !== null && items.length > 0);
    }

    const tagList = tags
        .split(" ")
        .filter(tag => tag.length > 0)
        .flatMap(tag => keyStartsWith(tag))
        .filter(item => item != null && item !== '');
    const uniqueTagList = Array.from(new Set(tagList));
    document.getElementById(elementId).textContent = uniqueTagList.join(", ");
}

module.exports = displayPos;