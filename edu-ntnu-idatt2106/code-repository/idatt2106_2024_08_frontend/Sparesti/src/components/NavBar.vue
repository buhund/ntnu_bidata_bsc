<script setup>
/**
 * A navigation bar that allows the user to navigate between the different pages of the application.
 * The navigation bar includes buttons for the home page, badges page, sparesti page, overview page, and profile page.
 * The buttons are styled as images with text labels.
 *
 * @component
 * @function
 * navigateToPage - Navigates to the specified page when a button is clicked.
 *
 * @template
 * Includes button elements for the home, badges, sparesti, overview, and profile pages.
 *
 * @style
 * Scoped styles control the layout and visual appearance of the navigation bar and buttons.
 */
import {ref} from "vue";
import {navigateToPage} from "@/utils/httputils.js";
import badges_logo from '@/assets/badges_logo.svg'
import home_logo from '@/assets/home_logo.svg'
import sparesti_menu_logo from '@/assets/sparesti_menu_logo.svg'
import overview_logo from '@/assets/overview_logo.svg'
import profile_logo from '@/assets/profile_logo.svg'

const goals = ref(badges_logo)
const home = ref(home_logo)
const sparesti = ref(sparesti_menu_logo)
const savings = ref(overview_logo)
const profile = ref(profile_logo)

</script>

<template>
  <div class ="navbar">
    <button type="button" id="home" class="btn" @click="navigateToPage('/home')">
      <img :src="home" alt="home">
      <h4>Hjem</h4>
    </button>
    <button type="button" id="goals" class="btn" @click="navigateToPage('/badges')">
        <img :src="goals" alt="goals">
        <h4>Badges</h4>
    </button>
    <button type="button" id="sparesti" class="btn" @click="navigateToPage('/sparesti')">
      <img :src="sparesti" alt="sparesti">
      <h4>Sparesti</h4>
    </button>
    <button type="button" id="overview" class="btn" @click="navigateToPage('/overview')">
      <img :src="savings" alt="sparing">
      <h4>Oversikt</h4>
    </button>
    <button type="button" id="profileBtn" class="btn" @click="navigateToPage('/profile')">
      <img :src="profile" alt="profile">
      <h4>Profil</h4>
    </button>
  </div>


</template>

<style scoped>
.navbar{
  justify-content: center;
  display: flex;
  align-items: center;
  flex-direction: row;
  text-align: center;
  position: relative;
  top: 0;
  background-color: #A4CD92;
  width: 100%;
  height: 100%;
  z-index: 999;
}

.btn img {
  height: 100%;
  padding-top: 10px;
}

.btn{
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0 20px 5px 20px;
  border: none;
  background-color: #A4CD92;
  height: 100%;
}

.btn * {
  min-height: 40px;
  margin-top: 5px;
}

.btn h4 {
  font-size: 15px;
}

.btn:hover{
  background-color: #89a76c !important;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

@media screen and (max-width: 700px) {
  .btn img {
    min-width: 20px;
    max-height: 35px;
    padding-top: 10px;
    padding: 2px;
  }

  .btn{
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 0 7px;
    border: none;
    background-color: #A4CD92;

  }
}

@media screen and (max-width: 360px) {
  .btn img {
    min-width: 20px;
    max-height: 35px;
    padding-top: 10px;
    padding: 2px;
  }

  .btn{
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 0 2px;
    border: none;
    background-color: #A4CD92;
  }
}

@media screen and (max-height: 500px) {
  .btn img {
    display: none;
  }

  .btn {
    justify-content: center;
  }

  .btn h4 {
    padding-bottom: 0;
  }
}
</style>