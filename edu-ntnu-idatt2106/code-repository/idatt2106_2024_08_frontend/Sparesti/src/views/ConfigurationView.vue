<script setup>
  import "../assets/baseStyle.css";
  import "../assets/configurationStyle.css";
  import {ref} from "vue";
  import {navigateToPage} from "@/utils/httputils.js";
  import {ip} from "@/utils/httputils.js";
  import EditKnowlegde from "@/components/Knowledge.vue";
  import {useTokenStore} from "@/stores/token.js";
  import axios from "axios";
  import router from "@/router/index.js";
  import {useRegisteringStore} from "@/stores/registering.js";

  const tokenStore = useTokenStore();
  const registeringStore = useRegisteringStore();

  const firstName = ref('');
  const lastName = ref('');
  const email = ref('');
  const password = ref('');
  const loaded = ref(false)
  let knowledgeLevel = 'low';
  let willingnessToHabitChangeLevel = '';
  let creationDate;
  let loginStreak;
  let lastLoginDate;

/**
 * Fetches user data and sets initial values on component mount.
 */
  (async () => {
    try{
      await navigateToPage('/config')
      if (!registeringStore.registering) {
        await navigateToPage("/home");
      }
      const response2 = await axios.get(`https://${ip}:8443/api/users/` + tokenStore.getLoggedInUser(), tokenStore.getAxiosAuthorizationConfig());
      if (response2.status === 200) {
        const userData = response2.data
        firstName.value = userData.firstName
        lastName.value = userData.lastName
        email.value = userData.email
        knowledgeLevel = userData.knowledgeLevel
        willingnessToHabitChangeLevel = userData.willingnessToHabitChangeLevel
        lastLoginDate = userData.lastLoginDate
        creationDate = userData.creationDate
        loginStreak = userData.loginStreak
        if (knowledgeLevel === null) {
          knowledgeLevel = 'low';
        }
        loaded.value = true;
      } else {
        registeringStore.notRegistering();
        await router.push('/login')
      }
    } catch (error){
      console.error('Error fetching data:', error);
    }
  })();

/**
 * Sets the knowledge level of the user.
 *
 * @returns {Promise<void>}
 */
  const setKnowledgeLevel = async () => {
    try {
      const tokenStore = useTokenStore();
      const updatedUserData = {
        firstName: firstName.value,
        lastName: lastName.value,
        email: email.value,
        knowledgeLevel: knowledgeLevel,
        willingnessToHabitChangeLevel: willingnessToHabitChangeLevel,
        lastLoginDate: lastLoginDate,
        creationDate: creationDate,
        loginStreak: loginStreak,
      };
      console.log(updatedUserData)
      const response = await axios.put(`https://${ip}:8443/api/users/` + tokenStore.getLoggedInUser(), updatedUserData, tokenStore.getAxiosAuthorizationConfig());
      if (response.status === 200) {
        console.log(response.status);
        await navigateToPage('/changeOfHabitConfig');
      } else {
        console.error('Feil ved oppretting av profil');
      }
    } catch (error) {
      console.error('Feil ved oppretting av profil:', error);
    }
  };

</script>

<template>
  <div class="flex-container">
    <div class="config-container">
      <div class="header">
        <h1 class="title">Konfigurering</h1>
      </div>
      <EditKnowlegde v-if="loaded.valueOf()" :knowledgeLevel="knowledgeLevel" @knowledgeLevel="(level) => knowledgeLevel = level"/><br><br>
      <br>
      <div class="buttonflex">
        <button class="basicButton" @click="setKnowledgeLevel">Neste</button>
      </div>
    </div>
    <div class="footer"></div>
  </div>

</template>

<style scoped>
.buttonflex{
  display: flex;
  justify-content: center;
}


h3 {
  padding-right: 30px;
}

label{
  display: flex;
  align-items: center;
  padding: 0.375em 0.75em 0.375em 0.375em;
}

.basicButton:hover{
  background-color: #89a76c !important;
}

</style>