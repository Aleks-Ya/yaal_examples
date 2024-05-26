const displayPos = require('./_display_pos');
const {JSDOM} = require('jsdom');
const {expect, test, beforeEach} = require('@jest/globals');

const elementId = "id1"
let div;

beforeEach(() => {
    const dom = new JSDOM(`<!DOCTYPE html><p>Hello world</p>`);
    div = dom.window.document.createElement('div');
    div.id = elementId;
    dom.window.document.body.appendChild(div);
    global.document = dom.window.document;
});

test('empty tag', () => {
    displayPos(elementId, "")
    expect(div.textContent).toBe("");
});

test('no pos tags', () => {
    displayPos(elementId, "tag1 nested::tag2")
    expect(div.textContent).toBe("");
});

test('single tag', () => {
    displayPos(elementId, "en::parts::noun")
    expect(div.textContent).toBe("Noun");
});

test('several tags', () => {
    displayPos(elementId, "en::parts::adjective tag1 nested::tag2 en::parts::noun")
    expect(div.textContent).toBe("Adjective, Noun");
});

test('duplicated tag', () => {
    displayPos(elementId, "en::parts::noun en::parts::noun")
    expect(div.textContent).toBe("Noun");
});

test('parent tag', () => {
    displayPos(elementId, "en::parts::verb::phrasal")
    expect(div.textContent).toBe("Verb");
});
