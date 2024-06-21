const {expect, test} = require('@jest/globals');

test('error to string', () => {
    let fullErrorString;
    let errorMessage
    let stackTrace
    try {
        // noinspection ExceptionCaughtLocallyJS
        throw new Error("abc")
    } catch (error) {
        errorMessage = error.toString();
        stackTrace = error.stack;
        fullErrorString = `${errorMessage}\n${stackTrace}`;
    }
    expect(errorMessage).toBe("Error: abc");
    expect(stackTrace).not.toBeNull();
    expect(fullErrorString).not.toBeNull();
    expect(fullErrorString).not.toBeNull();
    console.log(stackTrace)
});