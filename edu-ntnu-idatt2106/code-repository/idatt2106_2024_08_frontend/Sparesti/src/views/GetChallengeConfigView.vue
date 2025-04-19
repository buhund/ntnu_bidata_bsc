<script setup>
import "../assets/baseStyle.css";
import "../assets/configurationStyle.css";
import {ref} from "vue";
import axios from 'axios';
import GetChallenge from '../components/GetChallenge.vue';
import {navigateToPage} from "@/utils/httputils.js";
import { useTokenStore } from '@/stores/token';
import {ip} from "@/utils/httputils.js";
import {useRegisteringStore} from "@/stores/registering.js";

const tokenStore = useTokenStore();
const registeringStore = useRegisteringStore();

const loading = ref(false);
const curentGoal = ref([]);
const newChallenge = ref(null);
const isModalOpened = ref(false);
const challenges = ref([]);
const newChallengebuttonVisible = ref(true);

/**
 * Retrieves user data from backend
 * */
(async () => {
  try {
    const tokenStore = useTokenStore();
    await navigateToPage('/challengeConfig')
    if (!registeringStore.registering) {
      await navigateToPage("/home");
    }
    axios.get(`https://${ip}:8443/api/goals/user/` + tokenStore.getLoggedInUser(), tokenStore.getAxiosAuthorizationConfig()
    ).then(function (response) {
      const goalData = response.data;
      console.log(goalData[0]);
      curentGoal.value = goalData[0];
      updateChallengeList();
      loading.value = true;
    });
  } catch (err) {
    console.error(err);
  } finally {
    loading.value = false
  }
})();


/**
 * Retrieves and updates the list of challenges associated with the current goal
 */
const updateChallengeList = () => {
  try {
    axios.get(`https://${ip}:8443/api/challenges/goal/` + curentGoal.value.id, tokenStore.getAxiosAuthorizationConfig()
    ).then(function (response) {
      const goalData = response.data;
      console.log("Updated challenges:" , response.data);
      challenges.value = goalData;
      loading.value = true;
    });
  } catch (err) {
    console.error("Error updating challenges:", err)
  } finally {
    loading.value = false;
  }
}

/**
 * Handles the submission of a new challenge, sends a POST request to the server, and updates the challenge list accordingly
 */
const submitHandler = async () => {
  console.log(newChallenge);
  try {
    await axios.post(`https://${ip}:8443/api/challenges/goal/` + curentGoal.value.id,
        JSON.parse(JSON.stringify(newChallenge.value)), tokenStore.getAxiosAuthorizationConfig()
    ).then(function (response) {
      console.log("New challenge added:", response.data);
      updateChallengeList();
      console.log(newChallengebuttonVisible);
      newChallengebuttonVisible.value = false;
    });
  } catch (err) {
    console.error("Error submitting new challenge:", err);
  } finally {
    isModalOpened.value = false;
  }
}

/**
 * Navigates to home
 */
const navigateToHome = () => {
  registeringStore.notRegistering();
  navigateToPage('/home')
}

/**
 * Opens a modal to generate a new challenge for the logged-in user
 */
const openModal = () => {
  console.log(tokenStore.getLoggedInUser());
  try {
    axios.get(`https://${ip}:8443/api/challenges/generate/goal/` + curentGoal.value.id, tokenStore.getAxiosAuthorizationConfig()
    ).then(function (response) {
      const newGoalData = response.data;
      console.log(response);
      newChallenge.value = newGoalData;
      
      isModalOpened.value = true;
      if (newChallengebuttonVisible.value) {
        newChallengebuttonVisible.value = false;
      }
    });
  } catch (err) {
      console.error(err)  
    }
};

/**
 * Closes the currently opened modal
 */
const closeModal = () => {
  isModalOpened.value = false;
};

</script>

<template>
  <div class="flex-container">
    <div class="challenge-content">
      <div class="header">
        <h1 class="title">Konfigurering</h1>
      </div>
      <div class="main">
        <div class="buttonflex" v-if="loading">
          <h3 v-if="challenges.length != 0">Dine utfordringer:</h3>
          <div class="challenges" v-for="challenge in challenges" :key="challenge.id" :challenge-id="challenge.id">
            {{ challenge.name }}
          </div>
          <button v-if="newChallengebuttonVisible" class="basicButton" @click="openModal">Ny Utfordring</button>
          <button v-if="!newChallengebuttonVisible" class="basicButton" @click="openModal">Ny Utfordring</button>
          <button v-if="!newChallengebuttonVisible" class="basicButton" @click="navigateToHome">Fullfør Konfigurering</button>
          <GetChallenge :isOpen="isModalOpened" @modal-close="closeModal" @submit="submitHandler" name="first-modal">
            <template #name>{{ newChallenge.name }}</template>
            <template #description>{{ newChallenge.description }}</template>
            <template #target>{{ newChallenge.target }}</template>
            <template #endDate>{{ newChallenge.endDate }}</template>
          </GetChallenge>
          <button class="basicButton" v-if="newChallengebuttonVisible" @click="navigateToHome" >Hopp over</button>
        </div>
      </div>
    </div>
    <div class="footer"></div>
  </div>
</template>

<style>
.buttonflex{
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.basicButton{
  text-align: center;
  min-width: 20vw;
  margin-bottom: 10px;
  margin-top: 10px;
}

.basicButton:hover{
  background-color: #89a76c !important;
}
.challenges {
  margin: 8px;
  padding: 8px;
  font-size: large;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
  border: 2px solid white;
  background-color: white;
  cursor: default;
}


</style>