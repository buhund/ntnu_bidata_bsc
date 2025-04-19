<script setup>
/**
 * This Vue component displays a modal with a personalized savings challenge. It includes interactive elements
 * to accept or decline the challenge. The modal appears based on the `isOpen` prop and can be closed by clicking outside
 * its bounds or by pressing the close button. It supports custom content via slots for the challenge's name, description, goal, and end date.
 *
 * @component
 * @props
 *  isOpen - Boolean that controls the visibility of the modal.
 *
 * @emits
 *  modal-close - Emitted to close the modal without accepting the challenge.
 *  submit - Emitted when the challenge is accepted.
 *
 * @slots
 *  name - Slot for the name of the challenge.
 *  description - Slot for the description of the challenge.
 *  target - Slot for the goal of the challenge.
 *  endDate - Slot for the end date of the challenge.
 *
 * @usage
 * To use, provide the content for the slots and control the visibility with `isOpen`. Handle the emitted
 * events to manage the results of user actions (accept or decline).
 *
 * @example
 * <template>
 *   <Modal :isOpen="true">
 *     <template #name>Save $500</template>
 *     <template #description>Save $500 by the end of the year for an emergency fund.</template>
 *     <template #target>$500</template>
 *     <template #endDate>2023-12-31</template>
 *   </Modal>
 * </template>
 */
import { defineProps, defineEmits, ref } from "vue";
import {onClickOutside} from '@vueuse/core'
import "../assets/configurationStyle.css";

const props = defineProps({
  isOpen: Boolean,
});

const emit = defineEmits(["modal-close", "submit"]);

const target1 = ref(null)
onClickOutside(target1, ()=>emit('modal-close'))

</script>

<template>
  <div v-if="isOpen" class="modal-mask" >
			<div class="modal-wrapper" ref="target1">
				<div class="box">
					<ul class="question">
						<div class="text">
							<h3>Under ser du en spareutfordring generert kun for deg</h3>
						</div>
						<div id="body">
              <h4>Spareutfordringen din: </h4>
                <slot name="name"></slot>
              <h4>Beskrivelse: </h4>
                <slot name="description"></slot>
              <h4>Mål:</h4>
                <slot name="target"></slot>
              <h4>Sluttdato:</h4>
                <slot name="endDate"></slot>
						</div>
						<div class="godtaAvslaa">
							<div>
								<button class ="godta" @click.sumbmit="emit('submit')">Godta</button>
							</div>
							<div>
								<button class="avslaa" @click.stop="emit('modal-close')">Avslå</button>
							</div>
						</div>
					</ul>
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
  max-width: 70%;
  width: auto;
  overflow-y: auto;
  margin: 150px auto;
  padding: 0px ;
  justify-content: center;
  align-items: center;
}

.flex-container3 {
  display: flex;
  flex-direction: column;
  height: 90%;
  justify-content: center;
  align-items: center;
  padding-top: 20px;
  width: 100vw;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);

}

button{
  min-width: 200px;
  margin-top: 20px;
  margin-bottom: 10px;
}

.avslaa{
  background-color: #fb7782;
  min-width: 150px;
  text-align: center;
  border-radius: 35px;
  min-height: 50px;
  padding: 10px;
  border: none;
  font-size: 15px;
  margin-right: 30px;
}

.question{
  display: flex;
  flex-direction: column;
}

.godtaAvslaa{
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: flex-end;
}
#body {
  min-height: 300px;
}

ul{
  justify-content: space-between;
  padding-top: 10px;
}

.box{
  background-color: white;
  min-height: 30vh;
  border-radius: 30px;

}

h3 {
  padding-right: 30px;
}

.avslaa:hover{
  background-color: #fb4c4e !important;
}
.godta{
    background-color: #A4CD92;
    min-width: 150px;
    text-align: center;
    border-radius: 35px;
    min-height: 50px;
    padding: 10px;
    border: none;
    font-size: 15px;
    margin-right: 30px;
}
</style>