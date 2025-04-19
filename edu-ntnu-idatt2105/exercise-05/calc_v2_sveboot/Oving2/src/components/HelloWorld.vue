<script setup lang="ts">
defineProps<{
  msg: string
}>()
</script>

<template>
  <div class="greetings">
    <h1 class="green">{{ msg }}</h1>
    <h3>
      Welcome to the Advanced UFO calculator! Use the buttons bellow to switch the view between
      calculator and calculator history. Encountered a bug? Use the Bug Report tab!
    </h3>
  </div>
</template>

<style scoped>
h1 {
  font-weight: 500;
  font-size: 2.6rem;
  position: relative;
  top: -10px;
  color: var(--color-heading);
}

h3 {
  font-size: 1.2rem;
}

@media (min-width: 1024px) {
}
</style>
