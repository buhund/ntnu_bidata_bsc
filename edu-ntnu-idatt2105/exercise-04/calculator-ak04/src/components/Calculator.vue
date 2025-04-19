<script setup lang="ts">
import { ref } from 'vue';
import * as math from 'mathjs';
import { calculateResult } from '@/api'; // Adjust the import path based on where you placed your API service


const current = ref('');
const operator = ref<((a: number, b: number) => number) | null>(null);
const previous = ref<string | null>(null);
const operatorClicked = ref(false);
const formula = ref('');
const historyOfOperations = ref<Array<{ formula: string; result: string }>>([]);
const showHistory = ref(false);

function clear() {
  current.value = '';
  formula.value = '';
}

function isPreviousCharOperator() {
  const previousChar = formula.value.slice(-1);
  return ['+', '-', '*', '/', '.'].includes(previousChar);
}

function percent() {
  current.value = `${parseFloat(current.value) / 100}`;
}

function appendValue(number: string) {
  if (operatorClicked.value && !isPreviousCharOperator()) {
    current.value = number;
    operatorClicked.value = false;
  } else {
    current.value += number;
  }
  formula.value += number;
}

function comma() {
  if (!current.value.includes('.') && !isPreviousCharOperator()) {
    appendValue('.');
  }
}

function setPrevious() {
  previous.value = current.value;
  current.value = '';
  operatorClicked.value = true;
}

function division() {
  if (!isPreviousCharOperator()) {
    operator.value = (a, b) => a / b;
    setPrevious();
    formula.value += '/';
  }
}

function multiplication() {
  if (!isPreviousCharOperator()) {
    operator.value = (a, b) => a * b;
    setPrevious();
    formula.value += '*';
  }
}

function subtraction() {
  if (!isPreviousCharOperator()) {
    operator.value = (a, b) => a - b;
    setPrevious();
    formula.value += '-';
  }
}

function addition() {
  if (!isPreviousCharOperator()) {
    operator.value = (a, b) => a + b;
    setPrevious();
    formula.value += '+';
  }
}

/**
 * Checks if formula is is valid, i.e. not empty.
 * Calls api.ts/calculateResult, passing formula as an argument.
 *
 * calculateResult passes the formula as an object to the backend.
 * Backend evaluates the object, teasing out the numbers and operators,
 * evaluating it to a mathematical expression and solving it.
 * Result is sent as a response to calculateResult, which returns it to equals.
 *
 * Equals pushes the returned result/answer to the display.
 */
async function equals() {
  if (formula.value.trim()) {
    try {
      const result = await calculateResult(formula.value);
      current.value = isFinite(result) ? result.toString() : 'Ad Infinitum';
      historyOfOperations.value.push({ formula: formula.value, result: current.value });
      formula.value = '';
    } catch (error) {
      current.value = 'Error';
      console.error('Calculation error:', error);
    }
    operatorClicked.value = false;
  }
}


function backspace() {
  current.value = current.value.slice(0, -1);
  formula.value = formula.value.trim().slice(0, -1);
  if (isPreviousCharOperator()) {
    operatorClicked.value = true;
  }
}

function toggleHistory() {
  showHistory.value = !showHistory.value;
}

function clearHistory() {
  historyOfOperations.value = [];
}
</script>


<template>
  <div class="calculator-container">
    <div class="calculator">

      <div class="history_buttons">
        <div @click="toggleHistory" class="history_button">SFH View</div>
        <div @click="clearHistory" class="history_button clear_history_button">Clear History</div>
      </div>

      <div class="display">{{ formula || current || "0"}}</div>

      <div @click="clear" class="button clear_button">C</div>
      <div @click="backspace" class="button backspace_button">DEL</div>

      <div @click="percent" class="button operator_button">%</div>
      <div @click="division" class="button operator_button">÷</div>

      <div @click="appendValue('7')" class="button">7</div>
      <div @click="appendValue('8')" class="button">8</div>
      <div @click="appendValue('9')" class="button">9</div>
      <div @click="multiplication" class="button operator_button">×</div>

      <div @click="appendValue('4')" class="button">4</div>
      <div @click="appendValue('5')" class="button">5</div>
      <div @click="appendValue('6')" class="button">6</div>
      <div @click="subtraction" class="button operator_button">-</div>

      <div @click="appendValue('1')" class="button">1</div>
      <div @click="appendValue('2')" class="button">2</div>
      <div @click="appendValue('3')" class="button">3</div>
      <div @click="addition" class="button operator_button">+</div>

      <div @click="appendValue('0')" class="zero button">0</div>
      <div @click="comma" class="button">,</div>
      <div @click="equals" class="button operator_button">=</div>

    </div>

    <div v-if="showHistory" class="history">
      <div class="history_entry" v-for="(entry, index) in historyOfOperations" :key="index">
        {{ entry.formula }} = {{ entry.result}}
      </div>
    </div>
  </div>

</template>


<style scoped>

.h1-title {
  text-align: center;
  padding-bottom: 12px;
  font-weight: bolder;
}

.calculator-container {
  width: 100%;
  height: 90vh;
  padding-top: 22px;
  margin: 0 auto;
  display: flex;
  background-image: url("/src/assets/background-05.png");
  background-repeat: repeat;
  background-size: cover;
  background-position: center;
  border: 1px solid #222222;
  flex-direction: column;
}

.calculator {
  margin: 0 auto;
  width: 400px;
  font-size: 40px;
  display: grid;
  padding-bottom: 22px;
  grid-template-columns: repeat(4, 1fr);
  grid-auto-rows: minmax(50px, auto);
}

.display {
  grid-column: 1/5;
  margin: 2px;
  background-color: bisque;
  opacity: 80%;
  border: 1px solid #282828;
}

.history {
  margin: 0 auto;
  width: 80%;
  flex-grow: 1;
  overflow-y: auto;
  border: 1px solid #ccc;
  padding: 10px;
  font-size: 16px;
  display: flex;
  min-height: 80px;
  background-color: bisque;
  opacity: 90%;
  flex-direction: column-reverse;
  max-height: 440px;
}

.history_buttons {
  grid-column: 1/5;
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.history_entry {
  margin-bottom: 5px;
}

.history_button {
  background-color: steelblue;
  color: black;
  cursor: pointer;
  flex: 1;
  font-size: 22px;
  border: 1px solid #282828;
  display: flex;
  justify-content: center;
  align-items: center;
}

.clear_history_button {
  background-color: orchid;
  color: black;
  cursor: pointer;
  flex: 1;
  font-size: 22px;
  border: 1px solid #282828;
  display: flex;
  justify-content: center;
  align-items: center;
}

.zero {
  grid-column: 1/3;
}

.button {
  background-color: lightblue;
  font-weight: bold;
  color: black;
  border: 1px solid #282828;
  display: flex;
  margin: 2px;
  justify-content: center;
  align-items: center;
  opacity: 80%;
}

.operator_button {
  background-color: coral;
}

.backspace_button{
  background-color: aquamarine;
  font-color: black;

}

.clear_button{
  background-color: deeppink;
  color: black;
}

.button, .operator_button, .backspace_button, .clear_button {
  transition: transform 0.2s, margin 0.2s, border 0.2s;
  margin: 2px;
  border: 1px solid #282828;
  user-select: none;
  cursor: pointer;;
}

.button:hover, .operator_button:hover, .backspace_button:hover, .clear_button:hover {
  transform: scale(1.05);
  margin: 1px;
  border: 2px solid red;
  user-select: none;
}


</style>