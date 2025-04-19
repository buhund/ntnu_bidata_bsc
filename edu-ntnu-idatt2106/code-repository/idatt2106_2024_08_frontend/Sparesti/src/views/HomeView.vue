<script setup>
import NavBar from "@/components/NavBar.vue";
import "../assets/baseStyle.css";
import {useTokenStore} from "@/stores/token.js";
import {navigateToPage} from "@/utils/httputils.js";
import {ip} from "@/utils/httputils.js";
import axios from "axios";
import router from "@/router/index.js";
import {ref} from "vue";
import NewsComponent from "@/components/NewsComponent.vue";

const tips = ref('');
const saved = ref('');
const savedMessage = ref('');

/**
 * Retrieves tips and savings balance for the logged-in user and updates the interface.
 * If the user is not logged in, they are redirected to the login page.
 */
(async () => {
  try{
    const tokenStore = useTokenStore();
    await navigateToPage('/home')
    const response = await axios.get(`https://${ip}:8443/api/tips/` + tokenStore.getLoggedInUser(), tokenStore.getAxiosAuthorizationConfig());
    const response2 = await axios.get(`https://${ip}:8443/api/savings/` + tokenStore.getLoggedInUser(), tokenStore.getAxiosAuthorizationConfig());
    if (response.status === 200 && response2.status === 200) {
      tips.value = response.data
      const userData = response2.data
      saved.value = response2.data
      saved.value = userData.savings
      savedMessage.value = userData.savingMessage
    } else {
      await router.push('/login')
    }
  } catch (error){
    if (error.response && error.response.status === 404) {
      console.error("user not found")
    }
    console.error('Error fetching data:', error);
  }
})();
</script>

<template>
  <div class="flex-container">
    <div class="header">
      <h1 class="title">Hjem</h1>
    </div>
    <div class="main">
      <div class="home-content">
        <div class="savings-container">
          <div class="savings-info">
            <p class="savings-title">Med Sparesti har du spart:</p>
            <div class="savings-circle">
            <p class="savings-amount">{{ saved }} KR</p>
            </div>
            <div class="saved-message-box" v-if="savedMessage">
              <div class="saved-message-content">{{savedMessage}}</div>
            </div><br>
          </div>
        </div>

        <div class="tip-box" v-if="tips">
          <div class="tip-title">TIPS!</div>
          <div class="tip-content">{{ tips }}</div>
        </div>
        <h1>Nyheter</h1>
        <NewsComponent />
        <p>Nyhetene er hentet fra E24 sin offentlige RSS-feed.</p>
      </div>
    </div>
    <div class="footer">
      <nav-bar/>
    </div>
  </div>
</template>

<style scoped>
.home-content{
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.tip-box{
  background-color: white;
  height: 10vh;
  width: 80vw;
  border-radius: 35px;
  text-align: center;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 50px;
  margin-bottom: 10px;
}
.tip-content {
  text-align: center;
  width: 100%;
  flex-grow: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}
.tip-title{
  font-weight: bold;
}

.savings-info {
  text-align: center;
  align-items: center;
  display: flex;
  flex-direction: column;
}

.savings-title {
  font-size: 1.5em;
  color: black;
}

.savings-amount {
  font-size: 2.5em;
  color: black;
  font-weight: bold;
}
.savings-circle {
  background-color:#fcd975;
  border-radius: 50%;
  width: 200px;
  height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
  box-shadow: 0 8px 16px rgba(0,0,0,0.1);
}

</style>
