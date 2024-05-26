/* Should be located in "${HOME}/.local/share/Anki2/User 1/collection.media" */
function displayText() {
    const newElement = document.createElement("p");
    newElement.textContent = "Append Child In Media Function";
    document.body.appendChild(newElement);
}