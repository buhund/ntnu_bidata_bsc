import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useGoalStore = defineStore('goal', () => {
  const goal = ref(null)

  function setGoal(newGoal) {
    console.log(newGoal)
    goal.value = newGoal

  }
  
 

  return { goal, setGoal, }
},
)
