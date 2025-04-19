<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import HelloWorld from '../components/HelloWorld.vue'
import CalculatorView from '@/views/CalculatorView.vue'
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
  <div id = view>
    <div>
      <header id = LogoSection class = LogoArea>
        <img alt="UFO logo" class="logo" src="@/assets/stray.png" width="150" height="150" />

        <div class="wrapper">
          <HelloWorld msg="UFO Calculator" />

          <nav>
            <RouterLink to="/Calculator">Calculator</RouterLink>
            <RouterLink to="/History">History</RouterLink>
            <RouterLink to="/Bugs">Bug Report</RouterLink>
          </nav>
        </div>
      </header>
    </div>
    <div class="calculator-container">
      <CalculatorView />
    </div>

    <!-- Display other pages next to the home page -->
    <RouterView class="FuncArea" />
  </div>


</template>

<style scoped>

.FuncArea{
  flex: 1;
}

#view{
  display: flex;
  flex-direction: column;
}

#LogoSection{
  background-color: antiquewhite;
  border: 3px solid #000;
  border-right: 1.5px;
  padding: 5px;
  padding-left: 15px;
}

header {
  line-height: 1.5;
  max-height: 100vh;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
}

nav a.router-link-exact-active {
  color: var(--color-inactiveLink);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
}
</style>