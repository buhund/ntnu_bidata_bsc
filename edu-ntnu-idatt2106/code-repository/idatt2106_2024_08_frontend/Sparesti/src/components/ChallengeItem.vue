<script setup>
/**
 * A modal component that displays detailed info and a delete option. It shows content slots for name, description, progress, and dates.
 * The modal can be closed by clicking outside its area or by using the close button. If deletable, a trash icon is shown for deletion.
 *
 * @component
 * @props
 *  {Boolean} isOpen - Shows or hides the modal.
 *  {Boolean} deletable - Enables the delete option.
 *
 * @emits
 *  modal-close - Fired when the modal closes.
 *  submit - Fired to submit data (not implemented here).
 *  delete - Fired when the trash icon is clicked.
 *
 * @slots
 *  name, description, progress, target, startDate, endDate - Slots for various pieces of content like names and dates.
 *
 * @vueuse
 *  onClickOutside - Detects clicks outside the modal to close it.
 *
 * @example
 * <Modal :isOpen="true" :deletable="true">
 *   <template v-slot:name>Task Name</template>
 *   <template v-slot:description>Task Description</template>
 *   <template v-slot:progress>50%</template>
 *   <template v-slot:target>100 Items</template>
 *   <template v-slot:startDate>2022-01-01</template>
 *   <template v-slot:endDate>2022-12-31</template>
 * </Modal>
 */
import { defineProps, defineEmits, ref } from "vue";
import {onClickOutside} from '@vueuse/core'
import trash from '@/assets/trash-icon.svg'
import "../assets/configurationStyle.css";

const props = defineProps({
  isOpen: Boolean,
  deletable: Boolean,
});

const emit = defineEmits(["modal-close", "submit", "delete"]);

const target1 = ref(null)

</script>

<template>
  <div v-if="isOpen" class="modal-mask" >
    <div class="modal-wrapper">
      <div class="closeX">
        <div name="close" id="closePopup" @click="emit('modal-close')">&#10006</div>
      </div>
			<div class="flex-container3">
        <div class="details" ref="target1">
          <div id="heading">
            <h3>
              <slot name="name"></slot>
            </h3>
          </div>
          <div id="info">
            <h4>Beskrivelse: </h4>
              <slot name="description"></slot>
            <h4>Progresjon:</h4>
              <slot name="progress"></slot>
              av
              <slot name="target" ></slot>
            <h4>Dato:</h4>
              <slot name="startDate"></slot>
              -
              <slot name="endDate"></slot>
          </div>
          <div class="trash">
            <img v-if="deletable" :src="trash" alt="Trash Icon" class="image" @click="emit('delete')">
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
  margin: 150px auto;
  padding: 20px 30px;
  background-color: #fff;
  border-radius: 35px;
  justify-content: center;
  align-items: center;
  border: 1px solid black;
}

.flex-container3 {
  display: flex;
  justify-content: center;
  border-radius: 35px;
  padding: 20px 1px 20px 10px;
  margin: auto;
  max-width: 600px;
  width: auto;
  background-color: white;
}

.image {
  border-radius: 8px;
  height: 35px;
  width: auto;
  margin-top: 30px;
}

.image:hover {
  background-color: lightgray;
}


h3 {
  font-size: 1.2rem;
  font-weight: 550;
  align-content: center;
  text-align-last: center;
  margin-top: 0;
}

  h4 {
    font-size: 1.0rem;
    font-weight: bold;
    margin-bottom: 5px;
  }

#closePopup{
  border: none;
  background-color: white;
}

#closePopup:hover{
  color: #fb4c4e;
  background-color: white;
  cursor: pointer;
}

.details{
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: white;
  min-height: 30vh;
  border-radius: 30px;
}

.closeX{
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  font-size: 28px;
}

  @media screen and (max-height: 400px) {
    #delete {
      position: absolute;
      top: 12px;
      right: -10px;
    }
  }

</style>
