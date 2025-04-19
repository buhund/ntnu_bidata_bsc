<script setup lang="ts">
import Calculator from '../components/CalculatorMain.vue'
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
  <div class = calcArea>
    <main>
      <Calculator />
    </main>
  </div>

</template>

<style scoped>
root{
  min-width: 1024px;
}
@media (min-width: 1024px) {
  .calcArea {
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
  }
}
</style>







