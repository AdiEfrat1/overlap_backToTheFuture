document.addEventListener('DOMContentLoaded', () => {
    const msg = getMessageFromServer();
    addMessageToHtmlMessage();
});

const getMessageFromServer = async () => {
};

const addMessageToHtmlMessage = (msg) => {
    const helloMsg = document.getElementById('hello-msg');
    helloMsg.textContent += ` ${msg}`;
};