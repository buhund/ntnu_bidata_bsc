<script setup lang="ts">
import History from '../components/CalculatorHistory.vue'
import router from '@/router'
import {TokenStore} from '@/stores/Token'

const checkToken = () =>{
  if(TokenStore().getToken() == ""){
    router.push("/")
  }
}
checkToken()
</script>

<template>
  <div class="History">
    <main>
      <History />
    </main>
  </div>
</template>

<style>
@media (min-width: 1024px) {
  .History {
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
  }
}
</style>
