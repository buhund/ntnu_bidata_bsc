import { defineStore } from 'pinia';
import { ref } from 'vue'

export const FormStore = defineStore('form', () => {

  const Name = ref("");
  const email = ref("")

  function getName(){
    return Name.value
  }

  function getEmail(){
    return email.value
  }

  function setName(newName:string){
    console.log(newName)
    Name.value = newName;
  }

  function setEmail(newEmail:string){
    console.log(newEmail)
    email.value = newEmail
  }

  return {getName, getEmail, setName, setEmail}

});