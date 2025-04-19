import { describe, it, expect, beforeEach } from 'vitest'

import { mount } from '@vue/test-utils'
import CalculatorMain from '../CalculatorMain.vue'
import { createPinia } from 'pinia'

describe('CalculatorMain', () => {
  let pinia = createPinia()
  let wrapper = mount(CalculatorMain, {
    global:{
      plugins:[pinia]
    }
  })

  beforeEach(function(){
    pinia = createPinia()
    wrapper = mount(CalculatorMain, {
      global:{
        plugins:[pinia]
      }
    })
  })

  it("Performs addition correctly", async () =>{
    await wrapper.find('[data-testid="oneButton"]').trigger('click')
    await wrapper.find('[data-testid="addButton"]').trigger('click')
    await wrapper.find('[data-testid="twoButton"]').trigger('click')
    await wrapper.find('[data-testid="calcButton"]').trigger('click')
    const viewContent = wrapper.find('[data-testid="view"]').text()
    expect(viewContent).toBe("3")
  })

  it("Performs subtraction correctly", async () =>{
    await wrapper.find('[data-testid="twoButton"]').trigger('click')
    await wrapper.find('[data-testid="subButton"]').trigger('click')
    await wrapper.find('[data-testid="oneButton"]').trigger('click')
    await wrapper.find('[data-testid="calcButton"]').trigger('click')
    const viewContent = wrapper.find('[data-testid="view"]').text()
    expect(viewContent).toBe("1")
  })

  it("Performs multiplication correctly", async () =>{
    await wrapper.find('[data-testid="twoButton"]').trigger('click')
    await wrapper.find('[data-testid="multiButton"]').trigger('click')
    await wrapper.find('[data-testid="twoButton"]').trigger('click')
    await wrapper.find('[data-testid="calcButton"]').trigger('click')
    const viewContent = wrapper.find('[data-testid="view"]').text()
    expect(viewContent).toBe("4")
  })

  it("Performs division correctly", async () =>{
    await wrapper.find('[data-testid="fourButton"]').trigger('click')
    await wrapper.find('[data-testid="divButton"]').trigger('click')
    await wrapper.find('[data-testid="twoButton"]').trigger('click')
    await wrapper.find('[data-testid="calcButton"]').trigger('click')
    const viewContent = wrapper.find('[data-testid="view"]').text()
    expect(viewContent).toBe("2")
  })

  it("Trigger divide by zero error", async () =>{
    await wrapper.find('[data-testid="fourButton"]').trigger('click')
    await wrapper.find('[data-testid="divButton"]').trigger('click')
    await wrapper.find('[data-testid="zeroButton"]').trigger('click')
    await wrapper.find('[data-testid="calcButton"]').trigger('click')
    const viewContent = wrapper.find('[data-testid="view"]').text()
    expect(viewContent).toBe("Error")
  })

  it("Perform reset correctly", async () =>{
    await wrapper.find('[data-testid="fourButton"]').trigger('click')
    await wrapper.find('[data-testid="divButton"]').trigger('click')
    await wrapper.find('[data-testid="zeroButton"]').trigger('click')
    let viewContent = wrapper.find('[data-testid="view"]').text()
    expect(viewContent).toBe("4/0")
    await wrapper.find('[data-testid="resetButton"]').trigger('click')
    viewContent = wrapper.find('[data-testid="view"]').text()
    expect(viewContent).toBe("")
  })

  it("Perform delete correctly", async () =>{
    await wrapper.find('[data-testid="fourButton"]').trigger('click')
    await wrapper.find('[data-testid="divButton"]').trigger('click')
    await wrapper.find('[data-testid="zeroButton"]').trigger('click')
    let viewContent = wrapper.find('[data-testid="view"]').text()
    expect(viewContent).toBe("4/0")
    await wrapper.find('[data-testid="delButton"]').trigger('click')
    viewContent = wrapper.find('[data-testid="view"]').text()
    expect(viewContent).toBe("4/")
  })

  it("Perform ans correctly", async () =>{
    await wrapper.find('[data-testid="fourButton"]').trigger('click')
    await wrapper.find('[data-testid="divButton"]').trigger('click')
    await wrapper.find('[data-testid="twoButton"]').trigger('click')
    await wrapper.find('[data-testid="calcButton"]').trigger('click')
    let viewContent = wrapper.find('[data-testid="view"]').text()
    expect(viewContent).toBe("2")
    await wrapper.find('[data-testid="resetButton"]').trigger('click')
    await wrapper.find('[data-testid="ansButton"]').trigger('click')
    viewContent = wrapper.find('[data-testid="view"]').text()
    expect(viewContent).toBe("2")
  })
})