console.log("Executed now");

function sleep(millis) {
    var start = Date.now();
    while (Date.now() - start < millis);
}

sleep(2000)

console.log("Executed after 2 second");