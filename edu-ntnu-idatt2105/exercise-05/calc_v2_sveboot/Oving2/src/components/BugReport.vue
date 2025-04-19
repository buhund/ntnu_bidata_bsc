<script setup lang="ts">

import axios from 'axios'
import { ref } from 'vue'
import { FormStore } from '@/stores/Form'

// Use a ref to store the form data
const formData = ref({
  name: FormStore().getName(),
  email: FormStore().getEmail(),
  description: ''
})

async function sendForm(){
  FormStore().setName(formData.value.name)
  FormStore().setEmail(formData.value.email)
    try {
      const response = await axios.post('https://my-json-server.typicode.com/SverreFH/MockServer/Bugs',formData.value)
      console.log("Form submitted with data: ", response.data)
      showAlert('Form submitted successfully!')
    }
    catch (error){
      console.log(error)
      showAlert('Form submission error')
    }
  }

function showAlert(alert:string){
  window.alert(alert);
  }

const validateName = () => {
  return formData.value.name.trim() !== '';
}

function validateEmail (){
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(formData.value.email);
}

function validateDescription (){
  return formData.value.description.trim() !== '';
}

function isFormValid (){
  return validateName() && validateEmail() && validateDescription();
}


const fieldTouched = ref({
  name: false,
  email: false,
  description: false,
});


</script>

<template>
  <div class = form>
    <form @submit.prevent="sendForm">
      <h1 id = BugTitle>Bug Report Form</h1>
      <div class = block>
        <label for = "name">Name:</label>
        <input
          type="text"
          id="name"
          data-testid="name"
          v-model="formData.name"
          @blur="fieldTouched.name = true"
          :class="{ 'error': fieldTouched.name && !validateName(), 'success': fieldTouched.name && validateName()}"
          autocomplete="name"
        />
        <span data-testid = "NameError"  v-if=" fieldTouched.name && !validateName()" class="error-message">Please enter a valid name</span>
      </div>
      <div class = block>
        <label for="email">Email:</label>
        <input
          type="email"
          id="email"
          data-testid="email"
          v-model="formData.email"
          @blur="fieldTouched.email = true"
          :class="{ 'error': fieldTouched.email && !validateEmail(), 'success': fieldTouched.email && validateEmail() }"
          autocomplete="email"
        />
        <span data-testid="EmailError" v-if="fieldTouched.email && !validateEmail()" class="error-message">Please enter a valid email address</span>
      </div>
      <div class = block>
        <label for = "description">What is wrong?</label>
        <textarea
          id="description"
          data-testid="description"
          v-model="formData.description"
          @blur="fieldTouched.description = true"
          :class="{ 'error':fieldTouched.description && !validateDescription(), 'success': fieldTouched.description && validateDescription()}"
        ></textarea>
        <span data-testid="DescriptionError" v-if="fieldTouched.description && !validateDescription()" class="error-message">Description is required</span>
      </div>
      <div id = SubmitBtn>
        <button
        type = "submit"
        data-testid="submit"
        :disabled = "!isFormValid()"
        :class="{ 'Submit': isFormValid(), 'InactiveSubmit': !isFormValid() }"
        >Submit</button>
      </div>
    </form>
  </div>
</template>

<style scoped>
  .form{
    display: flex;
    padding: 20px;
    background-color: #f8f8f8;
    border: 2px solid #000;
    border-radius: 5px;
    min-width: 50vh;
    min-height: 60vh;
    justify-content: center;
    align-items: center;
  }

  .Entry{
    min-width: 35vh;
    min-height: 4vh;
  }

  #name{
    min-width: 35vh;
    min-height: 4vh;
  }

  #email{
    min-width: 35vh;
    min-height: 4vh;
  }

  #BugTitle{
    color: var(--color-heading);
    font-weight: 500;
    font-size: 25px;
    display: flex;
  }

  #SubmitBtn{
    display: flex;
    justify-content: center;
    padding: 20px;
  }

  .Submit{
    background-color: var(--color-heading);
    color: white;
    padding: 20px;
    font-size: 20px;
    font-weight: 500;
    min-width: 20vh;
    min-height: 5vh;
    border: 2px solid white;
  }

  .InactiveSubmit{
    background-color: lightgray;
    color: white;
    padding: 20px;
    font-size: 20px;
    font-weight: 500;
    min-width: 20vh;
    min-height: 5vh;
    border: 2px solid white;
    filter: saturate(35%);
  }

  #description {
    min-height: 20vh;
    min-width: 35vh;
    max-width: 45vh;
    max-height: 30vh;
  }

  .block{
    display: grid;
  }

  .error{
    border: 1px solid red;
  }

  .error-message{
    color: red;
  }

  .success{
    border: 1px solid green;
  }
</style>