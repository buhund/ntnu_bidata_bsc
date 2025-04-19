<script setup>
/**
 * A Vue component that displays a star icon which changes based on the completion status and mouse interactions.
 * The star icon represents a task or an achievement's status (completed or not) and visually changes when hovered over.
 *
 * @component
 * @props
 *  completed - Boolean indicating whether the task is completed.
 *  id - Number representing a unique identifier for the task.
 *  name - String representing the name of the task or item associated with the star.
 *
 * @data
 *  currentStarIcon - The current icon displayed. Changes between four different star icons based on hover and completion status.
 *
 * @functions
 *  changeIconOnHover - Updates the star icon when hovered. It switches to a highlighted version if completed, or a darker version if not.
 *
 * @template
 *  Includes an image element for the star icon and a text label. The component listens for mouseover and mouseleave events to trigger icon changes.
 *
 * @style
 *  Scoped styles manage the layout and presentation, including hover effects and positioning for the star and associated text.
 *
 * @usage
 * Place the component in any part of your UI that needs to represent items or tasks with a visual completion indicator.
 * Control the displayed icon and behavior by passing the appropriate props.
 *
 * @example
 * <template>
 *   <StarIcon :completed="true" :id="1" name="Complete Profile"></StarIcon>
 * </template>
 */
import greyStar from '@/assets/grey-star-icon.svg'
import blackStar from '@/assets/black-star-icon.svg'
import yellowStar from '@/assets/yellow-star-icon.svg'
import yellowStarWithBlackBorder from '@/assets/yellow-star-black-border-icon.svg'

import {defineProps, ref} from 'vue';

const props = defineProps({
  completed: Boolean,
  failed: Boolean,
  id: Number,
  name: String
});

const currentStarIcon = ref(props.completed ? yellowStar : greyStar);

function changeIconOnHover(isHovered) {
  if (isHovered) {
    currentStarIcon.value = props.completed ? yellowStarWithBlackBorder : blackStar;
  } else {
    currentStarIcon.value = props.completed ? yellowStar : greyStar;
  }
}

</script>

<template>
  <div class="container" @mouseover="changeIconOnHover(true)" @mouseleave="changeIconOnHover(false)">
    <div class="star_with_line">
      <div v-if="props.completed || props.failed" class="line" :id="props.id + 'line'"
           :style="{ backgroundColor: props.completed ? '#A4CD92' : (props.failed ? '#8B8484' : '') }"></div>
      <img :src="currentStarIcon" alt="Star Icon" class="image">
    </div>
    <div class="text">{{props.name}}</div>
  </div>

</template>

<style scoped>
  .image {
    height: 75px;
    width: auto;
  }

  .container {
    display: flex;
    align-items: center;
    margin-left: 36px;
  }

  .star_with_line {
    position: relative;
  }

  .line {
    position: absolute;
    top: -47px;
    left: 50%;
    transform: translateX(-50%);
    width: 3px;
    height: 47px;
    background-color: #A4CD92;
  }

  .text {
    margin-left: 15px;
  }

  .container:hover{
    cursor: pointer;
  }

  .container:hover .text {
    text-decoration: underline;

  }
</style>