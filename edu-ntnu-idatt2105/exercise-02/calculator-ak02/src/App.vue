<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import Header from "@/components/Header.vue";


</script>

<template >
<!--  <header>
    <img alt="The Prophet professing that every day, we stray further from god."
         class="welcomePic" src="@/assets/stray-from-god.png"/>


    <div class="wrapper">
      <nav>
        <RouterLink to="/">Home</RouterLink>
        <RouterLink to="/about">About</RouterLink>
        <RouterLink to="/SFH">SFH</RouterLink>
        <RouterLink to="/calculator">Calculator</RouterLink>
        <RouterLink to="/contact">Contact</RouterLink>
      </nav>
    </div>
  </header>-->



  <div class="container">
  <main>
    <Header />
  </main>
  </div>


  <RouterView />


</template>

<style scoped>


.container {
  width: 100%;
  max-height: 90vh;
  min-height: 90vh;
  overflow-y: auto;
  padding-top: 22px;
  margin: 0 auto;
  padding-left: 8px;
  padding-right: 8px;
  display: flex;
  background-color: rgba(215, 215, 215, 0.86);
  border: 2px solid #222222;
  align-content: flex-start;
}

header {
  line-height: 1.5;
  max-height: 100vh;
  padding-right: 20px;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

.img-stray {
  display: block;
  margin: 0 auto 2rem;
  border-radius: 8%;
  border: 8px solid darkcyan;
  width: auto;
  max-width: 90%;
  height: auto;

  transition: opacity 0.5s ease-in-out;
  opacity: 1;
}

.img-stray:hover {
  opacity: 0.8; /* Adjust as needed */
}

nav {
  width: 100%;
  font-size: 22px;
  text-align: center;
  margin-top: 2rem;
  padding: 10px;

}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
  transition: color 0.3s;
  font-size: 22px;
  font-weight: bold;
}


nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
    min-width: 400px;
    text-align: left;
    width: 80%;
    margin-left: -1rem;
    font-size: 1rem;
    padding: 1rem 0;
    margin-top: 1rem;
  }

  .img-stray {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: left;
    width: 30%;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
}
</style>
