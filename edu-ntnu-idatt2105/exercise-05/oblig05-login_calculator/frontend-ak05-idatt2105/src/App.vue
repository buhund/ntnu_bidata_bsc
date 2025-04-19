<!-- src/App.vue -->
<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import { useTheme } from '@/composables/useTheme';
import { useAuth } from '@/composables/useAuth';

const { toggleTheme } = useTheme();
const { isLoggedIn, username, logout } = useAuth();

const performLogout = () => {
  logout();
}

</script>

<template>


  <div class="wrapper">

    <nav>
      <RouterLink to="/">Home</RouterLink>
      <RouterLink to="/calculator">Calculator</RouterLink>
      <RouterLink to="/contact">Contact</RouterLink>
      <RouterLink v-if="!isLoggedIn" to="/login" class="login">Log In</RouterLink>
      <div v-if="isLoggedIn" class="user-info">Welcome,
        <span>{{ username }}</span>
        <button class="logout-button" @click="performLogout">Log out</button>
      </div>
    </nav>
  </div>


  <div id="app">
    <div></div>
    <div><RouterView /></div>
    <div></div>
  </div>-


<!--  <div id="app">
    <button @click="toggleTheme" class="theme-toggle">Toggle Theme</button>
  </div>-->



</template>

<style scoped>
.user-info {
  display: flex-end;
  margin-left: auto;
}

.logout-button {
    border: 0;
    padding: 10px 20px;
    margin-left: 14px;
    color: white;
    border-radius: 20px;
    background-color: rgba(50,176,214,0.68);
}


.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}

.logo:hover, .login:hover{
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}



.wrapper {
  display: flex;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  z-index: 1000;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  margin-bottom: 120px;
  height: 10vh;
}

body {
  padding-top: 70px; /* Adjust based on the actual height of your navigation bar */
}

.router-view-container {
  /* Center the container horizontally */
  min-height: 88vh; /* Minimum height to take up at least the viewport height, optional */
  padding: 20px; /* Adds some spacing inside the container */
  margin: 140px auto 0;
  width: 60vw;
  min-width:400px;
  box-sizing: border-box; /* Ensures padding doesn't add to the set width */
}

nav {
  width: 100%;
  font-size: 22px;
  text-align: center;
  margin-top: 2rem;
  padding: 10px;
  background-color: rgba(219, 112, 147, 0.8);
}

nav a.router-link-exact-active {
  filter: drop-shadow(0 0 2em #42b883aa);
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 10px;
  border-left: 1px solid var(--color-border);
  transition: color 0.3s;
  font-size: 22px;
  font-weight: bold;
}


nav a:first-of-type {
  border: 0;
}




</style>
