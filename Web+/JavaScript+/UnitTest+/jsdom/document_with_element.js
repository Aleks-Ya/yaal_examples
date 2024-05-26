const {JSDOM} = require('jsdom');
const assert = require("node:assert");

const elementId = "id1"
const dom = new JSDOM(`<!DOCTYPE html><p>Hello world</p>`);
let div = dom.window.document.createElement('div');
div.id = elementId;
div.textContent = "abc"
dom.window.document.body.appendChild(div);

assert(dom.window.document.getElementById(elementId).textContent === "abc")