/* Should be located in "${HOME}/.local/share/Anki2/User 1/collection.media" */
function displayTextNoDuplication(tags) {
    const id = "my-text";
    let element = document.getElementById(id);
    if (element == null) {
        element = document.createElement("p");
        element.id = id;
        document.body.appendChild(element);
    }
    element.textContent = `All tags (Append Child In Media Function No Duplication): ${tags}`;
}