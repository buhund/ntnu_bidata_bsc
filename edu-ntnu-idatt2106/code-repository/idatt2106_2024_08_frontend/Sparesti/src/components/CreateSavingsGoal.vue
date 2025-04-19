<script setup>
/**
 * A Vue component for setting up a savings goal. It includes fields for the goal's name, target amount, and end date.
 * The component uses Vee-Validate for validation and emits updates to the parent on input changes.
 *
 * @component
 * @props
 *  {String} nameValue - Initial value for the goal's name.
 *  {String} targetValue - Initial value for the goal's target amount.
 *  {String} endDateValue - Initial value for the goal's end date.
 *
 * @emits
 *  update:nameValue - Emits updates when the goal's name is changed.
 *  update:targetValue - Emits updates when the target amount is changed.
 *  update:endDateValue - Emits updates when the end date is changed.
 *
 * @function
 *  onNameInput - Updates the nameValue when the input changes.
 *  onTargetInput - Updates the targetValue while ensuring it remains an integer.
 *  onEndDateInput - Validates and updates the endDateValue, ensuring it's not set in the past.
 *  isInteger - Prevents non-integer inputs for the target field.
 *
 * @validation
 *  Uses 'required' and 'integer' validation rules to ensure the target field is valid.
 *
 * @template
 *  Provides form inputs for the name, target amount, and end date of a savings goal with error handling.
 *
 * @style
 *  Scoped styles ensure proper layout and visibility of elements, especially focusing on error messages.
 *
 * @example
 * <template>
 *   <div>
 *     <input type="text" name="nameValue" @input="onNameInput">
 *     <input type="text" name="targetValue" @keydown="isInteger" @input="onTargetInput">
 *     <input type="date" name="endDateValue" @input="onEndDateInput" min="today">
 *   </div>
 * </template>
 */

  import { ref } from "vue";
  import { useField, defineRule } from 'vee-validate';
  import { required, integer } from '@vee-validate/rules';

  const props = defineProps(['nameValue', 'targetValue', 'endDateValue'])
  const emit = defineEmits(['update:nameValue', 'update:targetValue', 'update:endDateValue']);
  const today = new Date().toISOString().split('T')[0]; // Today's date in YYYY-MM-DD (ISO/Euro) format
  const dateError = ref('');

  const onNameInput = (event) => {
    emit('update:nameValue', event.target.value);
  };

  const onTargetInput = (event) => {
    emit('update:targetValue', event.target.value);
  };

  /* Date Picker Widget and Validation */
  const onEndDateInput = (event) => {
    if (new Date(event.target.value) < new Date(today)) {
      dateError.value = 'Du kan ikke velge dato bakover i tid.';
      return;
    }
    dateError.value = '';
    emit('update:endDateValue', event.target.value);
  };

  /* Saving Goal Integer Input Validation and Error Message */

  // Define rules
  defineRule('required', required);
  defineRule('integer', integer);

  const nonIntError = ref('');

  const { value: targetValue, errorMessage: targetError } = useField(
      'targetValue',
      {
        validate: (value) => {
          if (!value) {
            return 'This field is required';
          }
          if (!/^\d+$/.test(value)) {
            return 'The input must be an integer';
          }
        }
      }
  );


  // Validate the input
  const isInteger = (event) => {
    if (!/^\d+$/.test(event.key) && !['Backspace', 'ArrowLeft', 'ArrowRight', 'Delete', 'Tab'].includes(event.key)) {
      event.preventDefault();
      nonIntError.value = 'Kun heltall er tillatt';
    } else {
      nonIntError.value = '';
    }
  };

</script>

<template>
  <div class="flex-container3">
    <div class="box">
      <ul class="question">
        <div class="text">
          <h3>Sett opp et sparemål nå!</h3>
        </div>
        <div>
          <label>Tittel på sparemålet</label>
          <input type="text"
            name="nameValue"
            placeholder="Navn på målet"
            @input="onNameInput">
        </div>
        <div>
          <label>Hvor mye vil du spare?</label>
          <input type="text"
              name="targetValue"
              placeholder="Beløp i kr"
              v-model="targetValue"
              @keydown="isInteger"
              @input="onTargetInput($event)">
            <div class="error-container">
              <span v-if="nonIntError" class="error">{{ nonIntError }}</span>
              <span v-if="targetError" class="error">{{ targetError }}</span>
            </div>
        </div>
        <div>
          <label>Velg en sluttdato for målet</label>
          <div v-if="dateError" class="error" >{{ dateError }}</div>
            <input type="date"
              name="endDateValue"
              :value="endDateValue"
              @input="onEndDateInput"
              :min="today">
            <div v-if="dateError" class="error">{{ dateError }}</div>
        </div>
      </ul>
    </div>
  </div>
</template>

<style scoped>

.flex-container3 {
  display: flex;
  flex-direction: row;
  height: 90%;
  justify-content: center;
  align-items: center;
  width: 100vw;
}

.error {
  color: red;
  text-align: center;
  width: 100%;
}

.error-container {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  margin-right: 20%;
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

label{
  display: flex;
  align-items: center;
  padding: 0.375em 0.75em 0.375em 0.375em;
}

input{
  display: flex;
  align-items: center;
  flex-direction: column;
  margin-bottom: 25px;
  border: 1px solid black;
  border-radius: 20px;
  min-width: 150px;
  min-height: 40px;
  text-align: left;
  font-size: 16px;
  padding: 10px;
}

</style>