<script setup>
import { ref, watch } from 'vue';
import axios from 'axios';
import { useFormStore } from '@/stores/formStore';
import { onMounted } from 'vue';

const store = useFormStore();
const submissionMessage = ref('');
const nameError = ref("");
const emailError = ref("");
const messageError = ref("");
const terms = ref(true); // Assuming terms are pre-accepted and not managed by the store

// Email validation regex
const emailRegex = new RegExp(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[?)[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}]?|(([\w-]+\.)+[a-zA-Z]{2,}))$/);

// Load and save form data from/to localStorage
onMounted(() => {
  store.loadForm();
});

// Watch store properties to save form data changes
watch(() => [store.name, store.email, store.message], () => {
  store.saveForm();
}, { deep: true });

function validateForm() {
  let isValid = true;
  nameError.value = '';
  emailError.value = '';
  messageError.value = '';
  submissionMessage.value = '';


  if (store.name.length < 2) {
    nameError.value = 'Name must be at least 2 characters long.';
    isValid = false;
  }
  if (!emailRegex.test(store.email)) {
    emailError.value = 'Email must be valid.';
    isValid = false;
  }
  if (store.message.length < 10) {
    messageError.value = 'Message must be at least 10 characters long.';
    isValid = false;
  }

  return isValid;
}

async function submitForm() {
  if (!validateForm()) {
    return;
  }

  try {
    await axios.post('http://localhost:3000/submissions', {
      name: store.name,
      email: store.email,
      message: store.message,
    });
    submissionMessage.value = 'Message submitted successfully!'; // Confirm this line exists
    store.clearForm();
  } catch (error) {
    submissionMessage.value = 'Failed to submit message.';
  }
}
</script>



<template>
  <form class="contact-form" @submit.prevent="submitForm" novalidate>
    <label>Name:</label>
    <input v-model="store.name" type="text" data-testid="nameField" required>
    <div v-if="nameError" class="error-message" data-testid="nameErrorLabel">{{ nameError }}</div>

    <label>Email:</label>
    <input v-model="store.email" type="email" data-testid="emailField" required>
    <div v-if="emailError" class="error-message" data-testid="emailErrorLabel">{{ emailError }}</div>

    <label>Message:</label>
    <textarea v-model="store.message" data-testid="messageField" required></textarea>
    <div v-if="messageError" class="error-message" data-testid="messageErrorLabel" >{{ messageError }}</div>

    <div class="terms">
      <input type="checkbox" v-model="terms" disabled>
      <label>Accept terms and conditions</label>
    </div>

    <div class="submit">
      <button type="submit" class="button" data-testid="submitButton">Submit</button>
      <div v-if="submissionMessage" class="submission-message" data-testid="submitMessage">{{ submissionMessage }}</div>
    </div>
  </form>
</template>

<style scoped>

.input-invalid {
  border: 2px solid red;
}

.error-message {
  color: red;
  margin-top: 4px;
  font-size: 0.8em;
  font-weight: bold;
}

input[type="checkbox"] {
  display: inline-block;
  width: 16px;
  margin: 0 10px 0 0;
  position: relative;
  top: 2px;
}


.contact-form {
  width: 100%;
  max-height: 90vh;
  min-height: 30vh;
  max-width: 30vw;
  min-width: 20vw;
  border-radius: 10px;

  overflow-y: auto;

  padding: 40px;
  margin: 30px auto;

  display: flex;
  flex-direction: column;
  align-content: flex-start;

  background-color: rgba(215, 215, 215, 0.86);
  border: 2px solid #222222;

  -webkit-box-shadow: 0px 2px 15px -12px rgba(0, 0, 0, 0.57);
  -moz-box-shadow: 0px 2px 15px -12px rgba(0, 0, 0, 0.57);
  box-shadow: 2px 15px 12px rgba(0, 0, 0, 0.57);

}

.contact-form input,
.contact-form textarea {
  max-width: 100%;
  resize: vertical;
}

.button {
  border: 0;
  padding: 10px 20px;
  margin-top: 20px;
  color: white;
  border-radius: 20px;
  background-color: rgba(50,176,214,0.68);
}

.submit {
  text-align: center;
}

/*
.button:not(:disabled):hover {
  background-color: rgba(214,161,0,0.68);
}

.button:not(:disabled):active {
  background-color: rgba(176, 132, 0, 0.68);
}
*/

</style>