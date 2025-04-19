import { defineStore } from 'pinia';
import { ref } from 'vue';

export const TokenStore = defineStore('Token', () => {
  const token = ref("");

  function getToken(){
    return token.value;
  }

  function setToken(newResult:string){
    console.log(newResult)
    token.value = newResult;
  }

  return { token, getToken, setToken };
}, {
  // Add the persist option here
  persist: {
    storage: sessionStorage, // data will be stored in sessionStorage
  },
});