<script setup>
/**
 * A component that displays news items fetched from the server.
 * The component includes a list of news items with images, titles, and descriptions.
 * The news items are fetched from the server using an HTTP GET request.
 *
 * @component
 * @function
 * fetchNewsItems - Fetches news items from the server.
 */
import {useTokenStore} from "@/stores/token.js";
import {ip} from "@/utils/httputils.js";
import axios from "axios";
import {ref} from "vue";

const tokenStore = useTokenStore();
const newsItems = ref([]);

(async () => {
  try{
    const response = await axios.get(`https://${ip}:8443/api/news`, tokenStore.getAxiosAuthorizationConfig());

    if (response.status === 200) {
      newsItems.value = response.data;
    }
  } catch (error){
    console.error('Error fetching data:', error);
  }
})();
</script>

<template>
  <div class="newsContainer" v-for="item in newsItems">
    <a :href="item.link" target="_blank"><div class="item">
      <img v-if="item.enclosures[0].type === 'img/jpg'" :src="item.enclosures[0].url" alt="Bilde">
      <div>
        <h3>{{ item.title }}</h3>
        <p>{{ item.description.value }}</p>
      </div>
    </div></a>
  </div>
</template>

<style scoped>
  .item {
    background-color: white;
    width: 60vw;
    height: 20vh;
    border-radius: 35px;
    text-align: left;
    padding: 20px;
    display: flex;
    flex-direction: row;
    align-items: flex-start;
    justify-content: left;
    margin-top: 50px;
    margin-bottom: 10px;
  }

  .item img {
    height: 100%;
    border-radius: 35px;
  }

  .item div {
    padding: 1vw;
  }

  .newsContainer * {
    color: black;
    text-decoration: none;
  }

  .item:hover {
    box-shadow: 0 4px 8px rgba(0,0,0,0.1);
  }

  @media screen and (max-width: 900px) {
    .item {
      width: 80vw;
      height: auto;
      flex-direction: column;
    }
    .item img {
      width: 100%;
      height: auto;
    }
  }
</style>