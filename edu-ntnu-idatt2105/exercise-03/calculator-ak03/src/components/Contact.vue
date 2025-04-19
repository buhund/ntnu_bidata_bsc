<script setup lang="ts">
import ContactForm from "@/components/ContactForm.vue";

const handleFormSubmission = (contactMessage) => {
  console.log(contactMessage);
};

</script>

<template>
  <div class="contact-container">
    <div class="contact">

      <div>
        <h1 class="h1-title">Contact the Apostoli Sveratus</h1>
        <br>
        <br>

        <p>
          Please use this modest Scematus to communicate thine thoughts to the Holy Above. Terms and conditions apply.
        </p>

      </div>
      <div>
        <ContactForm @message-submitted="handleFormSubmission" />
      </div>

    </div>
  </div>
</template>

<style scoped>


.contact-container {
  width: 100%;
  max-height: 90vh;
  overflow-y: auto;
  padding-top: 22px;
  margin: 0 auto;
  padding-left: 8px;
  padding-right: 8px;
  display: flex;
  background-color: rgba(215, 215, 215, 0.86);
  border: 2px solid #222222;
  align-content: flex-start;
  flex-direction: column;
}

.contact {
  display: flex;
  flex-direction: column;
  width: 100%;
}

.h1-title {
  text-align: center;
  padding-bottom: 12px;
  font-weight: bolder;
  color: black;
}

p {
  font-weight: bold;
  color: black;
}

@media (min-width: 1024px) {
  .contact {
    min-height: 80vh;
    display: flex;
    align-items: flex-start;
  }
}
</style>

