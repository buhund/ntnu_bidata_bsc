<script setup lang="ts">
import Shop from "@/components/Shop.vue";
</script>

<template>
  <main>
    <Shop />
  </main>
</template>

<style scoped>

  :host {
    width: 98vh;
    height: 100%;
    padding-top: 22px;
    margin: 0 auto;
    display: flex;
    background-image: url("/src/assets/background-05.png");
    background-repeat: repeat;
    background-size: cover;
    background-position: center;
    border: 1px solid #222222;
  }
</style>