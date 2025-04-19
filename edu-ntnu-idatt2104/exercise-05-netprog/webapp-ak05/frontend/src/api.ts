// src/api.js
import axios from 'axios';

const API_BASE_URL = 'http://localhost:5240/api';

export const compileCode = async (code: string): Promise<any> => {
  try {
    const response = await axios.post(`${API_BASE_URL}/compilation/compile`, { code });
    return response.data;
  } catch (error: unknown) {
    // Using type assertion to handle the error as an Axios error and get better error messages
    if (axios.isAxiosError(error)) {
      // This makes TypeScript know that the error is an AxiosError, enabling better error messages/responses
      if (error.response) {
        console.error('Error status:', error.response.status);
        console.error('Error data:', error.response.data);
        const errorMessage = `Compilation error: ${error.response.data.message || 'Unknown error occurred.'}`;
        throw new Error(errorMessage);
      } else if (error.request) {
        console.error('No response received:', error.request);
        throw new Error('No response from server. Please check your network connection.');
      } else {
        console.error('Error setting up request:', error.message);
        throw new Error(`Error setting up compilation request: ${error.message}`);
      }
    } else {
      // Handle non-Axios error
      console.error('Unexpected error:', error);
      throw new Error('Unexpected error occurred during compilation.');
    }
  }
};


