/* Should be located in "${HOME}/.local/share/Anki2/User 1/collection.media" */
function displayTags(tags) {
    const newElement = document.createElement("p");
    newElement.textContent = `All tags (Append Child In Media Function): ${tags}`;
    document.body.appendChild(newElement);
}