<script setup lang="ts">

import LogIn from '../components/LogIn.vue'
</script>

<template>
  <div class="LoginForm">
    <main>
      <LogIn />
    </main>
  </div>
</template>

<style scoped>
@media (min-width: 1024px) {
  .LoginForm {
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
  }
}
</style>