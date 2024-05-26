const sum = require('./sum');
const {expect, test, beforeEach, afterEach} = require('@jest/globals');

let something;

beforeEach(() => {
    something = "setup";
});

test('adds 1 + 2 to equal 3', () => {
    expect(sum(1, 2)).toBe(3);
});

afterEach(() => {
    something = null;

});
