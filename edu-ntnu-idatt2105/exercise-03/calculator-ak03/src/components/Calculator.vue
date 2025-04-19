<script>
import * as math from "mathjs";

export default {
  name: "Calculator",
  data() {
    return {
      current: "",
      operator: null,
      previous: null,
      solve: "",
      operatorClicked: false,
      formula: "",
      historyOfOperations: [],
      showHistory: false,
      divisionByZeroError: false,
    }
  },

  methods: {
    clear() {
      this.current = "";
      this.formula = "";
    },

    // Prevent writing things like 1++2
    isPreviousCharOperator() {
      const previousChar = this.formula.slice(-1);
      return ["+", "-", "*", "/", "."].includes(previousChar);
    },

    percent() {
      this.current = `${parseFloat(this.current) / 100}`
    },

    appendValue(number) {
      if (this.operatorClicked && !this.isPreviousCharOperator()) {
        this.current = number;
        this.operatorClicked = false;
      } else {
        this.current = `${this.current}${number}`;
      }
      this.formula += number;
    },

    comma() {
      if (this.current.indexOf(".") === -1 && !this.isPreviousCharOperator()) {
        this.appendValue(".");
      }
    },

    setPrevious() {
      this.previous = this.current;
      this.current = "";
      this.operatorClicked = true;
    },

    division() {
      if (!this.isPreviousCharOperator()) {
        this.operator = (a, b) => a / b;
        this.setPrevious();
        this.formula += "/";
      }
    },

    multiplication() {
      if (!this.isPreviousCharOperator()) {
        this.operator = (a, b) => (a * b);
        this.setPrevious();
        this.formula += "*";
      }
    },

    subtraction() {
      if (!this.isPreviousCharOperator()) {
        this.operator = (a, b) => (a - b);
        this.setPrevious();
        this.formula += "-";
      }
    },

    addition() {
      if (!this.isPreviousCharOperator()) {
        this.operator = (a, b) => (a + b);
        this.setPrevious();
        this.formula += "+";
      }
    },

    equals() {
      try {
        const result = math.evaluate(this.formula);
        if (result === Infinity || result === -Infinity) {
          this.current = "Ad Infinitum";
        } else {
          this.current = result.toString();
        }
        this.historyOfOperations.push({
          formula: this.formula,
          result: this.current,
        });
        this.formula = "";
      } catch (error) {
        this.current = "Error";
      }
      this.operatorClicked = false;
    },


    backspace() {
      if (this.current.length > 0) {
        // Remove last character from current
        this.current = this.current.slice(0, -1);
      }

      if (this.formula.length > 0) {
        // Remove last character from formula
        this.formula = this.formula.trim().slice(0, -1);
      }
      if (this.isPreviousCharOperator()) {
        this.operatorClicked = true;
      }
    },

    toggleHistory() {
      this.showHistory = !this.showHistory;
    },

    // Clear history
    clearHistory() {
      this.historyOfOperations = [];
    },




  }
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