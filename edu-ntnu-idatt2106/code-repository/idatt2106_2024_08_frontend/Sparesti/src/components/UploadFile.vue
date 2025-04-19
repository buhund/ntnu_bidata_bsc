<script setup>
/**
 * A component that allows the user to upload CSV files to the application.
 * The component includes a button to select files, a list of selected files, and a button to upload the files.
 * It emits a status flag to the parent component when the files are uploaded.
 *
 * @component
 * @props
 * status - The status of the file upload process.
 *
 * @function
 * triggerFileInput - Triggers the file input when the button is clicked.
 * handleFileUpload - Handles the file upload process.
 * uploadFiles - Uploads the selected files to the application.
 * deleteFile - Deletes a selected file from the list.
 * changeIconOnHover - Changes the icon to a red version upon hovering.
 * resetIconAfterHover - Resets the icon back to the original black version after hovering.
 *
 * @data
 * files - An array of selected files.
 * uploadStatus - The status of the file upload process.
 * trashIcon - The icon for deleting files.
 *
 * @computed
 * isUploadable - A flag indicating whether files are selected and can be uploaded.
 *
 * @template
 * Includes a button to select files, a list of selected files, and a button to upload the files.
 */
import blackTrashIcon from '../assets/trash-icon.svg';
import redTrashIcon from '../assets/red-trash-icon.svg';
import {computed, ref} from 'vue';
import axios from "axios";
import {useTokenStore} from "@/stores/token.js";
import {ip} from "@/utils/httputils.js";

const triggerFileInput = () => {
  document.getElementById('fileInput').click();
};

const files = ref([]);
const uploadStatus = ref('');

const tokenStore = useTokenStore();
const userEmail = tokenStore.getLoggedInUser();

const props = defineProps(['status'])
const emit = defineEmits(['update:status']);
const status = defineModel({ default: false })
const trashIcon = ref(blackTrashIcon);

const isUploadable = computed(() => files.value.length > 0);

const handleFileUpload = (event) => {
  if (uploadStatus.value === 'Alle filer lastet opp!') {
    uploadStatus.value = '';
    files.value = [];
  }
  const newFiles = Array.from(event.target.files);
  files.value = [...files.value, ...newFiles];
};

const uploadFiles = async () => {
  console.log('statustest');
  emit('update:status', true);
  if (files.value.length === 0) {
    uploadStatus.value = 'Ingen filer valgt';
    return;
  }
  uploadStatus.value = 'Laster opp...';
  try {
    for (const file of files.value) {
      await axios.postForm(`https://${ip}:8443/api/users/${userEmail}/transactions_csv_to_first_account`,  {
        file: file
      }, tokenStore.getAxiosAuthorizationConfig()
    ).then( function (response) {
        console.log(response);
        uploadStatus.value = 'Alle filer lastet opp!';
        emit('status', true);
    });
    }
    
  } catch (error) {
    uploadStatus.value = 'Feil ved opplasting av filer';
  }
};

const deleteFile = (index) => {
  files.value.splice(index, 1);
};

/**
 * Changes the icon to a red version upon hovering.
 * @returns {void}
 */
function changeIconOnHover() {
  trashIcon.value = redTrashIcon;
}

/**
 * Resets the icon back to the original black version after hovering.
 * @returns {void}
 */
function resetIconAfterHover() {
  trashIcon.value = blackTrashIcon;
}
</script>

<template>
  <div class="box">
    <h3>Last opp kontoutskriftene dine (.csv)</h3>
    <input type="file" id="fileInput" class="hidden" multiple @change="handleFileUpload" accept=".csv">
    <button @click="triggerFileInput" class="customFileButton">Velg filer</button>
    <ul>
      <li v-for="(file, index) in files" :key="index">
        {{ file.name }}
        <img :src=trashIcon v-if="uploadStatus !== 'Alle filer lastet opp!'" alt="Slett" class="delete-icon" @mouseover="changeIconOnHover" @mouseleave="resetIconAfterHover" @click="deleteFile(index)">
      </li>
    </ul>
    <button @click="uploadFiles" :disabled="!isUploadable" class="upload">Last opp valgte filer</button>
    <p v-if="uploadStatus">{{ uploadStatus }}</p>
  </div>

</template>

<style scoped>
.box{
  background-color: white;
  min-height: 30vh;
  width: 350px;
  border-radius: 30px;
  display: flex;
  flex-direction: column;
  padding: 10px;
  justify-content: center;
  align-items: center;
}

.upload, .customFileButton {
  margin-bottom: 15px;
  background-color: #A4CD92;
  min-width: 200px;
  border-radius: 30px;
  text-align: center;
  min-height: 50px;
  padding: 10px;
  border: none;
  font-size: 15px;
  cursor: pointer;
}

.upload:hover, .customFileButton:hover {
  background-color: #89a76c;
}
.upload:disabled{
  cursor: not-allowed;
}

.hidden {
  display: none;
}

.velgFileButton button{
  background-color: #A4CD92;
  min-width: 250px;
  border-radius: 30px;
  text-align: center;
  min-height: 50px;
  padding: 10px;
  border: none;
  font-size: 15px;
}

.delete-icon{
  margin-left: 20px;
  height: 15px;
  width: auto;
}

ul {
  list-style-type: none;
}

@media screen and (max-width: 700px) {
  .box {
    max-width: 250px;
  }
}
</style>