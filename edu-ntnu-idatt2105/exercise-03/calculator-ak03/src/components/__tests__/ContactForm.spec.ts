/**
 * Unit Test, Vitest
 */
import { describe, it, expect, beforeEach, vi, afterEach } from 'vitest';
import { mount } from '@vue/test-utils';
import { createPinia, setActivePinia } from 'pinia';
import axios from 'axios';
import ContactForm from '@/components/ContactForm.vue';
import { useFormStore } from '@/stores/formStore';
import { nextTick } from 'vue';

// Mock axios
vi.mock('axios', () => ({
  default: {
    post: vi.fn(() => Promise.resolve({ data: { message: 'Message submitted successfully!' } }))
  },

}));

// Mock localStorage
vi.stubGlobal('localStorage', {
  getItem: vi.fn((key) => null), // Default to null unless a specific key's value is set in a test
  setItem: vi.fn(),
  removeItem: vi.fn(),
});

describe('ContactForm.vue', () => {
  beforeEach(() => {
    setActivePinia(createPinia());
  });

  afterEach(() => {
    vi.clearAllMocks(); // Clear mocks to ensure fresh state for each test
  });

  it('loads form data from localStorage on mount', async () => {
    const wrapper = mount(ContactForm, {
      global: {
        plugins: [createPinia()],
      },
    });

    // Assertions to verify that data was loaded from localStorage
    const store = useFormStore();

    // Default mock returns null --> these should be empty
    expect(store.name).toBe('');
    expect(store.email).toBe('');
    expect(store.message).toBe('');
  });

  it('validates form fields correctly and displays error messages', async () => {
    const wrapper = mount(ContactForm, {
      global: {
        plugins: [createPinia()],
      },
    });

    // Input values which will trigger validation errors
    await wrapper.find('[data-testid="nameField"]').setValue('O');
    await wrapper.find('[data-testid="emailField"]').setValue('tideman');
    await wrapper.find('[data-testid="messageField"]').setValue('less10');

    // Trigger form submission
    await wrapper.find('form').trigger('submit.prevent');

    // Check if error messages are displayed as expected
    expect(wrapper.text()).toContain('Name must be at least 2 characters long.');
    expect(wrapper.text()).toContain('Email must be valid.');
    expect(wrapper.text()).toContain('Message must be at least 10 characters long.');
  });

  it('submits the form successfully and clears the form on success', async () => {
    const wrapper = mount(ContactForm, {
      global: {
        plugins: [createPinia()],
      },
    });

    await wrapper.find('[data-testid="nameField"]').setValue('Optimus Prime');
    await wrapper.find('[data-testid="emailField"]').setValue('optimus@cybertron.com');
    await wrapper.find('[data-testid="messageField"]').setValue('This is more than 10 characters.');
    await wrapper.find('form').trigger('submit.prevent');

    await nextTick();

    // Assert that axios.post was called correctly
    expect(axios.post).toHaveBeenCalledWith('http://localhost:3000/submissions', {
      name: 'Optimus Prime',
      email: 'optimus@cybertron.com',
      message: 'This is more than 10 characters.',
    });

    // Check for success message
    expect(wrapper.text()).toContain('Message submitted successfully!');

    // Verify form fields are cleared after successful submission
    const store = useFormStore();
    expect(store.name).toBe('');
    expect(store.email).toBe('');
    expect(store.message).toBe('');
  });
});
