<script setup>
import GoalItem from '@/components/GoalItem.vue';
import ChallengeItem from '@/components/ChallengeItem.vue';
import ChallengeCircle from "@/components/ChallengeCircle.vue";
import GetChallenge from '@/components/GetChallenge.vue';
import NavBar from "@/components/NavBar.vue";
import "../assets/baseStyle.css";
import {computed, ref} from 'vue';
import axios from 'axios';
import { useTokenStore } from '@/stores/token';
import { navigateToPage } from '@/utils/httputils';
import { ip } from "@/utils/httputils.js";
import GoalStar from "@/components/GoalStar.vue";
import GetNewChallengeCircle from "@/components/GetNewChallengeCircle.vue";
import MakeNewGoalStar from "@/components/MakeNewGoalStar.vue";
import CreateGoalPopup from "@/components/CreateGoalPopup.vue";

const tokenStore = useTokenStore();
const loading = ref(true);
const newChallenge = ref(null);
const isNewChallengeModalOpened = ref(false);
const isGoalModalOpened = ref(false);
const isNewGoalPopupOpened = ref(false);
const goalInfo = ref([]);
const isChallengeModalOpened = ref(false);
const challengeInfo = ref([]);

const goals = ref([]);
const userHasNoGoals = ref(false);
const challenges = ref([]);


/**
 * Retrieves the users goals and challenges from backend.
 */
(async () => {
  try{
    await navigateToPage('/sparesti')
    const response = await axios.get(`https://${ip}:8443/api/goals/user/` + tokenStore.getLoggedInUser(), tokenStore.getAxiosAuthorizationConfig());

    response.data.sort((a, b) => new Date(b.startDate) - new Date(a.startDate));

    goals.value = response.data;

    if (goals.value.length === 0) {
      userHasNoGoals.value = true;
    }

    for (const goal of goals.value) {
      await updateChallengeList(goal.id);
    }

    loading.value = false;

  } catch (error){
    console.error('Error fetching data:', error);
  }
})();

/**
 * Updates the list of challenges for a given goal ID.
 * Sorts the challenges by start date and completion.
 *
 * @param {number} goalId - The ID of the goal to retrieve challenges for.
 * @returns {Promise} - A Promise indicating when the list has been updated.
 */
const updateChallengeList = async (goalId) => {
  try {
    const response = await axios.get(`https://${ip}:8443/api/challenges/goal/` + goalId, tokenStore.getAxiosAuthorizationConfig());
    const challengesData = response.data;
    const incompleteChallenges = challengesData.filter(challenge => !challenge.completed);
    const completedChallenges = challengesData.filter(challenge => challenge.completed);

    incompleteChallenges.sort((a, b) => new Date(b.startDate) - new Date(a.startDate));
    completedChallenges.sort((a, b) => new Date(b.startDate) - new Date(a.startDate));

    const sortedChallenges = [...incompleteChallenges, ...completedChallenges];
    challenges.value.push(sortedChallenges);

    loading.value = false;
  } catch (err) {
    console.error(err);
  } finally {
    loading.value = true;
  }
}

/**
 * Deletes a challenge from the server.
 *
 * @param {Object} challenge - The challenge to be deleted.
 * @throws {Error} If an error occurs during the deletion process.
 */
const deleteChallenge = (challenge) => {
  const bool = confirm('Er du helt sikker på at du ønsker å slette denne utfordringen?');
  if (bool) {
    try {
      axios.delete(`https://${ip}:8443/api/challenges/` + challenge.id, tokenStore.getAxiosAuthorizationConfig()
      ).then(function (response) {
        console.log(response);
        window.location.reload();
        closeChallengeModal();
      });
    } catch (err) {
      console.error(err);
    }
  }
}

/**
 * Deletes a goal.
 *
 * @param {Object} goalInfo - The goal information containing the ID of the goal to be deleted.
 * @throws {Error} If an error occurs while deleting the goal.
 */
const deleteGoal = (goalInfo) => {
  const bool = confirm('Er du helt sikker på at du ønsker å slette dette målet?');
  if (bool) {
    try {
      axios.delete(`https://${ip}:8443/api/goals/` + goalInfo.id, tokenStore.getAxiosAuthorizationConfig()
      ).then(function (response) {
        console.log(response);
        window.location.reload();
        closeGoalModal();
      });
    } catch (err) {
      console.error(err);
    }
  }
}

/**
 * Opens the new challenge modal and generates a new challenge goal.
 *
 * @function
 */
const openNewChallengeModal = () => {
  try {
    axios.get(`https://${ip}:8443/api/challenges/generate/goal/` + goals.value[0].id, tokenStore.getAxiosAuthorizationConfig()
    ).then(function (response) {
      const newGoalData = response.data;
      console.log(response);
      newChallenge.value = newGoalData;
      isNewChallengeModalOpened.value = true;
    });
  } catch (err) {
    console.error(err);
  }
};

/**
 * Opens the goal modal with the given goal information.
 *
 * @param {string} goal - The goal information to be displayed in the modal.
 * @returns {void}
 */
const openGoalModal = (goal) => {
  goalInfo.value = goal;
  isGoalModalOpened.value = true;
};

/**
 * Opens the new goal popup.
 *
 * @function
 * @name openNewGoalPopup
 * @returns {void}
 */
const openNewGoalPopup = () => {
  isNewGoalPopupOpened.value = true;
};

/**
 * Opens the challenge modal with the given challenge.
 *
 * @param {string} challenge - The challenge to display in the modal.
 */
const openChallengeModal = (challenge) => {
  challengeInfo.value = challenge;
  console.log(challengeInfo);

  isChallengeModalOpened.value = true;
};

/**
 * Closes the new challenge modal.
 * Sets the value of isNewChallengeModalOpened to false.
 *
 * @function
 * @name closeNewChallengeModal
 */
const closeNewChallengeModal = () => {
  isNewChallengeModalOpened.value = false;
};

/**
 * Closes the goal modal.
 * @function
 *
 * @returns {void}
 */
const closeGoalModal = () => {
  isGoalModalOpened.value = false;
};

/**
 * Closes the challenge modal.
 *
 * @function closeChallengeModal
 * @returns {void}
 */
const closeChallengeModal = () => {
  isChallengeModalOpened.value = false;
};

/**
 * Closes the new goal popup and refreshes the page.
 *
 * @function closeNewGoalPopup
 */
const closeNewGoalPopup = () => {
  isNewGoalPopupOpened.value = false;
  window.location.reload();
};

/**
 * Submits a new challenge to the server.
 *
 * @async
 * @function submitNewChallenge
 * @returns {Promise<void>} A Promise that resolves when the new challenge is successfully submitted.
 * @throws {Error} If an error occurs during the submission process.
 */
const submitNewChallenge = async () => {
  try {
    await axios.post(`https://${ip}:8443/api/challenges/goal/` + goals.value[0].id,
        JSON.parse(JSON.stringify(newChallenge.value)), tokenStore.getAxiosAuthorizationConfig()
    ).then(function (response) {
      console.log(response);
      window.location.reload();

    });
  } catch (err) {
    console.error(err);
  } finally {
    isNewChallengeModalOpened.value = false;
  }
}

/**
 * Represents whether to display the "Make New Goal" star.
 *
 * @type {Boolean}
 */
const shouldDisplayMakeNewGoalStar = computed(() => {
  if (goals.value.length > 0) {
    const firstGoal = goals.value[0];
    return firstGoal.completed || firstGoal.failed;
  }
  return false;
}).value;


</script>

<template>
  <div class="flex-container">
    <div class="header">
      <h1 class="title">Sparesti</h1>
    </div>
    <div v-if="loading" class="main" id="loading">Laster inn...</div>
    <div v-if="!loading" class="main" id="challenges">
      <div class="sparesti-content">
        <GoalItem :isOpen="isGoalModalOpened" :completed="goalInfo.completed" :failed="goalInfo.failed" :target="goalInfo.target" :progress="goalInfo.progress" @modal-close="closeGoalModal" :key="goalInfo.id" @delete="deleteGoal(goalInfo)" >
          <template #name>{{ goalInfo.name }}</template>
          <template #progress>{{goalInfo.progress}}</template>
          <template #target>{{ goalInfo.target }}</template>
          <template #date>{{ goalInfo.startDate }} - {{ goalInfo.endDate }}</template>
        </GoalItem>
        <ChallengeItem :deletable="!goalInfo.completed && (challenges[0] && challenges[0].some(challenge => challenge.id === challengeInfo.id))" :isOpen="isChallengeModalOpened" @modal-close="closeChallengeModal" :key="challengeInfo.id" @delete="deleteChallenge(challengeInfo)">
          <template #name>{{challengeInfo.name}}</template>
          <template #description>{{ challengeInfo.description }}</template>
          <template #target>{{challengeInfo.target}} </template>
          <template #progress>{{ challengeInfo.progress }}</template>
          <template #startDate>{{ challengeInfo.startDate }}</template>
          <template #endDate>{{ challengeInfo.endDate }}</template>
        </ChallengeItem>


        <MakeNewGoalStar v-if="userHasNoGoals" @click="openNewGoalPopup" :number-of-goals="0" class="make-new-goal-star"/>
        <CreateGoalPopup :isOpen="isNewGoalPopupOpened" @modal-close="closeNewGoalPopup"/>

        <div class="all-goals">
          <div class="goal-challenge-container" v-for="(goal, goalIndex) in goals" :key="goalIndex">

            <MakeNewGoalStar v-if="shouldDisplayMakeNewGoalStar" :previous-goal-is-completed="goals[0].completed" @click="openNewGoalPopup" class="make-new-goal-star"/>
            <GoalStar class="goal-star" :completed="goal.completed" :failed="goal.failed" :name="goal.name" :id="goal.id" @click="openGoalModal(goal)"/>

            <div v-if="goalIndex === 0 && !goals[0].completed">
              <GetNewChallengeCircle class="get-new-challenge-circle" @click="openNewChallengeModal"/>

              <GetChallenge :isOpen="isNewChallengeModalOpened"
                            @modal-close="closeNewChallengeModal"
                            @submit="submitNewChallenge" name="first-modal">
                <template #name>{{ newChallenge.name }}</template>
                <template #description>{{ newChallenge.description }}</template>
                <template #target>{{ newChallenge.target }}</template>
                <template #endDate>{{ newChallenge.endDate }}</template>
              </GetChallenge>
            </div>

            <div v-for="(c, cIndex) in challenges[goalIndex]" :key="cIndex">
              <ChallengeCircle class="challenge-circle" :id="c.id" :progress="c.progress" :target="c.target" :challengeName="c.name"
                               :completed="c.completed" @delete="deleteChallenge(c)" @click="openChallengeModal(c)"/>
            </div>

          </div>
        </div>


      </div>
    </div>
    <div class="footer">
      <nav-bar/>
    </div>
  </div>
</template>

<style>
.flex-container {
  display: flex;
  flex-direction: column;
  align-items: center;

}
#loading,
.sparesti-content{
  display: flex;
  flex-direction: column;
  align-items: center;
}

.challenge-circle {
  margin-top: -47px;
  position: relative;
}

.make-new-goal-star,
.goal-star {
  position: relative;
  margin-bottom: 30px;
}

.make-new-goal-star {
  margin-top: 50px;
}

.goal-challenge-container {
  position: relative;

}

.get-new-challenge-circle {
  margin-top: -48px;
}

</style>
