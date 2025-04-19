<script setup lang="ts">

import { PrevAnsStore } from '@/stores/PrevAns'
import {TokenStore} from '@/stores/Token'
import { HistoryStore} from '@/stores/History'
import { ref } from 'vue'
import axios from 'axios'

const view = ref('')

function resetView() {
  view.value=""
}

function addChar(char:string){
  if(view.value.includes("Error")){
    view.value = "";
  }
  view.value = view.value + char;
}

function  removeChar(){
  if(view.value.includes("Error")){
    view.value = "";
  } else {
    view.value = view.value.slice(0,-1);
  }
}

function calculate(){
  try{

    const matches = view.value;

    if (matches.includes("/0")) {
      throw new Error('Divide by zero error');
    }

    var currentResult = eval(view.value)
    view.value = String(Number(currentResult.toFixed(12)));
    PrevAnsStore().setResult(currentResult.toString());
    HistoryStore().addToLog(matches.toString() + " = " + currentResult.toString())
  } catch(e) {
    console.log(e)
    view.value = "Error"
  }
}

async function calculateBackEnd(){
  const config = {
    headers: {
      'Authorization': 'Bearer ' + TokenStore().getToken()
    }
  }

  const stringToSend = view.value;
  try{
    if(stringToSend.includes("/0")){
      throw new Error("Divide by zero error")
    }

    const response = await axios.post("http://localhost:8080/user/calculate", stringToSend, config)

    const answer = response.data

    PrevAnsStore().setResult(answer)
    HistoryStore().addToLog(stringToSend + " = " + answer)
    const equation = stringToSend + " = " + answer;
    console.log(equation)
    const userID = 1;

    const config2 = {
      headers:{
        'Authorization': 'Bearer ' + TokenStore().getToken(),
        'Content-Type': 'text/plain'
      }
    }

    const log = await axios.post("http://localhost:8080/user/Logg",  equation , config2)

    view.value=answer;

  }catch (e){
    console.log(e)
    view.value="Error"
  }
}

function ANS(){
  if(view.value.includes("Error")){
    view.value = "";
  }
  view.value = view.value + PrevAnsStore().getResult();
}


</script>

<template>
  <div class = calc>
    <h3 data-testid="view" id = view>{{view}}</h3>
    <div class = buttonRow>
      <button data-testid="resetButton" @click="resetView()">C</button>
      <button data-testid="ansButton" @click="ANS()">ANS</button>
      <button data-testid="delButton" @click="removeChar()">DEL</button>
      <button data-testid="addButton" @click="addChar('+')">+</button>
    </div>
    <div class = buttonRow>
      <button data-testid="oneButton" @click = "addChar('1')">1</button>
      <button data-testid="twoButton" @click = "addChar('2')">2</button>
      <button data-testid="threeButton" @click = "addChar('3')">3</button>
      <button data-testid="subButton" @click = "addChar('-')">-</button>
    </div>
    <div class = buttonRow>
      <button data-testid="fourButton" @click = "addChar('4')">4</button>
      <button data-testid="fiveButton" @click = "addChar('5')">5</button>
      <button data-testid="sixButton" @click = "addChar('6')">6</button>
      <button data-testid="multiButton" @click = "addChar('*')">*</button>
    </div>
    <div class = buttonRow>
      <button data-testid="sevenButton" @click = "addChar('7')">7</button>
      <button data-testid="eightButton" @click = "addChar('8')">8</button>
      <button data-testid="nineButton" @click = "addChar('9')">9</button>
      <button data-testid="divButton" @click = "addChar('/')">/</button>
    </div>
    <div class = buttonRow>
      <button></button>
      <button data-testid="zeroButton" @click = "addChar('0')">0</button>
      <button data-testid="comaButton" @click = "addChar('.')">.</button>
      <button data-testid="calcButton" @click = "calculateBackEnd()">=</button>
    </div>
  </div>

</template>

<style scoped>
.calc{
  display: block;
  font-size: 30px;
  padding: 15px;
  background-color: #800020;
  border: 2px solid #000;
  background-image: url("src/assets/ufo.webp");
  max-width: 50vh;
}

.buttonRow{
  display: flex;
}

button{
  margin: 5px;
  width: 70px;
  height: 70px;
  background-color: antiquewhite;
  border: 2px solid #000;
  color: var(--color-heading);
  font-weight: bold;
  font-size: 20px;
}

button:hover{
  background-color: var(--color-border);
}

#view{
  background-color: antiquewhite;
  border: 2px solid #000;
  padding: 10px;
  margin: 5px;
  color: var(--color-heading);
  min-height: 10vh;
}
</style>