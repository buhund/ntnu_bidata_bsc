<script setup>
import NavBar from "@/components/NavBar.vue";
import "../assets/baseStyle.css";
import {ip} from "@/utils/httputils.js";
import {useRouter} from "vue-router";
const router = useRouter();
import {useTokenStore} from "@/stores/token.js";
import axios from "axios";
import {ref} from "vue";
import {navigateToPage} from "@/utils/httputils.js";
import {useField, useForm} from "vee-validate";
import { Form as ValidationForm } from 'vee-validate';
import * as yup from "yup";
import GoBackButton from "@/components/GoBackButton.vue";

const {handleSubmit, errors, meta} =useForm();
const firstName = ref('');
const lastName = ref('');
const email = ref('');
const confirmPasswordError = ref('');
const knowledgeLevel = ref('');
const { value: password } = useField('password', yup.string().required('Passord må oppgis'));
const { value: confirmPassword } = useField('confirmPassword', yup.string().required('Passord må oppgis'));
const showPassword1 = ref(false);
const showPassword2 = ref(false);
const togglePasswordVisibility1 = () => {
  showPassword1.value = !showPassword1.value;
}
const togglePasswordVisibility2 = () => {
  showPassword2.value = !showPassword2.value;
}

let habitChange;
let creationDate;
let lastLoginDate;
let loginStreak;

/**
 * Retrieves user data from backend and fills in the form.
 */
(async () => {
  try{
    const tokenStore = useTokenStore();
    await navigateToPage('/editPassword')
    const response2 = await axios.get(`https://${ip}:8443/api/users/` + tokenStore.getLoggedInUser(), tokenStore.getAxiosAuthorizationConfig());
    if (response2.status === 200) {
      const userData = response2.data
      firstName.value = userData.firstName
      lastName.value = userData.lastName
      email.value = userData.email
      knowledgeLevel.value = userData.knowledgeLevel
      habitChange = userData.willingnessToHabitChangeLevel
      lastLoginDate = userData.lastLoginDate
      creationDate = userData.creationDate
      loginStreak = userData.loginStreak
    } else {
      await router.push('/login')
    }
  } catch (error){
    console.error('Error fetching data:', error);
  }
})();

/**
 * Changes the user's password.
 */
const changePassword = async () => {
  if (password.value !== confirmPassword.value) {
    confirmPasswordError.value = 'Passordene matcher ikke';
    console.error(confirmPasswordError.value);
    return;
  }
  try {
    const tokenStore = useTokenStore();
    const updatedUserData = {
      firstName: firstName.value,
      lastName: lastName.value,
      email: email.value,
      knowledgeLevel: knowledgeLevel.value,
      password: password.value,
      willingnessToHabitChangeLevel: habitChange,
      creationDate: creationDate,
      loginStreak: loginStreak,
      lastLoginDate: lastLoginDate,
    };
    const response = await axios.put(`https://${ip}:8443/api/users/` + tokenStore.getLoggedInUser(), updatedUserData, tokenStore.getAxiosAuthorizationConfig());
    if (response.status === 200) {
      alert('Passordet ditt har blitt endret.');
      await navigateToPage('/profile')
    } else {
      alert('Feil ved endring av passord');
    }
  } catch (error) {
    console.error('Feil ved endring av passord:', error);
    alert('Feil ved endring av passord');
  }
};
</script>

<template>
  <div class="flex-container">
    <GoBackButton page="/profile" class="goBackButton"/>
    <div class="header">
        <h1 class="title">Endre passord</h1>
    </div>
    <div class="main" id="profile">
      <ValidationForm @submit="handleSubmit(changePassword)">


        <div class="password-container">
          <input :type="showPassword1 ? 'text' : 'password'" name="password" v-model="password" placeholder="Nytt passord" class="input" required autocomplete="current-password">
          <button type="button" @click="togglePasswordVisibility1" class="password-toggle-btn" >
            {{ showPassword1 ? 'Skjul' : 'Vis' }}
          </button>
        </div>
        <div class="password-container">
          <input :type="showPassword2 ? 'text' : 'password'" name="password" v-model="confirmPassword" placeholder="Gjenta passord" class="input" required autocomplete="current-password">
          <button type="button" @click="togglePasswordVisibility2" class="password-toggle-btn">
            {{ showPassword2 ? 'Skjul' : 'Vis' }}
          </button>
        </div>
        <div v-if="confirmPasswordError" class="error"> {{confirmPasswordError}}</div>

      </ValidationForm>

      <button class="btn" id="changePassword" @click="changePassword" :disabled="!meta.valid || !meta.dirty">Lagre</button><br><br>

    </div>
    <div class="footer">
      <nav-bar/>
    </div>
  </div>

</template>


<style>
.password-container {
  padding-top: 3px;
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center;
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
}

.password-toggle-btn:hover{
  background-color: #89a76c !important;
}

.goBackButton {
  position: absolute;
  top: 20px;
  left: 20px;
}
</style>