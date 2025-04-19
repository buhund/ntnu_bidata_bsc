<script setup>
import "../assets/baseStyle.css";
import "../assets/configurationStyle.css";
import {navigateToPage} from "@/utils/httputils.js";
import UploadFile from "@/components/UploadFile.vue";
import { ref } from "vue";
import {useRegisteringStore} from "@/stores/registering.js";

const visible = ref(false);
const registeringStore = useRegisteringStore();
/**
 * Make an attempt to navigate to the '/uploadFileConfig' page and log any error messages.
 */
(async () => {
  try{
    await navigateToPage('/uploadFileConfig');
    if (!registeringStore.registering) {
      await navigateToPage("/home");
    }
    visible.value = false;
    document.getElementById("nextPageButton").disabled = true;
  } catch (error){
    console.error('Error fetching data:', error);
  }
})();

/**
 * Updates the disabled status of the "nextPageButton" element based on the provided status.
 *
 * @param {boolean} status - The status indicating whether the button should be disabled or not
 * @returns {void}
 */
const fileUploaded = (status) => {
  document.getElementById("nextPageButton").disabled = !status;
}
</script>

<template>
  <div class="flex-container">
    <div class="fileconfig-content">
      <div class="header">
        <h1 class="title">Konfigurering</h1>
      </div>

      <div class="uploadFile">
        <UploadFile
        v-model:status="visible"
        @update:status="fileUploaded"/>
      </div>

      <br>
      <div class="buttonflex">
        <button class="basicButton" id="nextPageButton"  @click="navigateToPage('/goalConfig')">Neste</button>
        <button class="basicButton" @click="navigateToPage('/goalConfig')" >Hopp over</button>
      </div>
    </div>
    <div class="footer">
    </div>
  </div>
</template>

<style scoped>
.buttonflex{
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.basicButton{
  margin-top: 20px;
}
.basicButton:hover{
  background-color: #89a76c;
}

.uploadFile {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}
#nextPageButton:disabled{
  cursor: not-allowed;
}
</style>