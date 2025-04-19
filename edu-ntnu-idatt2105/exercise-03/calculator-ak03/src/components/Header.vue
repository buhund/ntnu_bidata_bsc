<script setup lang="ts">
import {RouterLink} from "vue-router";
</script>

<template>
  <h1 class="h1-title">Apostoli Sveratus Rexgloriosus</h1>
  <h2 class="h2-title">Thine Lord Welcometh Thee</h2>
  <br>
  <img src="/src/assets/sveratus-crusifixus-02.png"
       class="img-home"
       alt="Cross">

  <div class="wrapper">
    <nav>
      <RouterLink to="/">Home</RouterLink>
      <RouterLink to="/sveratus">SFH</RouterLink>
      <RouterLink to="/about">About</RouterLink>
      <RouterLink to="/calculator">Calculator</RouterLink>
      <RouterLink to="/contact">Contact</RouterLink>
      <RouterLink to="/shop">Reliquary</RouterLink>
    </nav>
  </div>

  <div>
    <p class="p-center">
      Thou art invited to use the Abacus Modernicus for thine calculatory needs.
      <br>
      Yet take heed, as not to Assume Knowledge of the Infinite, such that thine Flesh needest be Cleansed of Sin.
    </p>
  </div>


</template>

<style scoped>

.img-stray {
  display: block;
  margin: 0 auto 2rem;
  border-radius: 8%;
  border: 8px solid darkcyan;
  width: auto;
  max-width: 80%;
  height: auto;
  transition: opacity 0.5s ease-in-out;
  opacity: 1;
}

.img-home {
  display: block;
  margin: 0 auto;
  width: 460px;
  height: auto;
  border-radius: 50%;
  border: 8px solid goldenrod;
}

.h1-title {
  font-size: 42px;
  text-align: center;
  padding-bottom: 12px;
  font-weight: bolder;
  color: black;
}

.h2-title {
  text-align: center;
  font-weight: bold;
  color: black;
}
.p-center {
  text-align: center;
  color: black;
}
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
  background-color: rgba(255, 235, 205, 0.86);
  border: 2px solid #222222;
  align-content: flex-start;
}

.wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  font-size: 16px;
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
  padding: 0 10px;
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

    text-align: left;
    width: 80%;
    margin-left: -1rem;
    font-size: 1rem;
    padding: 1rem 0;
    margin-top: 1rem;
  }

  .img-stray {
    margin: 0 auto;
    display: block;
    width: 500px;
    height: auto;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  /*
  nav {
    text-align: left;
    width: 30%;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
  */

}
</style>