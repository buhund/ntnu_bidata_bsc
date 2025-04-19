import {defineStore} from "pinia";
import {useLocalStorage} from "@vueuse/core";

export const useRegisteringStore = defineStore('registering', () => {
    let registering = useLocalStorage('registering', false);

    function notRegistering() {
        registering.value = false;
    }

    function isRegistering() {
        registering.value = true;
    }

    return {registering, notRegistering, isRegistering};
});