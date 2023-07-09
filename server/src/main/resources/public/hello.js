const xhr = new XMLHttpRequest();
xhr.open('GET', 'https://api.example.com/data', true);

xhr.onload = function() {
    if (xhr.status === 200) {
      const response = JSON.parse(xhr.responseText);
      console.log(response);

      return response;
    }
  };
  
  xhr.onerror = function() {
    console.log('Request failed.');
  };

document.addEventListener('DOMContentLoaded', () => {
    const msg = getMessageFromServer();
    addMessageToHtmlMessage();
});

const getMessageFromServer = () => {
    return xhr.send();
};

const addMessageToHtmlMessage = (msg) => {
    const helloMsg = document.getElementById('hello-msg');
    helloMsg.textContent += ` ${msg}`;
};