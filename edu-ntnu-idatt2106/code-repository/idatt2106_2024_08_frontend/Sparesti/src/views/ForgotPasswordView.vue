<script setup>

import {useField, useForm} from "vee-validate";
import { Form as ValidationForm } from 'vee-validate';
import * as yup from "yup";
import {ip} from "@/utils/httputils.js";
import axios from "axios";
import {ref} from "vue";
import GoBackButton from "@/components/GoBackButton.vue";
import "../assets/loginStyle.css"
import router from "@/router/index.js";

const emailNotFound = ref('');
const emailSentTo = ref('');
const isButtonDisabled = ref(false);

/**
 * Handles the submission of the forgotten password form.
 */
const { handleSubmit, errors, meta } = useForm();

/**
 * Sending an email with a link to reset your password.
 */
const sendEmail = handleSubmit(async () => {
  if (!isButtonDisabled.value) {
    isButtonDisabled.value = true; // Disable the button
    setTimeout(() => {
      isButtonDisabled.value = false; // Re-enable the button
    }, 10_000); // 10 000 milliseconds = 5 seconds

    try {
      const response = await axios.post(`https://${ip}:8443/api/forgot_password`, email.value, {
        headers: {
          "Content-Type": "text/plain"
        }
      });
      console.log(response.data);
      emailNotFound.value = '';
      emailSentTo.value = email.value;
    } catch (error) {
      if (error.response && error.response.status === 404) {
        console.error('This email is not registered.');
        emailNotFound.value = 'Eposten er ikke registrert';
      } else {
        console.error('Sending email with new password failed:', error);
      }
    }
  }
});

/**
 * Navigates the user back to the "login" page.
 *
 * @return {void}
 */
function goBack() {
  router.push("/login");
}

const emailRegex = /^[a-zA-Z0-9._%+-æøåÆØÅ]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

const { value: email } = useField('email', yup.string().required('Epost må fylles ut').matches(emailRegex, 'Må være en gyldig epost'));

</script>

<template>
  <div class="screen">
    <GoBackButton page="/login" class="goBackButton" @click="goBack"/>

    <h1>Få nytt passord</h1>
    <p class="description">Skriv inn e-postadressen du registrerte deg med.
      <br>Vi sender deg en lenke for å logge inn og tilbakestille passordet ditt.</p>

    <ValidationForm @submit="sendEmail">
      <span v-if="errors.email" class="error">{{ errors.email }}</span>
      <input type="text" name="email" v-model="email" placeholder="Din e-post adresse" class="input" required>

      <span v-if="emailNotFound" class="error">{{ emailNotFound }}</span>
      <button type="submit" class="sendNewPassword" :disabled="isButtonDisabled">Send nytt passord</button>
    </ValidationForm>

    <p v-if="emailSentTo">En epost med nytt passord er sendt til {{emailSentTo}}
      <br>OBS! Sjekk spamfilteret ditt hvis du ikke finner mailen.</p>

  </div>
</template>


<style scoped>

.description {
  margin-left: 50px;
  margin-right: 50px;
}
.sendNewPassword{
  background-color: #A4CD92;
  cursor: pointer;
}
.sendNewPassword:hover{
  background-color: #89a76c;
}
.sendNewPassword:disabled{
  cursor: not-allowed;
}

</style>