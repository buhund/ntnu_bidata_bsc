<script setup>

import axios from "axios";
import {useForm, useField} from 'vee-validate';
import { Form as ValidationForm } from 'vee-validate';
import * as yup from 'yup';
import { ref } from 'vue';
import {useTokenStore} from "@/stores/token.js";
import {navigateToPage} from "../utils/httputils.js";
import {ip} from "../utils/httputils.js";
import "../assets/loginStyle.css"
import GoBackButton from "@/components/GoBackButton.vue";
import {useRegisteringStore} from "@/stores/registering.js";

/**
 * Handles submission of the registration form.
 */
const {handleSubmit, errors, meta} =useForm();

/**
 * Token storage for handling authentication.
 */
const tokenStore = useTokenStore();
/**
 * Store for signifying you are setting up a user.
 */
const registeringStore = useRegisteringStore();

const emailInUse = ref('');
const confirmPasswordError = ref('');
const nameRegex = /^[A-Za-zæøåÆØÅ\s-]+$/;
const emailRegex = /^[a-zA-Z0-9._%+-æøåÆØÅ]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

/**
 * Field value for input fields with error handling.
 */
const { value: firstName } = useField('firstName', yup.string().required('Fornavn må fylles ut').matches(nameRegex, 'Fornavn kan bare inneholde bokstaver'));
const { value: lastName } = useField('lastName', yup.string().required('Etternavn må fylles ut').matches(nameRegex, 'Etternavn kan bare inneholde bokstaver'));
const { value: email } = useField('email', yup.string().required('Epost må fylles ut').matches(emailRegex, 'Må være en gyldig epost'));
const { value: password } = useField('password', yup.string().required('Passord må oppgis'));
const { value: confirmPassword } = useField('confirmPassword', yup.string().required('Passord må oppgis'));

/**
 * Handles submission of the form.
 */
const onSubmit = handleSubmit(async () => {
  try{
    if (password.value !== confirmPassword.value) {
      confirmPasswordError.value = 'Passordene matcher ikke';
      console.error(confirmPasswordError.value);
      return;
    }
    const response = await axios.post(`https://${ip}:8443/api/users`, {
      firstName: firstName.value,
      lastName: lastName.value,
      email: email.value,
      password: password.value
    })
    console.log(response.data);

    const response2 = await axios.post(`https://${ip}:8443/api/login`, {
      email: email.value,
      password: password.value,
    });
    console.log(response2.data);
    tokenStore.saveEmailAndTokenInStore(email.value, response2.data);
    registeringStore.isRegistering();
    await navigateToPage('/config')
  } catch (error){
    if (error.response && error.response.status === 409) {
      console.error("email already registered")
      emailInUse.value = 'Eposten er allerede registrert'
      email.value = '';
    }
    console.error('Login failed:', error);
  }
})

</script>

<template>
  <div class="screen">
    <GoBackButton page="/" class="goBackButton"/>
    <h1>Registrer ny bruker</h1>
  <ValidationForm @submit="onSubmit">
    <div class="inputs">
      <span v-if="errors.firstName" class="error">{{ errors.firstName }}</span>
      <input type="text" name ="firstName" placeholder="Fornavn" class="input" v-model="firstName" required>
      <span v-if="errors.lastName" class="error">{{ errors.lastName }}</span>
      <input type="text" name ="lastName" placeholder="Etternavn" class="input" v-model="lastName" required>
      <span v-if="errors.email" class="error">{{ errors.email }}</span>
      <input type="text" name ="email" placeholder="E-post adresse" class="input" v-model="email" required autocomplete="username">
      <span v-if="errors.password" class="error">{{ errors.password }}</span>
      <input type="password" name ="password" placeholder="Passord" class="input" v-model="password" required autocomplete="new-password">
      <span v-if="errors.confirmPassword" class="error">{{ errors.confirmPassword }}</span>
      <input type="password" name ="confirmPassword" placeholder="Gjenta passord" class="input" v-model="confirmPassword" required autocomplete="new-password">
      <div v-if="emailInUse" class="error"> {{emailInUse}}</div>
      <div v-if="confirmPasswordError" class="error"> {{confirmPasswordError}}</div>

    </div>
    <button class="register" type="submit" :disabled="!meta.valid || !meta.dirty">Registrer</button>
  </ValidationForm>
  </div>

</template>

<style scoped>

.register{
  background-color: #A4CD92;
  cursor: pointer;
}
.register:hover{
  background-color: #89a76c;
}
.register:disabled{
  cursor: not-allowed;
}

</style>