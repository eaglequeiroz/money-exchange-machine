import axios from "axios";

const API_URL = process.env.REACT_APP_BACKEND_URL || "http://localhost:8080/exchange";

export const exchangeMoney = async (bill, maxCoins) => {
  try {
    const response = await axios.post(`${API_URL}?bill=${bill}&maxCoins=${maxCoins}`);
    return response.data;
  } catch (error) {
    return `Error: ${error.response?.data || "Unable to process request"}`;
  }
};

export const increaseCoinInventory = async (coinUpdates) => {
  return axios.post(`${API_URL}/increase`, coinUpdates);
};