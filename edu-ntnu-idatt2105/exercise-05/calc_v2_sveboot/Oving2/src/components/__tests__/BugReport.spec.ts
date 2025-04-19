import { describe, it, expect, beforeEach } from 'vitest'

import { mount } from '@vue/test-utils'
import BugReport from '../BugReport.vue'
import { createPinia } from 'pinia'


describe('BugReport', () =>{

  let pinia = createPinia()
  let wrapper = mount(BugReport, {
    global:{
      plugins:[pinia]
    }
  })

  beforeEach(function(){
    pinia = createPinia()
    wrapper = mount(BugReport, {
      global:{
        plugins:[pinia]
      }
    })
  })

  it("Perform Name validation", async () =>{
    const nameField = wrapper.find('[data-testid="name"]')
    await nameField.trigger('blur')
    const nameError = wrapper.find('[data-testid="NameError"]').text()
    expect(nameError).toBe("Please enter a valid name");
  })

  it("Perform Email validation", async () =>{
    const nameField = wrapper.find('[data-testid="email"]')
    await nameField.trigger('blur')
    const emailError = wrapper.find('[data-testid="EmailError"]').text()
    expect(emailError).toBe("Please enter a valid email address")
  })

  it("Perform Description validation", async () =>{
    const nameField = wrapper.find('[data-testid="description"]')
    await nameField.trigger('blur')
    const emailError = wrapper.find('[data-testid="DescriptionError"]').text()
    expect(emailError).toBe("Description is required")
  })
})