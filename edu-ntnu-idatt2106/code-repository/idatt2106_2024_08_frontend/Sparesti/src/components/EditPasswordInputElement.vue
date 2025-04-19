<script setup>
/**
 * A Vue component for a password input field with validation. It accepts a label, a model value, and an error message.
 * The component emits updates to the parent when the input value changes.
 *
 * @component
 * @props
 *  {String} label - Text label for the input field, defaults to an empty string.
 *  {String|Number} modelValue - The current value of the input field, can be a string or number, defaults to an empty string.
 *  {String} error - Error message to display when validation fails.
 *
 * @emits
 *  update:modelValue - Emits the current value of the input field to the parent component whenever it changes.
 *
 * @function
 *  onInput - Handles input events, updates the model value via an emitted event.
 *
 * @template
 *  Renders a label, a password input field that reacts to validation, and displays error messages if any.
 *
 * @style
 *  Scoped styles handle the layout and visual feedback of the input field, including error indications.
 *
 * @example
 * <template>
 *   <div>
 *     <label>{{ label }}</label>
 *     <input type="password" :value="modelValue" @input="onInput">
 *     <span v-if="error">{{ error }}</span>
 *   </div>
 * </template>
 */

defineProps({
  label: {
    type: String,
    default: ''
  },
  modelValue: {
    type: [String, Number],
    default: ''
  },
  error: String
})
const emit = defineEmits(['update:modelValue']);

const onInput = (event) => {
  emit('update:modelValue', event.target.value);
};

</script>
<template>

  <div class="element">
    <label class="heading">{{ label }}</label>
    <input
        class="inputField"
        :class="{ 'is-error': error }"
        :value="modelValue"
        @input="onInput"
        name="inputField"
        minlength="2"
        maxlength="30"
        type="password"
    >
    <span v-if="error" class="error">{{ error }}</span>
  </div>
</template>

<style scoped>
.element{
  justify-content: center;
  display: flex;
  flex-direction: column;
  padding: 5px;
}

.inputField {
  width:100px;
  height: 30px;
  border-style: none;
  border-color: white;
  border-radius: 7px;
}
.inputField.is-error {
  border-color: red;
}

.error {
  color: red;
  margin-top: 5px;
}


</style>
