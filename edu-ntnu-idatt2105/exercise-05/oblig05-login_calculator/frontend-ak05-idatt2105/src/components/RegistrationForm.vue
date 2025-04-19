<script setup lang="ts">
// src/components/RegistrationForm.vue

import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const username = ref("");
const password = ref("");
const registrationError = ref("");
const terms = ref(true);
const router = useRouter();

async function register() {
  if (!terms.value) {
    registrationError.value = 'You must accept the terms and conditions to register.';
    return;
  }

  const userData = {
    username: username.value,
    password: password.value,
  };

  try {
    const response = await axios.post('http://localhost:8080/api/auth/register', userData, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    // alert("Registration successful");
    // await router.push('/login');
    console.log("Registration successful", response.data.message);
    console.log("Registering:", userData);
    await router.push({ name: 'login' });
  } catch (error) {
    // Check if error is an AxiosError
    if (axios.isAxiosError(error)) {
      // TypeScript now know if this is an AxiosError, so it can access error.response safely
      const message = error.response?.data?.message || 'Failed to register.';
      registrationError.value = message;
    } else {
      // If not an AxiosError, handle it as a generic error
      console.error('An unexpected error occurred:', error);
      registrationError.value = 'An unexpected error occurred.';
    }
  }
}
</script>

<template>
  <form class="registration-form" @submit.prevent="register" novalidate>
    <h2>Register</h2>
    <label>User name:</label>
    <input v-model="username" type="text" required>

    <label>Password:</label>
    <input v-model="password" type="password" required>

    <div class="terms">
      <input type="checkbox" v-model="terms" disabled>
      <label>Accept terms and conditions</label>
    </div>

    <div v-if="registrationError" class="error-message">{{ registrationError }}</div>

    <div class="submit">
      <button type="submit" class="button">Register User</button>
    </div>
  </form>
</template>


<style scoped>

.input-invalid {
  border: 2px solid red;
}

.error-message {
  color: red;
  margin-top: 4px;
  font-size: 0.8em;
  font-weight: bold;
}

input[type="checkbox"] {
  display: inline-block;
  width: 16px;
  margin: 0 10px 0 0;
  position: relative;
  top: 2px;
}


.registration-form {
  width: 100%;
  max-height: 90vh;
  min-height: 30vh;
  max-width: 30vw;
  min-width: 20vw;
  border-radius: 10px;

  overflow-y: auto;

  padding: 40px;


  display: flex;
  flex-direction: column;
  align-content: flex-start;

  background-color: rgba(247, 247, 247, 0.86);
  border: 2px solid #222222;
  color: black;

  -webkit-box-shadow: 0px 2px 15px -12px rgba(0, 0, 0, 0.57);
  -moz-box-shadow: 0px 2px 15px -12px rgba(0, 0, 0, 0.57);
  box-shadow: 2px 15px 12px rgba(0, 0, 0, 0.57);

}

.login-form input,
.login-form textarea {
  max-width: 100%;
  resize: vertical;
}

.button {
  border: 0;
  padding: 10px 20px;
  margin-top: 20px;
  color: white;
  border-radius: 20px;
  background-color: rgba(50,176,214,0.68);
}

.submit {
  text-align: center;
}

</style>