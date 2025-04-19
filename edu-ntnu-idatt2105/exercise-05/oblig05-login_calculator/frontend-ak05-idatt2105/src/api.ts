// src/api.ts
import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

/**
 * Gets formula from Calculator.vue/equals function.
 * Uses axios to send a POST request to backend URL.
 * Payload is the string formula, wrapped in an { object }.
 * axios.post returns a "promise", which resolves the response from backend.
 *
 * Next: backend/controller/CalculatorController.java will receive and
 * evaluate the formula object.
 *
 * When the evaluation is complete, the response from the backend will contain the
 * evaluated expression result.
 * The calculateResult function returns this to the equals function.
 *
 * @param formula
 */
export const calculateResult = async (formula: string) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/calculate`, { formula });
    return response.data;
  } catch (error) {
    console.error('Error during API call:', error);
    throw error;
  }
};


export const saveCalculation = async (calculation) => {
  const token = localStorage.getItem('userToken');
  return axios.post(`${API_BASE_URL}/calculations`, calculation, {
    headers: { Authorization: `Bearer ${token}` },
  });
};

export const fetchCalculations = async (page) => {
  const token = localStorage.getItem('userToken');
  return axios.get(`${API_BASE_URL}/calculations?page=${page}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
};