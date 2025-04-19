<script setup>
import "../assets/baseStyle.css";
import "../assets/configurationStyle.css";
import {ref} from "vue";
import {useField} from 'vee-validate';
import axios from 'axios';
import CreateSavingsGoal from "@/components/CreateSavingsGoal.vue";
import {navigateToPage} from "@/utils/httputils.js";
import { useTokenStore } from '@/stores/token';
import {ip} from "@/utils/httputils.js";
import {useRegisteringStore} from "@/stores/registering.js";

const tokenStore = useTokenStore();
const registeringStore = useRegisteringStore();
const dateError = ref('');


/**
 * Retrieves the values of the fields 'newGoalName', 'newGoalTarget', and 'newGoalEndDate' using the useField hook
 */
const { value:newGoalName } = useField('newGoalName');
const { value:newGoalTarget } = useField('newGoalTarget');
const { value:newGoalEndDate } = useField('newGoalEndDate');

/**
 * Navigates to home
 */
const navigateToHome = () => {
  registeringStore.notRegistering();
  navigateToPage('/home');
}
/**
 * Make an attempt to navigate to the '/goalConfig' page and log any error messages.
 */
(async () => {
  try{
    await navigateToPage('/goalConfig')
    if (!registeringStore.registering) {
      await navigateToPage("/home");
    }
  } catch (error){
    console.error('Error fetching data:', error);
  }
})();


/**
 * Saves a new goal for the logged-in user
 */
const saveGoal = () => {
  const endDate = new Date(newGoalEndDate.value);
  const today = new Date();

  today.setHours(0, 0, 0, 0);
  if (endDate < today) {
    dateError.value = 'Du kan ikke velge en dato som allerede har vært.';
    return;
  }
  try {
    console.log(newGoalName.value)
			axios.post(`https://${ip}:8443/api/goals/user/` + tokenStore.getLoggedInUser(), {
				name: newGoalName.value,
				target: newGoalTarget.value,
				endDate: newGoalEndDate.value
			}, tokenStore.getAxiosAuthorizationConfig()

			).then(function (response) {
				const goalData = response.data;
				console.log(goalData);
        navigateToPage('/challengeConfig')
			});
		} catch (err) {
			console.log("Error saving goal" + err);
		}
}
</script>

<template>
  <div class="flex-container">
    <div class="challenge-content">
      <div class="header">
        <h1 class="title">Konfigurering</h1>
      </div>
      <div class="main">
        <div class="goal-content">
        <create-savings-goal
            v-model:nameValue="newGoalName"
            v-model:targetValue="newGoalTarget"
            v-model:endDateValue="newGoalEndDate"/>
          <div v-if="dateError" class="error">{{ dateError }}</div>
          <div class="buttonflex">
            <button class="basicButton" @click="saveGoal">Neste</button>
            <button class="basicButton" @click="navigateToHome">Hopp over</button>
          </div>
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

.goal-content{
  padding-top: 50px;
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
.goal-content{
  margin-top: -50px;
}

</style>