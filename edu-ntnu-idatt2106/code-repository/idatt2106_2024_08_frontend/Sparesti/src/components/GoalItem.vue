<script setup>
/**
 * Displays a modal with details about a savings goal, including a progress bar and an optional delete icon.
 * The modal becomes visible when `isOpen` is true. It updates dynamically based on the provided `target` and `progress` values.
 *
 * @component
 * @props
 *  isOpen - Controls whether the modal is visible.
 *  completed - Indicates if the goal has been achieved.
 *  target - The savings target amount.
 *  progress - The current savings amount.
 *
 * @emits
 *  modal-close - Triggered to close the modal.
 *  delete - Triggered to delete the savings goal (only if not completed).
 *
 * @slots
 *  name - Displays the name of the savings goal.
 *  target - Displays the target amount.
 *  date - Displays the target date.
 *  progress - Displays the current savings progress.
 *
 * @usage
 * To use, wrap in a conditional that checks `isOpen` and include necessary slots for content.
 * The modal can be closed via the 'X' button, and the trash icon (if visible) deletes the goal.
 */

import { defineProps, defineEmits, ref } from "vue";
import "../assets/configurationStyle.css";
import trash from "@/assets/trash-icon.svg";
import {onClickOutside} from "@vueuse/core";
const props = defineProps({
  isOpen: Boolean,
  completed: Boolean,
  failed: Boolean,
  target: Number,
  progress: Number
});

const emit = defineEmits(["modal-close", "delete"]);

const target2 = ref(null)

</script>

<template>
  <div v-if="isOpen" class="modal-mask" >
    <div class="modal-wrapper">
      <div class="closeX">
        <div name="close" id="close" @click="emit('modal-close')">&#10006</div>
      </div>
      <div class="flex-container3" ref="target2">
        <div class="box">
          <div class="details">
            <div class="nameAndClose">
              <h3 class="name">
                <slot name="name"></slot>
              </h3>
            </div>
            <div id="info">
              <h4 id="target">
                Sparemål:
              </h4>
                <slot name="target"></slot>
                kr
              <h4 id="date">
                Dato:
              </h4>
                <slot name="date"></slot>
            </div>
            <div id="progressbar">
              <div id="myBar" :style="{ width: (props.progress / props.target) * 100 + '%', 'max-width': props.target + 'px' }"></div>
            </div>
            <h4>
              Totalt spart:
            </h4>
              <slot name="progress"></slot>
          </div>
          <div class="trash">
            <img v-if="!props.completed && !props.failed" :src="trash" alt="Trash Icon" class="image" @click="emit('delete')">
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
}

.modal-wrapper {
  max-width: 40%;
  width: auto;
  overflow-y: auto;
  margin: 150px auto;
  padding: 10px 30px 30px 30px;
  background-color: #fff;
  border-radius: 35px;
  justify-content: center;
  align-items: center;
  border: 1px solid black;
}

#progressbar {
  margin-top: 15px;
  margin-bottom: 10px;
  background-color: white;
  border: 1px solid black;
  border-radius: 13px;
  overflow: hidden;
}

#progressbar > div{
  min-height: 20px;
  overflow: hidden;
  border-radius: 20px;
  background-color: #A4CD92;
  text-align: center;
  line-height: 30px;
  color: white;
}

.image {
  border-radius: 8px;
  max-height: 30px;
  width: auto;
  grid-column-start: 3;
  position: absolute;
  flex: 1;
}

.image:hover {
  background-color: lightgray;
}

#close{
  border: none;
  background-color: white;
}

#close:hover{
  color: #fb4c4e;
  cursor: pointer;
}

.closeX{
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  font-size: 28px;
}

.flex-container3 {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding-bottom: 20px;
}

.box{
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: white;
  min-height: 30vh;
}

h3 {
  font-size: 1.2rem;
  font-weight: 550;
  align-content: center;
  text-align-last: center;
}

h4 {
  font-size: 1.0rem;
  font-weight: bold;
  margin-bottom: 5px;
}
</style>
