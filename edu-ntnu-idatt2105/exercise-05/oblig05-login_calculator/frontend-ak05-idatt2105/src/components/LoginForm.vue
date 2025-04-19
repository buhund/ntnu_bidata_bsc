<!-- src/components/LoginForm.vue -->
<script setup lang="ts">
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { useAuth } from '@/composables/useAuth';

const username = ref('');
const password = ref('');
const loginError = ref('');

const { login } = useAuth();
const router = useRouter();

const performLogin = async () => {
  try {
    const response = await axios.post('http://localhost:8080/api/auth/authenticate', {
      username: username.value,
      password: password.value,
    }, {
      headers: {
        'Content-Type': 'application/json',
      },
    });

    login(response.data.token, username.value);
    // Redirect to the homepage or another page as needed
    await router.push('/');
  } catch (error) {
    loginError.value = 'Failed to log in. Please check your credentials.';
    console.error('Login error:', error);
  }
};
</script>



<template>
  <form class="login-form" @submit.prevent="performLogin" novalidate>
    <h2>Log In</h2>
    <label>User name:</label>
    <input v-model="username" type="text" required>

    <label>Password:</label>
    <input v-model="password" type="password" required>
    <div v-if="loginError" class="error-message">{{ loginError }}</div>

    <div class="submit">
      <button type="submit" class="button">Log in</button>
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


.login-form {
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