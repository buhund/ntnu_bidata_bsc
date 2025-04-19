<script setup>
/**
 * A button that navigates back to the previous page when clicked.
 * The button is styled as an image with a hover effect.
 *
 * @component
 * @props
 * page - The path to the page to navigate back to.
 *
 * @function
 * goBackToPage - Navigates back to the specified page.
 *
 * @template
 * Includes an image element inside a button element.
 *
 * @style
 * Scoped styles control the layout and visual appearance of the button and image.
 */
import router from "@/router/index.js";

const props = defineProps({
  page: {
    type: String,
    required: true
  }
});

function goBackToPage(page) {
  router.push(page)
}

</script>

<template>
  <button @click="goBackToPage(page)" class="button">
    <img src='../assets/go_back_logo.png' alt="gå tilbake" class="img">
  </button>

</template>

<style scoped>
.button {
  border: none;
  background: none;
  cursor: pointer;
  padding: 0;
}

.img {
  width: 50px;
  height: auto;
}
</style>