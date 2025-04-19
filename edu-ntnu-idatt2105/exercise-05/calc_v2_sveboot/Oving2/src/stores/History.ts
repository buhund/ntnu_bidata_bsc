import { defineStore } from 'pinia';
import { reactive } from 'vue'

export const HistoryStore = defineStore('history', () => {

  const log: string[] = reactive([]);

  function getLog(){
    return log;
  }

  function addToLog(expression:string){
    console.log(expression)
    log.push(expression);
  }

  return {log, getLog, addToLog}

});