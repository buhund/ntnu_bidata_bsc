<script setup lang="ts">
import ContactForm from "@/components/ContactForm.vue";

const handleFormSubmission = (contactMessage) => {
  console.log(contactMessage);
};

</script>

<template>
  <div>
    <div>
      <div>
        <h1 class="h1-title">Contact</h1>
        <p>
          from outer space.
        </p>

      </div>
      <div class="form-container">
        <ContactForm @message-submitted="handleFormSubmission" />
      </div>
    </div>
  </div>
</template>

<style scoped>


.contact {
  display: flex;
  flex-direction: column;
  width: 100%;
}

.contact-container {
  display: flex;
  flex-direction: column;
  align-items: center; /* Center children horizontally in the flex container */
  width: 100%; /* Use the full width of the parent container */
  max-width: 600px; /* Maximum width of the content, adjust as needed */
  margin: 0 auto; /* Center the .contact div inside its container */
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

