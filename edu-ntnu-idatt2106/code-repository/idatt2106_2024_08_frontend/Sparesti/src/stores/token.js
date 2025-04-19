/**
 * This file contains the store for the token.
 * The token is stored in the store and can be accessed from anywhere in the application.
 * The token is also persisted in the sessionStorage.
 */
import { defineStore } from "pinia";
import {ref} from "vue";

export const useTokenStore = defineStore("token", () => {
    let jwtToken = ref(null);
    let loggedInUser = ref(null);

    function saveEmailAndTokenInStore(email, token) {
        jwtToken.value = token;
        loggedInUser.value = email;
    }

    function clearToken() {
        jwtToken.value = null;
        loggedInUser.value = null;


    }

    function getAxiosAuthorizationConfig() {
        return {
            headers: {
                "Content-Type": "application/json",
                "Authorization" : "Bearer " + jwtToken.value
            },
        };
    }

    function getLoggedInUser() {
        return loggedInUser.value;
    }

    return {jwtToken, loggedInUser, saveEmailAndTokenInStore, getAxiosAuthorizationConfig, getLoggedInUser, clearToken}
},
{
    persist: {
        enabled: true,
        storage: sessionStorage,
    }
})
