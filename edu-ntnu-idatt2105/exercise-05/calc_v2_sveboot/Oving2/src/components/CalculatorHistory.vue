<script setup lang="ts">

  //import { HistoryStore } from '@/stores/History'
  import axios from 'axios'
  import { ref } from 'vue'
  import { TokenStore } from '@/stores/Token'

  //const historyStore = HistoryStore();
  //let history = historyStore.getLog()
  let history = ref([])
  const name = "Test";

  const getUserHistory = async () => {
    const config = {
      headers:{
        'Content-Type': 'Application/json',
        'Authorization': 'Bearer ' + TokenStore().getToken()
      }
    }

    console.log("Testing")
    const response = await axios.post("http://localhost:8080/user/GetLogg", {name}, config)
    history.value = response.data;
    console.log(history);

  }

  getUserHistory();

</script>

<template>
  <div id = logTitle>
    <h3>History</h3>
  </div>
  <div id = log>
    <ul id = list v-for="item in history.slice().reverse()" :key = "item">
      <li>{{item}}</li>
    </ul>
  </div>

</template>

<style scoped>

#log{
  background: #f8f8f8 url("src/assets/ufo.webp");
  min-width: 60vh;
  min-height: 70vh;
  border: 2px solid #000;
  font-size: 20px;
}

#logTitle{
  display: flex;
  justify-content: center;
  align-content: center;
  color: var(--color-heading);
  font-weight: 500;
  font-size: 40px;
}

#list{
  color: var(--color-heading);
}

</style>