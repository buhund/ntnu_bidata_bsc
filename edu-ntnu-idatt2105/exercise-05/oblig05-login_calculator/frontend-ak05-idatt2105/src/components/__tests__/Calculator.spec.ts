/**
 * Unit Test, Vitest
 */

import { describe, it, expect } from "vitest";
import { mount } from "@vue/test-utils";
import Calculator from "@/components/Calculator.vue";

describe("Calculator.vue", () => {
    it("Clear current input and formula", async () => {
        const wrapper = mount(Calculator);
        await wrapper.vm.appendValue("5");
        await wrapper.vm.clear();
        expect(wrapper.vm.current).toBe("");
        expect(wrapper.vm.formula).toBe("");
    });

    it("Handles addition correctly", async () => {
        const wrapper = mount(Calculator);
        await wrapper.vm.appendValue("5");
        await wrapper.vm.addition();
        await wrapper.vm.appendValue("3");
        await wrapper.vm.equals();
        expect(wrapper.vm.current).toBe("8");
    });

    it("Handles subtraction correctly", async () => {
        const wrapper = mount(Calculator);
        await wrapper.vm.appendValue("5");
        await wrapper.vm.subtraction();
        await wrapper.vm.appendValue("3");
        await wrapper.vm.equals();
        expect(wrapper.vm.current).toBe("2");
    });

    it("Handles multiplication correctly", async () => {
        const wrapper = mount(Calculator);
        await wrapper.vm.appendValue("5");
        await wrapper.vm.multiplication();
        await wrapper.vm.appendValue("3");
        await wrapper.vm.equals();
        expect(wrapper.vm.current).toBe("15");
    });

    it("Handles division correctly", async () => {
        const wrapper = mount(Calculator);
        await wrapper.vm.appendValue("10");
        await wrapper.vm.division();
        await wrapper.vm.appendValue("2");
        await wrapper.vm.equals();
        expect(wrapper.vm.current).toBe("5");
    });

    it("Handles divide by zero correctly", async () => {
        const wrapper = mount(Calculator);
        await wrapper.vm.appendValue('5');
        await wrapper.vm.division();
        await wrapper.vm.appendValue('0');
        await wrapper.vm.equals();
        expect(wrapper.vm.current).toBe('Ad Infinitum');
    });

    it("Handles decimal values correctly", async () => {
        const wrapper = mount(Calculator);
        await wrapper.vm.appendValue('5');
        await wrapper.vm.appendValue('.');
        await wrapper.vm.appendValue('3');
        await wrapper.vm.addition();
        await wrapper.vm.appendValue('3');
        await wrapper.vm.appendValue('.');
        await wrapper.vm.appendValue('5');
        await wrapper.vm.equals();
        expect(wrapper.vm.current).toBe('8.8');
    });

    it("Clear history of operations", async () =>{
        const wrapper = mount(Calculator);
        await wrapper.vm.appendValue('5');
        await wrapper.vm.addition();
        await wrapper.vm.appendValue('3');
        await wrapper.vm.equals();

        expect(wrapper.vm.historyOfOperations.length).toBeGreaterThan(0); // Should be numbers on the tape
        await wrapper.vm.clearHistory();
        expect(wrapper.vm.historyOfOperations).toHaveLength(0); // Tape should be cleared
    });

});


