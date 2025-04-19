<script setup>
import {ref} from 'vue';
import router from "@/router/index.js";
import sparesti_logo from '@/assets/sparesti_logo.png'
import "../assets/loginStyle.css"

const logo = ref(sparesti_logo)

/**
 * Navigates to the login page.
 */
const navigateToLogin = () => {
  router.push('/login');
}

/**
 * Navigates to the registration page.
 */
const navigateToRegister = () => {
  router.push('/register');
}

</script>

<template>
  <div class="screen">
    <div id="logo">
      <img :src="logo" alt="Logo">
    </div>
    <button type="button" id="login" class="btn" @click="navigateToLogin">
      Logg inn
    </button>
    <button type="button" id="register" class="btn" @click="navigateToRegister">
      Registrer ny bruker
    </button>
    <div class="info">
      <div class="info-container">
        <h1 class="h">Hva er Sparesti?</h1>
        <h3>Ta sparingen din til nye høyder med Sparesti!</h3>
        <p class="intro">Sparesti er en tjeneste som gjør det enkelt og morsomt å spare til dine mål og drømmer.
          Du får lage din egen sti, basert på egne sparemål,
          hvor du kan få personlige utfordringer basert på dine forbruksvaner og ønsker Ved å spare i appen,
          får du ta spareutfordringer og se fremgang mot målene dine.</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.screen{
  width: 100vw;
  display: flex;
  flex-direction: column;
  font-family: Arial, serif;
}

#logo img {
  max-width: 60%;
  height: auto;
  aspect-ratio: 1/1;
}

.info {
  margin-top: 100px;
  text-align: left;
  padding: 10px;
  margin-bottom: 10px;
}

p {
  font-size: 16px;
}

.info-container {
  background-color: whitesmoke;
  min-width: 100%;
  max-width: 80vw;
  border-radius: 30px;
  text-align: center;
  padding: 10px;
  border: solid 6px #A4CD92;
  font-size: 15px;
  margin-bottom: 25px;
}

.btn {
  background-color: #A4CD92;
  border-radius: 30px;
  text-align: center;
  min-height: 50px;
  width: 25%;
  padding: 10px;
  border: none;
  font-size: 16px;
  margin-bottom: 25px;
  font-weight: bold;
  cursor: pointer;
}

.btn:hover{
  background-color: #89a76c !important;
}

@media screen and (max-width: 700px) {
  .btn {
    min-width: 40%;
  }

  .info{
    margin-right: 25px;
  }
}
</style>