<script setup lang="ts">
import { ref, defineEmits } from 'vue';


const name = ref('');
const email = ref('');
const message = ref('');

const emit = defineEmits(["message-submitted"]);

const onSubmit = () => {
  if (name.value === "" || email.value === "" || message.value === "") {
    alert("Please fill out every field");
    return
  }

  const contactMessage = {
    name: name.value,
    email: email.value,
    message: message.value,
  };

  emit("message-submitted", contactMessage);

  // Reset form field
  name.value = "";
  email.value = "";
  message.value = "";
}
</script>


<template>
  <form class="contact-form" @submit.prevent="onSubmit">
    <h3>Formi Contactus</h3>

    <label for="name">Name:</label>
    <input id="name" v-model="name" placeholder="Your Name">

    <label for="email">E-mail:</label>
    <input id="email" type="email" v-model="email" placeholder="Your E-mail">

    <label for="message">Message:</label>
    <textarea id="message" v-model="message" placeholder="Write your message here"></textarea>

    <input class="button" type="submit" value="Submit">
  </form>
</template>

<style scoped>

</style>