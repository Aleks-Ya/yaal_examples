function myFunction() {
    return document.title;
}

// Test case
test('substitute document from jsdom', () => {
    global.document = {
        title: 'Mock Title',
    }

    // jest.spyOn(global, 'document', 'get').mockReturnValue(mockDocument);

    expect(myFunction()).toBe('Mock Title');
});