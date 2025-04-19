<script setup lang="ts">

import {RouterLink} from "vue-router";
</script>


<template>
  <div class="wrapper">
  </div>
  <img src="/src/assets/stray-from-god.png"
       class="header-image"
       alt="Cross">

  <br>
</template>

<style scoped>


.header-image:hover {
  filter: drop-shadow(0 0 2em #ffaf07);
}

.header-image {
  display: block;
  margin: 0 auto;
  width: auto;
  height: 40vh;
  border-radius: 8%;
  border: 8px solid goldenrod;
}

.wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  font-size: 16px;
}

</style>