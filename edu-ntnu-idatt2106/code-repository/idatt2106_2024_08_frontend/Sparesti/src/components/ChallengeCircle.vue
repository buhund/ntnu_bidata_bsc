<script setup>
/**
 * A Vue component for displaying a progress indicator within a circle and a vertical line.
 * It visually represents the progress towards a challenge or goal and highlights completion status.
 *
 * @component
 * @props
 *  {Number} progress - The current progress value.
 *  {Number} target - The target or goal value to achieve.
 *  {Number} id - Unique identifier for the element, used for dynamic styling and element targeting.
 *  {String} challengeName - The name of the challenge.
 *  {Boolean} completed - Flag indicating whether the challenge is completed.
 *
 * @onMounted
 *  Calculates the percentage of progress and adjusts the height of the fill element accordingly.
 *  If the challenge is completed, it updates the visual style of the circle and line to indicate completion.
 *
 * @template
 *  Displays a circle with a rising fill based on progress, accompanied by a line and challenge name text.
 *  Utilizes hover effects to enhance interactivity.
 *
 * @style
 *  Scoped styles control the layout and visual interactions of the progress indicator and its components.
 *
 * @example
 * <template>
 *   <div class="container">
 *     <div class="circle_with_line">
 *       <div class="line" :id="props.id + 'line'"></div>
 *       <div class="circle" :id="props.id + 'circle'">
 *         <div class="fill" :id="props.id"></div>
 *       </div>
 *     </div>
 *     <div class="text">{{ props.challengeName }}</div>
 *   </div>
 * </template>
 */

import { onMounted } from "vue";

import { defineProps } from 'vue';

const props = defineProps({
  progress: Number,
  target: Number,
  id: Number,
  challengeName: String,
  completed: Boolean,
});

onMounted(() => {
  const progressPercent = (props.progress / props.target) * 100 + '%';
  const elementId = props.id.toString();
  document.getElementById(elementId).style.height = progressPercent;

  if (props.completed === true) {
    document.getElementById(elementId + 'circle').style.boxShadow ="0 0 0 3px #A4CD92";
    document.getElementById(elementId + 'line').style.backgroundColor="#A4CD92";
  }
});

</script>

<template>

  <div class="container">
    <div class="circle_with_line">
      <div class="line" :id="props.id + 'line'"></div>
      <div class="circle" :id="props.id + 'circle'">
        <div class="fill" :id="props.id"></div>
      </div>
    </div>

    <div class="text">{{ props.challengeName }}</div>
  </div>
</template>

<style scoped>

.circle_with_line {
  position: relative;
}

.line {
  position: absolute;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 3px;
  height: 50px;
  background-color: #8B8484;
}


.circle {
  display: flex;
  align-items: center;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  border: 3px solid #EBE9FB;
  text-align: center;
  line-height: 250px;
  overflow: hidden;
  padding: 3px;
  position: relative;
  box-sizing: border-box;
  box-shadow: 0 0 0 3px #8B8484;
  margin: 50px;
}

.container:hover .text{
  text-decoration: underline;
}

.container:hover .circle{
  border: 3px solid black;
}

.container:hover{
  cursor: pointer;
}

.fill {
  background-color: #A4CD92;
  height: 0;
  width: 100%;
  position: absolute;
  bottom: 0;
  left: 0;
}

.text {
  flex: 1;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  margin-left: -30px;
}

.container {
  display: flex;
  align-items: center;
}

</style>