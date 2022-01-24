const eventData = "[{\"data\":\"id:id0\\nevent:eventName0\\n:comment0\\nretry:5000\\ndata:\",\"mediaType\":{\"type\":\"text\",\"subtype\":\"plain\",\"parameters\":{\"charset\":\"UTF-8\"},\"qualityValue\":1.0,\"wildcardType\":false,\"wildcardSubtype\":false,\"concrete\":true,\"subtypeSuffix\":null,\"charset\":\"UTF-8\"}},{\"data\":\"message0\",\"mediaType\":null},{\"data\":\"\\n\\n\",\"mediaType\":{\"type\":\"text\",\"subtype\":\"plain\",\"parameters\":{\"charset\":\"UTF-8\"},\"qualityValue\":1.0,\"wildcardType\":false,\"wildcardSubtype\":false,\"concrete\":true,\"subtypeSuffix\":null,\"charset\":\"UTF-8\"}}]"
const dataList = JSON.parse(eventData)
for (let dataObj of dataList) {
    const propertyList = dataObj.data.split('\n')
    for (let propertyStr of propertyList) {
        const keyValue = propertyStr.split(':')
        const key = keyValue[0]
        const value = keyValue[1]
        console.log('Property: ' + key + '=' + value)
    }
}
