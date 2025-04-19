<script setup>
/**
 * A Vue component for creating and saving a new savings goal. It uses form fields to gather user input for a goal's name, target, and end date.
 * The form validates inputs before submission to ensure data integrity. It uses axios for server requests, handling user authentication via tokens.
 *
 * @component
 * @props
 *  {Boolean} isOpen - Controls the visibility of the modal.
 *
 * @emits
 *  modal-close - Fired to close the modal either after a successful save or when the user cancels the operation.
 *  submit - Not used in current implementation.
 *
 * @vueuse
 *  useField - Used to handle form fields and their validation.
 *  computed - Vue's reactivity system to compute state based on other reactive state or properties.
 *
 * @function
 *  saveGoal - Sends a POST request with the new goal data to a server and handles the response.
 *  isFormValid - Checks if the form data meets the requirements to enable the save button.
 *
 * @template
 *  Provides a modal layout with input fields and buttons to submit or cancel the saving of a new goal.
 *  Uses CreateSavingsGoal for the actual form fields linked via v-model to keep data synchronized.
 *
 * @style
 *  Scoped styles for modal layout, buttons, and other UI elements to ensure proper display and user interaction.
 *
 * @example
 * <template>
 *   <div v-if="props.isOpen" class="modal-mask">
 *     <div class="modal-wrapper">
 *       <CreateSavingsGoal
 *           v-model:nameValue="newGoalName"
 *           v-model:targetValue="newGoalTarget"
 *           v-model:endDateValue="newGoalEndDate"/>
 *       <button @click="saveGoal" :disabled="!isFormValid">Save</button>
 *       <button @click="emit('modal-close')">Cancel</button>
 *     </div>
 *   </div>
 * </template>
 */

import {defineProps, defineEmits, ref, computed} from "vue";
import "../assets/configurationStyle.css";
import CreateSavingsGoal from "@/components/CreateSavingsGoal.vue";
import axios from "axios";
import {ip} from "@/utils/httputils.js";
import {useTokenStore} from "@/stores/token.js";
import {useField} from "vee-validate";

const tokenStore = useTokenStore();

const { value:newGoalName } = useField('newGoalName');
const { value:newGoalTarget } = useField('newGoalTarget');
const { value:newGoalEndDate } = useField('newGoalEndDate');

const props = defineProps({
  isOpen: Boolean,
});

const emit = defineEmits(["modal-close", "submit"]);

const target1 = ref(null)

const saveGoal = () => {
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
      emit('modal-close')
    });
  } catch (err) {
    console.log(err);

  }
}

const isFormValid = computed(() => {
  const today = new Date(new Date().toDateString());
  today.setHours(23, 59, 59, 999);

  return newGoalName.value !== '' && newGoalTarget.value !== '' && (new Date(newGoalEndDate.value) > today);
});

</script>

<template>
  <div v-if="props.isOpen" class="modal-mask" >
    <div class="modal-wrapper">
      <div class="closeX">
        <div id="close" @click="emit('modal-close')">&#10006;</div>
      </div>
      <div class="flex-container3" ref="target1">
        <div class="box">
          <CreateSavingsGoal
              v-model:nameValue="newGoalName"
              v-model:targetValue="newGoalTarget"
              v-model:endDateValue="newGoalEndDate"/>

          <button class="save" @click="saveGoal" :disabled="!isFormValid">Lagre</button>
          <button class="close" @click="emit('modal-close')">Avbryt</button>
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
  overflow-y: auto;
}
.modal-wrapper {
  width: 300px;
  margin: 100px auto;
  padding: 20px 20px;
  background-color: #fff;
  border-radius: 35px;
}
.flex-container3 {
  display: flex;
  flex-direction: column;
  height: 90%;
  justify-content: center;
  align-items: center;
  max-width: 300px;
}

button{
  min-width: 200px;
  margin-top: 20px;
}

#close:hover{
  color: #fb4c4e;
  background-color: white;
  cursor: pointer;
}

.closeX{
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  font-size: 28px;
}

ul{
  justify-content: space-between;
}

.box{
  background-color: white;
  min-height: 30vh;
  border-radius: 30px;
  margin-left: 50px;
  margin-right: 50px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.save {
  background-color: #A4CD92;
  min-width: 150px;
  text-align: center;
  border-radius: 35px;
  min-height: 50px;
  padding: 10px;
  border: none;
  font-size: 15px;
  margin-right: 30px;
  font-weight: bold;
}

.close {
  background-color: #fb4c4e;
  min-width: 150px;
  text-align: center;
  border-radius: 35px;
  min-height: 50px;
  padding: 10px;
  border: none;
  font-size: 15px;
  margin-right: 30px;
  font-weight: bold;
}
</style>