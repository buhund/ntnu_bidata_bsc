import { ref } from 'vue';

export const mockPrevAnsStore = {
  result: ref(''), // Initialize ref with empty string
  getResult() {
    return this.result.value;
  },
  setResult(newResult:string) {
    this.result.value = newResult;
  },
};

