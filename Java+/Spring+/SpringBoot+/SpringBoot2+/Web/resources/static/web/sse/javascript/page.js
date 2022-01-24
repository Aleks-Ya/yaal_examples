
var source;
function closeEventSource() {
    const el = document.getElementById('messages');
    el.appendChild(document.createTextNode('Closing from client...'));
    el.appendChild(document.createElement('br'));
    el.appendChild(document.createElement('br'));
    source.close()
}
function openEventSource() {
    source = new EventSource('/emitter');
    source.onmessage = function (event) {
        const dataList = JSON.parse(event.data)
        for (let dataJson of dataList) {
            const propertiesList = dataJson.data.split('\n')
            const propertyMap = new Map(propertiesList.map(property => {
                const parts = property.split(':')
                const key = typeof parts[0] != 'undefined' ? parts[0] : 'comment'
                const value = parts[1]
                return [key, value]
            }))
            console.log(propertyMap)
            const type = propertyMap.get('event')
            const el = document.getElementById('messages');
            el.appendChild(document.createTextNode(type + ' - ' + propertyMap));
            el.appendChild(document.createElement('br'));
            el.appendChild(document.createElement('br'));
            if (type === 'close') {
                // source.close()
                el.appendChild(document.createTextNode('EventSource is closed from server'));
                el.appendChild(document.createElement('br'));
                el.appendChild(document.createElement('br'));
            }
        }
    }
    source.onerror = function (event) {
        const el = document.getElementById('messages');
        el.appendChild(document.createTextNode(event));
        el.appendChild(document.createElement('br'));
        el.appendChild(document.createElement('br'));
    }
}
function closeEmitter() {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", "/stop", false );
    xmlHttp.send( null );
}
