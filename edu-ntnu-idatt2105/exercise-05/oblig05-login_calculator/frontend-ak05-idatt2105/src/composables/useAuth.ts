// src/composables/useAuth.js
import { ref, readonly } from 'vue';

const token = ref(localStorage.getItem('userToken'));
const username = ref(localStorage.getItem('username'));
const isLoggedIn = ref(!!token.value);

export function useAuth() {
  const login = (tokenValue, usernameValue) => {
    token.value = tokenValue;
    username.value = usernameValue;
    isLoggedIn.value = true;
    localStorage.setItem('userToken', tokenValue);
    localStorage.setItem('username', usernameValue);
  };

  const logout = () => {
    token.value = null;
    username.value = null; // Clear the username
    isLoggedIn.value = false;
    localStorage.removeItem('userToken');
    localStorage.removeItem('username');
  };

  return {
    isLoggedIn: readonly(isLoggedIn),
    username: readonly(username), // Exposing username so that it can be shown on the website
    login,
    logout,
  };
}
