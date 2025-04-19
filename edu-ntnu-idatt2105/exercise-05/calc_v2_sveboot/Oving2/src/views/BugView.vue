<script setup lang="ts">
  import BugReport from '../components/BugReport.vue'
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
  <div class = BugForm>
    <main>
      <BugReport />
    </main>
  </div>
</template>

<style scoped>
@media (min-width: 1024px) {
  .BugForm {
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
  }
}
</style>