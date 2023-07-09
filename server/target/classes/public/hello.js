const url = 'http://127.0.0.1:4567';

document.addEventListener('DOMContentLoaded', async () => {
    const msg = await getMisterWithVariable('Adi');
    addMessageToHtmlMessage(msg);
});

const getMessageFromServer = async () => {
    try {
        const response = await fetch(`${url}/world`);
        
        if (response.ok) {
            const data = await response.text();
            console.log(data);

            return data;
        }

    } catch (error) {
        console.log('Request failed:', error);
    }
};

const getMisterWithVariable = async (name) => {
    try {
        const response = await fetch(`${url}/mister`, {
            method : "POST",
            headers: {'Content-Type': 'text/html'}, 
            body: name,
        });

        if (response.ok) {
            const data = await response.text();
            console.log(data);
    
            return data;
        }

    } catch (error) {
        console.log('Request failed:', error);
    }
};

const addMessageToHtmlMessage = (msg) => {
    const helloMsg = document.getElementById('hello-msg');
    helloMsg.textContent += ` ${msg}`;
};