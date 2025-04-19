<!-- src/components/WebCompiler.vue -->

<script setup lang="ts">
import { ref } from 'vue'
import { compileCode } from '../api.ts'


const inputCode = ref('') // Store the input from user
const outputCode = ref('') //Store the output after compilation

const isLoading = ref(false); // Initial state is not loading

// Example code for easy testing
const exampleCode_01 = `#include<stdio.h>\n\nint main(void) {\n    printf("Hello World\\n");\n    return 0;\n}`
const exampleCode_02 = `#include <stdio.h>\n#include <math.h>\n\nint isPrime(int number) {\n  if (number <= 1) return 0;\n  for (int i = 2; i <= sqrt(number); i++) {\n    if (number % i == 0) return 0; \n  }\n  return 1;\n}\n\nint main() {\n  int number = 29; // Input number to check\n  if (isPrime(number))\n    printf("%d is a prime number.\\n", number);\n  else\n    printf("%d is not a prime number.\\n", number);\n  return 0;\n}`;


const triggerCompilation = async () => {
  if (inputCode.value.trim()) {
    try {
      const result = await compileCode(inputCode.value);
      outputCode.value = result; // Assuming the backend returns the compilation result directly
    } catch (error) {
      console.error("Failed to compile code:", error);
      // Display the error message from the catch block of compileCode
      outputCode.value = error.message;
    }
  }
};



</script>


<template>
  <div class="compiler-container">
    <h1>Compiler of Code</h1>

    <!-- Code Input -->
    <textarea v-model="inputCode" placeholder="Type your code here..." rows="10" cols="50"></textarea>

    <!-- Compile Button -->
    <button @click="triggerCompilation" :disabled="isLoading">Compile Code</button>
    <div v-if="isLoading">Compiling...</div>
    <!-- TODO: Add some fancy loading spinny schnizzle ma hizzle -->


    <!-- Compiled Code Output -->
    <textarea v-model="outputCode" readonly rows="10" cols="50" class="output-area"></textarea>

    <div class="example-code">
      <p>Example code: Hello World</p>
      <textarea readonly :value="exampleCode_01" class="example"></textarea>
    </div>
    <div class="example-code">
      <p>Example code: Prime calculator</p>
      <textarea readonly :value="exampleCode_02" class="example-02"></textarea>
    </div>
  </div>
</template>


<style scoped>
.compiler-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
  align-items: center;
  margin-top: 20px;
}

textarea {
  width: 80%;
  padding: 10px;
  font-family: monospace;
  resize: vertical;
}

.output-area {
  background-color: #213547;
  border: 1px solid #ccc;
}


 .example-code {
   margin-top: 20px;
 }

.example {
  width: 300px;
  height: 140px;
  font-family: monospace;
  overflow: auto;
  white-space: pre;
}

.example-02 {
  width: 500px;
  height: 400px;
  font-family: monospace;
  overflow: auto;
  white-space: pre;
}
</style>

