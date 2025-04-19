import { defineStore } from 'pinia';
import { ref } from 'vue'



export const PrevAnsStore = defineStore('History', () => {
  
  const result = ref("");
  
  function getResult(){
    return result.value;
  }
  
  function setResult(newResult:string){
    console.log(newResult)
    result.value = newResult;
  }

  return {result, getResult, setResult}

});