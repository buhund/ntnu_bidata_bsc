// src/stores/formStore.ts
import { defineStore } from 'pinia';

export const useFormStore = defineStore('form', {
  state: () => ({
    name: '',
    email: '',
    message: '',
  }),
  actions: {
    loadForm() {
      this.name = localStorage.getItem('formData.name') || '';
      this.email = localStorage.getItem('formData.email') || '';
      this.message = localStorage.getItem('formData.message') || '';
    },
    saveForm() {
      localStorage.setItem('formData.name', this.name);
      localStorage.setItem('formData.email', this.email);
      localStorage.setItem('formData.message', this.message);
    },
    clearForm() {
      this.name = '';
      this.email = '';
      this.message = '';
      localStorage.removeItem('formData.name');
      localStorage.removeItem('formData.email');
      localStorage.removeItem('formData.message');
    }
  }
});

