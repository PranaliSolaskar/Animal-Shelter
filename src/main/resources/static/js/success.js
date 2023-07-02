function myFunction() {
    alert('Hello from JavaScript!');
}

function triggerFunction() {
    fetch('/students')
        .then(response => response.text())
        .then(data => eval(data))
        .catch(error => console.error('Error:', error));
}
