<script setup>
import { ref } from 'vue';
import { useForm, useField } from 'vee-validate';
import { Form as ValidationForm } from 'vee-validate';
import * as yup from 'yup';
import { useRouter } from "vue-router";
import {ip} from "@/utils/httputils.js";
import axios from 'axios';
import {useTokenStore} from "@/stores/token.js";
import GoBackButton from "@/components/GoBackButton.vue";
import "../assets/loginStyle.css"

const router = useRouter();

/**
 * Navigates to the home page.
 */
const navigateToHome = () => {
  router.push('/home');
}

/**
 * Handles submission of the login form.
 */
const { handleSubmit, errors, meta } = useForm();

/**
 * Token storage for handling authentication.
 */
const tokenStore = useTokenStore();
tokenStore.getAxiosAuthorizationConfig()

const userNotFound = ref('');

/**
 * Handles submission of the form.
 */
const onSubmit = handleSubmit(async () => {
  try {
    const response = await axios.post(`https://${ip}:8443/api/login`, {
      email: email.value,
      password: password.value,
    });
    console.log(response.data);
    tokenStore.saveEmailAndTokenInStore(email.value, response.data);
    navigateToHome();
  } catch (error) {
    if (error.response && error.response.status === 401) {
      console.error('User not found. Please check your credentials.');
      userNotFound.value = 'E-post adresse og/eller passordet er feil. Prøv igjen'
    } else {
      console.error('Login failed:', error);
    }
  }
});

/**
 * Represents a value indicating whether the password should be shown or hidden.
 *
 * @type {boolean}
 */
const showPassword = ref(false);

/**
 * Toggles password display between visible and hidden.
 */
const togglePasswordVisibility = () => {
  showPassword.value = !showPassword.value;
}


/**
 * Regular expression pattern for validating email addresses*/
const emailRegex = /^[a-zA-Z0-9._%+-æøåÆØÅ]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

/**
 * Represents an email address.
 *
 * @class
 */
const { value: email } = useField('email', yup.string().required('Epost må fylles ut').matches(emailRegex, 'Må være en gyldig epost'));

/**
 * Represents a user's password.
 *
 * @class
 */
const { value: password } = useField('password', yup.string().required('Passord må fylles ut'));
</script>

<template class="view">
  <div class="screen">
    <GoBackButton page="/" class="goBackButton"/>
    <h1>Innlogging</h1>
    <ValidationForm @submit="onSubmit" class="form-container">
      <div class="input-container">
        <span v-if="errors.email" class="error">{{ errors.email }}</span>
        <input type="text" name="email" v-model="email" placeholder="Din e-post adresse" class="input" required autocomplete="username">
      </div>
      <div class="input-container">
        <span v-if="errors.password" class="error">{{ errors.password }}</span>
        <div class="password-container">
          <input :type="showPassword ? 'text' : 'password'" name="password" v-model="password" placeholder="Ditt passord" class="input" required autocomplete="current-password">
          <button type="button" @click="togglePasswordVisibility" class="password-toggle-btn">
            {{ showPassword ? 'Skjul' : 'Vis' }}
          </button>
        </div>
      </div>
      <div v-if="userNotFound" class="error">{{ userNotFound }}</div>
      <button type="submit" class="login" :disabled="!meta.valid || !meta.dirty">Logg inn</button>
      <router-link to="/forgotPassword" class="forgotPassword">Glemt passord?</router-link>
    </ValidationForm>
  </div>
</template>

<style scoped>
.password-container {
  padding: 4px;
  position: relative;
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.input-container {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
}

.error {
  width: 100%;
  text-align: center;
  margin-top: 5px;
}

.forgotPassword {
  display: flex;
  align-items: center;
  flex-direction: column;
  margin-bottom: 200px;
}

.password-toggle-btn{
  background-color: #A4CD92;
  min-width: 60px;
  border-radius: 30px;
  text-align: center;
  height: 40px;
  border: none;
  font-size: 15px;
  cursor: pointer;
  transform: translateY(-50%);
  position: absolute;
  right: 5px;
  top: 20px;
  margin-top: 12px;
  margin-right: 1px;
}

.password-toggle-btn{
  background-color: #A4CD92;
}
.password-toggle-btn:hover{
  background-color: #89a76c;
}
.login:disabled{
  cursor: not-allowed;
}
.login{
  background-color: #A4CD92;
  cursor: pointer;
}
.login:hover{
  background-color: #89a76c;
}

</style>