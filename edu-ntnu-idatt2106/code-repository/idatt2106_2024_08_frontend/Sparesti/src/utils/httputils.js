/**
 * This file contains utility functions for making HTTP requests.
 */
import axios from "axios";
import {useTokenStore} from "@/stores/token.js";
import router from "@/router/index.js";

export const ip = 'localhost';

export async function navigateToPage(page) {
    try{
        const tokenStore = useTokenStore();
        const response = await axios.get(`https://${ip}:8443/api/check_token`, tokenStore.getAxiosAuthorizationConfig());
        if (response.status === 200) {
            await router.push(page)
        }
    } catch (error){
        if (error.response && error.response.status === 401) {
            console.error("timeout")
            await router.push('/')
        }
    }

}
