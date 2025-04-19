<script setup>
import NavBar from "@/components/NavBar.vue";
import "../assets/baseStyle.css";
import { useTokenStore } from "@/stores/token.js";
import { navigateToPage } from "@/utils/httputils.js";
import { ip } from "@/utils/httputils.js";
import axios from "axios";
import router from "@/router/index.js";
import {computed, defineEmits, ref} from "vue";

const missingBadges = ref([]);
const userBadges = ref([]);
const badgeAssetsPath = computed(() => `https://${ip}:8443/api`);


  const allBadges = computed(() => {
  const achievedBadges = userBadges.value.map(badge => ({ ...badge, achieved: true }));
  const unachievedBadges = missingBadges.value.map(badge => ({ ...badge, achieved: false }));

  const combinedBadges = achievedBadges.concat(unachievedBadges);

  return combinedBadges.sort((a, b) => b.achieved - a.achieved);
});

const showPopup = ref(false);
const currentBadge = ref({});

/**
 * Display the badge popup with the given badge.
 *
 * @param {string} badge - The badge to be displayed in the popup.
 */
function displayBadgePopup(badge) {
  currentBadge.value = badge;
  showPopup.value = true;
}

/**
 * Closes the popup window.
 *
 * @return {void} This method does not return any value.
 */
function closePopup() {
  showPopup.value = false;
}

/**
 * Fetches user data and sets initial values on component mount.
 */
(async () => {
  const tokenStore = useTokenStore();
  await navigateToPage('/badges');
  const response = await axios.get(`${badgeAssetsPath.value}/user/${tokenStore.getLoggedInUser()}/badges`, tokenStore.getAxiosAuthorizationConfig());
  const response2 = await axios.get(`${badgeAssetsPath.value}/user/${tokenStore.getLoggedInUser()}/missing_badges`, tokenStore.getAxiosAuthorizationConfig());

  if (response.status === 200 && response2.status === 200) {
    userBadges.value = response.data;
    missingBadges.value = response2.data;
  } else {
    await router.push('/login');
  }
})();
</script>

<template>
  <div class="flex-container">
    <div class="header">
      <h1 class="title">Badges</h1>
    </div>
    <div class="main">
      <div class="badge-container">
        <div v-for="badge in allBadges" :key="badge.id" class="badge-item" @click="displayBadgePopup(badge)">
          <img class="badge" :class="{ 'badge-greyed': !badge.achieved }" :src="`${badgeAssetsPath}${badge.filePath}.png`" alt="Badge Image">
          <div class="badge-description">{{ badge.name }} - {{ badge.description }}</div>
        </div>
      </div>
    </div>
    <div class="footer">
      <nav-bar/>
    </div>
  </div>

  <div v-if="showPopup" class="popup">
    <div class="popup-content">
      <div class="closeX">
        <div name="close" id="closePopup" @click="closePopup">&#10006</div>
      </div>
      <h3>{{ currentBadge.name }}</h3>
      <p>{{ currentBadge.description }}</p>
      <img :src="`${badgeAssetsPath}${currentBadge.filePath}.png`" alt="Current Badge">
    </div>
  </div>
</template>


<style>
  .main{
    width: 100%;
  }

  .badge-container{
    font-weight: bold;
    display: flex;
    flex-direction: column;
    font-size: 20px;
    align-items: flex-start;
    padding-left: 30px;
    justify-content: center;
    flex-grow: 1;
  }

  .closeX{
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
    font-size: 28px;
  }

  #closePopup:hover{
    color: #fb4c4e;
    background-color: white;
    cursor: pointer;
  }

  .badge {
    height: 16vh;
    width: 16vh;
    margin: 10px;
  }

  .badge:hover{
    cursor: pointer;
    margin-top: -2px;
    transition: margin-top 0.2s ease-in-out;
  }

  .badge-greyed {
    filter: grayscale(100%);
    opacity: 70%;
  }

  .badge-item {
    display: flex;
    align-items: flex-start;
    justify-content: start;
    gap: 10px;
  }

  .badge-description {
    display: inline-block;
  }

  .popup {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0,0,0,0.3);
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .popup-content {
    background: white;
    padding: 40px;
    border-radius: 35px;
    box-shadow: 0 4px 6px rgba(0,0,0,0.1);
    width: 20%;
    height: 40%;
    max-height: 70vh;
    overflow-y: hidden;
  }

  .popup-content img {
    width: 75%;
    height: auto;
    display: block;
    margin: 10px auto;
  }

  @media (min-width: 768px) {
    .badge-container {
      display: grid;
      grid-template-columns: repeat(3, 1fr);
    }
    .badge {
      height: 12vh;
      width: 12vh;
    }
  }
  .badge-description {
    display: inline-block;
    font-size: 16px;
  }
  @media (max-width: 767px) {
    .badge-description {
      font-size: 14px;
    }
    .badge {
      height: 8vh;
      width: 8vh;
    }
    .badge:hover {
      transform: scale(0.95);
    }
    .popup-content{
      width: 60%;
      height: 35%;
      overflow-y: scroll;
    }
  }
  </style>
