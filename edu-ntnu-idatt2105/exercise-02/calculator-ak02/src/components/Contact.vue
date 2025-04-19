<script setup lang="ts">
import ContactForm from "@/components/ContactForm.vue";

const handleFormSubmission = (contactMessage) => {
  console.log(contactMessage);
};

</script>

<template>
  <div class="contact-container">
    <div class="contact">
        <h1 class="h1-title">Contact the Apostles of Sveratus</h1>
        <br>
        <br>

        <p>
          Wishest thou to Ascend into the Apostoli Sveratus Rexgloriosus, for Holy Communion with the Lord and Savior
          Sveratus Sancti?
          Or havest thou some other urgent matter of import which requires the attention of the Elders?
          <br>
          <br>
          Send in thine tidings via Scematis Contacus:
        </p>

        <br>
        <ContactForm @message-submitted="handleFormSubmission" />
        <br>

      <div>
        <p>By submitting this form, you accept the Contract, as given by the Pontifex Maximus Primus Sveratus
          Apostoli.
          The Contract may only be looked upon by Consilium Augures Seniorum Sacerdotum.
        </p>
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
}

.h1-title {
  text-align: center;
  padding-bottom: 12px;
  font-weight: bolder;
}

p {
  font-weight: bold;
}

@media (min-width: 1024px) {
  .contact {
    min-height: 80vh;
    display: flex;
    align-items: flex-start;
  }
}
</style>

