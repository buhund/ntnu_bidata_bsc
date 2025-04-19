<script setup lang="ts">
  import axios from 'axios'
  import { useRouter } from 'vue-router';
  import { ref } from 'vue'
  import { TokenStore } from '@/stores/Token'



  const router = useRouter();
  const errorMessage = ref('');

  const handleLogin = async () =>{
    const userName = document.getElementById('Name').value;
    const password = document.getElementById('Password').value;

    console.log(userName);
    console.log(password);

    const response = await axios.post("http://localhost:8080/auth/logIn", { "userName": userName, "password": password })

    let answer = response.data;

    if(answer != "failed"){
      TokenStore().setToken(answer);
      router.push('/Home');
    } else {
      errorMessage.value = 'Login failed. Please check your credentials.';
    }


  }

</script>

<template>
  <div id = LogInArea>
    <h1>LogIn</h1>
    <div>
      <h2>Name</h2>
      <input id = "Name" type = "text">
    </div>
    <div>
      <h2>Password</h2>
      <input id = "Password" type = "password">
    </div>
    <button id = logInButton @click="handleLogin()">LogIn</button>
    <p style="color: red;">{{ errorMessage }}</p>
  </div>
</template>

<style scoped>
  #logInButton{
    display: flex;
    justify-content: center;
    padding: 20px;
    background-color: var(--color-heading);
    color: white;
    font-size: 20px;
    font-weight: 500;
    min-width: 20vh;
    min-height: 5vh;
    border: 2px solid white;
  }

  #LogInArea{
      display: grid;
      padding: 5px;
      background-color: antiquewhite;
      border: 2px solid #000;
      border-radius: 5px;
      min-width: 50vh;
      min-height: 60vh;
      justify-content: center;
      align-items: center;
  }

  h1{
    color: var(--color-heading);
  }

</style>