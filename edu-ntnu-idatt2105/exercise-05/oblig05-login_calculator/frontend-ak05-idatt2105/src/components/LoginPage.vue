<!-- src/components/LoginPage.vue -->

<script setup lang="ts">
import { ref } from 'vue';
import { computed } from 'vue';
import { useRoute } from 'vue-router';
import LoginForm from "@/components/LoginForm.vue";
import RegistrationForm from "@/components/RegistrationForm.vue";

const isLoginFormVisible = ref(true); // State to toggle between forms
const route = useRoute(); // Use Vue Router's useRoute hook to access the current route
const showRegistrationSuccess = computed(() => route.query.registered === 'true');
const toggleForm = () => {
  isLoginFormVisible.value = !isLoginFormVisible.value;
};

const handleFormSubmission = (contactMessage: string) => {
  console.log(contactMessage);
};
</script>

<template>
  <div>
    <!-- Registration success message -->
    <div v-if="showRegistrationSuccess" class="success-message">
      Registration successful! Please log in with your new credentials.
    </div>

    <div v-if="isLoginFormVisible">
      <h1 class="h1-title">Log in</h1>
      <p>Input log-in details.</p>
      <div class="form-container">
        <LoginForm @message-submitted="handleFormSubmission" />
      </div>
      <button @click="toggleForm">Register New User</button>
    </div>

    <div v-else>
      <h1 class="h1-title">Register</h1>
      <p>Fill in your details to register.</p>
      <div class="form-container">
        <RegistrationForm @message-submitted="handleFormSubmission" />
      </div>
      <button @click="toggleForm">Back to Login</button>
    </div>
  </div>
</template>


<style scoped>

.success-message {
  color: green;
  margin-bottom: 20px;
  text-align: center;
  font-weight: bold;
}

button {
  margin-top: 20px;
  padding: 10px 20px;
  border-radius: 5px;
  border: none;
  background-color: #007bff;
  color: white;
  cursor: pointer;
}

.form-container {
  display: flex;
  flex-direction: column;
  align-items: center; /* Center children horizontally in the flex container */
  width: 100%; /* Use the full width of the parent container */
  max-width: 600px; /* Maximum width of the content, adjust as needed */
  margin: 0 auto; /* Center the .contact div inside its container */
}

.h1-title {
  text-align: center;
  padding-bottom: 12px;
  font-weight: bolder;
}

p {
  font-weight: bold;
}

</style>

