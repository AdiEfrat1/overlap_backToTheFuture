const SERVER_URL = 'http://localhost:4567';

export const getAllYoungs = async () => {
    try {
        const response = await fetch(`${SERVER_URL}/youngs/all`);

        if (response.ok) {
            const data = await response.json();

            return data;
        }

    } catch (error) {
        console.log('Request failed:', error);
    }
};

export const getDetailedYoung = async (id) => {
    try {
        const response = await fetch(`${SERVER_URL}/youngs/${id}`);

        if (response.ok) {
            const data = await response.json();

            return data;
        }

    } catch (error) {
        console.log('Request failed:', error);
    }
};

export const addNewYoung = async (young) => {
    try {
        await fetch(`${SERVER_URL}/youngs/add`, {
            method : "POST",
            headers: {'Content-Type': 'application/json'}, 
            body: JSON.stringify(young),
        });

    } catch (error) {
        console.log('Request failed:', error);
    }
};

export const removeYoung = async (id) => {
    try {
        await fetch(`${SERVER_URL}/youngs/remove/${id}`, {
            method : "DELETE",
            headers: {'Content-Type': 'application/json'}, 
        });
        
    } catch (error) {
        console.log('Request failed:', error);
    }
};