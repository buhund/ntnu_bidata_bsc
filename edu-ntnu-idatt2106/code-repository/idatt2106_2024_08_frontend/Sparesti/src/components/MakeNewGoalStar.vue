<script setup>
/**
 * A component that allows the user to add a new goal.
 *
 * @component
 * @props
 * {Number} numberOfGoals - The number of goals the user has.
 * {Boolean} previousGoalIsCompleted - Flag indicating whether the previous goal is completed.
 *
 * @function
 * changeIconOnHover - Changes the icon on hover.
 * resetIconAfterHover - Resets the icon after hover.
 *
 * @template
 * Includes an image element for the star icon and a text label.
 */
import greyStarIcon from '@/assets/grey-star-empty-icon.svg'
import blackStarIcon from '@/assets/black-star-empty-icon.svg'
import {ref} from "vue";

import { defineProps } from 'vue';

const props = defineProps({
  numberOfGoals: Number,
  previousGoalIsCompleted: Boolean,
});

const starIcon = ref(greyStarIcon);

// Function to change the icon on hover
function changeIconOnHover() {
  starIcon.value = blackStarIcon;
}

// Function to reset the icon after hover
function resetIconAfterHover() {
  starIcon.value = greyStarIcon;
}
</script>

<template>
  <div class="container" @mouseover="changeIconOnHover" @mouseleave="resetIconAfterHover">
    <div class="star_with_line">
      <div>
        <img :src="starIcon" alt="Star Icon" class="image" >
        <div class="plus">+</div>
      </div>
      <div v-if="props.numberOfGoals !==0 && !props.previousGoalIsCompleted" class="line"></div>

    </div>

    <div class="text">Legg til et nytt mål</div>
  </div>
</template>

<style scoped>


.container {
  display: flex;
  align-items: center;
  margin-left: 36px;
  margin-bottom: 33px;
}

.image {
  height: 75px;
  width: auto;
  margin-bottom: -3px;
}

.star_with_line {
  position: relative;
}

.line {
  position: absolute;
  top: 60px;
  left: 50%;
  width: 2px;
  transform: translateX(-50%);
  height: 50px;
  border: none;
  border-left: 5px dotted #8B8484;
}

.plus {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 50px;
  color: #8B8484;
}

.text {
  flex: 1;
  margin-left: 15px;
}

.container:hover .plus {
  color: #000000;
}

.container:hover .text {
  font-weight: bold;
}
</style>