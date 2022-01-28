
var source;
function openEventSource() {
    console.info("Creating EventSource...")
    source = new EventSource('/createEmitter');
    source.onmessage = function (event) {
        console.info("OnMessage event starting...")
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
        console.info("OnMessage event finished.")
    }
    source.onerror = function (event) {
        console.info("OnError event starting...")
        const el = document.getElementById('messages');
        el.appendChild(document.createTextNode(event));
        el.appendChild(document.createElement('br'));
        el.appendChild(document.createElement('br'));
        console.info("OnError event finished.")
    }
    console.info("EventSource is created.")
}
function emit() {
    console.info("Sending emitting request...")
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", "/emit", false );
    xmlHttp.send( null );
    console.info("Emitting request is sent.")
}
function closeEmitter() {
    console.info("Sending close emitter request...")
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", "/stopEmitter", false );
    xmlHttp.send( null );
    console.info("Close emitter request is sent.")
}
function closeEventSource() {
    console.info("Cosing EventSource...")
    const el = document.getElementById('messages');
    el.appendChild(document.createTextNode('Closing from client...'));
    el.appendChild(document.createElement('br'));
    el.appendChild(document.createElement('br'));
    source.close()
    console.info("EventSource is closed.")
}
