<script setup>
	import NavBar from "@/components/NavBar.vue";
	import "../assets/baseStyle.css";
  import router from "@/router/index.js";
  import {useTokenStore} from "@/stores/token.js";
  import axios from "axios";
  import {ref} from "vue";
  import {navigateToPage} from "@/utils/httputils.js";
  import {ip} from "@/utils/httputils.js";
  import {useField, useForm} from "vee-validate";
  import { Form as ValidationForm } from 'vee-validate';
  import * as yup from "yup";
  import EditKnowlegde from "@/components/Knowledge.vue";
  import ChangeOfHabit from "@/components/ChangeOfHabit.vue";
  import UploadFile from "@/components/UploadFile.vue";

  const {handleSubmit, errors, meta} =useForm();
  const password = ref('');
  const editing = ref(false);
  const visible = ref(false);
  const fileUploaded = ref(false);

  /**
   * Toggles the editing mode.
   */
  const toggleEditing = () => {
    editing.value = !editing.value;
  }

  const nameRegex = /^[A-Za-zæøåÆØÅ\s-]+$/;
  const emailRegex = /^[a-zA-Z0-9._%+-æøåÆØÅ]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  const emailInUse = ref('');
  const { value: firstName } = useField('firstName', yup.string().required('Fornavn må fylles ut').matches(nameRegex, 'Fornavn kan bare inneholde bokstaver'));
  const { value: lastName } = useField('lastName', yup.string().required('Etternavn må fylles ut').matches(nameRegex, 'Etternavn kan bare inneholde bokstaver'));
  const { value: email } = useField('email', yup.string().required('Epost må fylles ut').matches(emailRegex, 'Må være en gyldig epost'));
  let knowledgeLevel = '';
  let habitChange = '';
  let creationDate;
  let loginStreak;
  let lastLoginDate;

  /**
   * Maps knowledge level values to their corresponding labels.
   *
   * @type {Object}
   */
  const knowledgeLevelMap = {
    low: 'Lav',
    medium: 'Middels',
    high: 'Høy'
  };

  /**
   * Maps habit change level values to their corresponding labels.
   *
   * @type {Object}
   */
  const changeOfHabitMap = {
    low: 'Litt',
    medium: 'Middels',
    high: 'Veldig'
  };

  /**
   * Translates the knowledge level value into its corresponding label.
   *
   * @returns {string} - The translated knowledge level label.
   * @param Knowledgelevel
   */
  const translateKnowledgeLevel = (Knowledgelevel) => {
    return knowledgeLevelMap[Knowledgelevel] || Knowledgelevel;
  };

  /**
   * Translates the habit change level value into its corresponding label.
   *
   * @returns {string} - The translated habit change level label.
   * @param Habitlevel
   */
  const translateChangeOfHabitLevel = (Habitlevel) => {
    return changeOfHabitMap[Habitlevel] || Habitlevel;
  };

  /**
   * Retrieves user data from backend and fills in the form.
   *
   * @returns {Promise<void>} - A promise that resolves when the user data is fetched and filled in.
   */
  (async () => {
    try{
      const tokenStore = useTokenStore();
      await navigateToPage('/profile')
      const response2 = await axios.get(`https://${ip}:8443/api/users/` + tokenStore.getLoggedInUser(), tokenStore.getAxiosAuthorizationConfig());
      if (response2.status === 200) {
        const userData = response2.data
        firstName.value = userData.firstName
        lastName.value = userData.lastName
        email.value = userData.email
        password.value = userData.password
        knowledgeLevel = userData.knowledgeLevel
        habitChange = userData.willingnessToHabitChangeLevel
        lastLoginDate = userData.lastLoginDate
        creationDate = userData.creationDate
        loginStreak = userData.loginStreak
      } else {
        await router.push('/login')
      }
    } catch (error){
      console.error('Error fetching data:', error);
    }
  })();

  /**
   * Updates the user profile with the modified data.
   *
   * @returns {Promise<void>} - A promise that resolves when the user profile is updated.
   */
  const updateProfile = async () => {
    try {
      const tokenStore = useTokenStore();
      const updatedUserData = {
        firstName: firstName.value,
        lastName: lastName.value,
        email: email.value,
        knowledgeLevel: knowledgeLevel,
        willingnessToHabitChangeLevel: habitChange,
        creationDate: creationDate,
        loginStreak: loginStreak,
        lastLoginDate: lastLoginDate,
      };
      const response = await axios.put(`https://${ip}:8443/api/users/` + tokenStore.getLoggedInUser(), updatedUserData, tokenStore.getAxiosAuthorizationConfig());
      if (response.status === 200) {
        await navigateToPage('/profile');
        alert("Profil oppdatert")
        editing.value = false;
      } else {
        console.error('Feil ved oppdatering av profil');
      }
    } catch (error) {
      if (error.response && error.response.status === 409) {
        console.error("Mail already registered")
        emailInUse.value = 'Eposten er allerede registrert'
      }
      console.error('Feil ved oppdatering av profil:', error);
    }
  };

  /**
   * Deletes the user profile.
   *
   * @returns {Promise<void>} - A promise that resolves when the user profile is deleted.
   */
  const deleteProfile = (async () => {
    const tokenStore = useTokenStore();
    const confirmDelete = confirm("Er du sikker på at du vil slette brukeren?");
    if(confirmDelete){
      try{
        const response = await axios.delete(`https://${ip}:8443/api/users/`+ tokenStore.getLoggedInUser(), tokenStore.getAxiosAuthorizationConfig());
        console.log(response.data);
        await router.push("/")
      } catch (error){
        if (error.response && error.response.status === 404) {
          console.error("User not found")
          emailInUse.value = 'Noe gikk galt'
        }
        console.error('Login failed:', error);
      }
    }
  })

  /**
   * Token store used for managing and persisting tokens.
   *
   * @typedef {Object} TokenStore
   *
   * @property {function} getToken - Retrieves a token from the token store.
   * @property {function} setToken - Sets a token in the token store.
   * @property {function} deleteToken - Deletes a token from the token store.
   */
  const tokenStore = useTokenStore();

  /**
   * Asynchronously logs out the user.
   * This function removes the 'token' from the sessionStorage and clears the tokenStore.
   * It then redirects the user to the homepage.
   *
   * @function logout
   * @async
   * @return {Promise<void>} A promise that resolves when the logout process is complete.
   */
  const logout = async () => {
    sessionStorage.removeItem('token');
    tokenStore.clearToken();
    await router.push("/")
  }

</script>

<template>
  <div class="flex-container">
    <div class="header">
      <h1 class="title">Profil</h1>
    </div>
    <div class="main" id="profile">
      <ValidationForm @submit="handleSubmit(updateProfile)">
        <div class="label-container">
          <div class="profile-container">
          <label for="firstname" class="label">Fornavn</label>
          <span v-if="errors.firstName" class="error">{{ errors.firstName }}</span>
          <div class="input-container">
            <input type="text" name="firstname" v-model="firstName" :disabled="!editing" placeholder="Fornavn" class="input">
          </div>
          <label for="lastName" class="label">Etternavn</label>
          <span v-if="errors.lastName" class="error">{{ errors.lastName }}</span>
          <div class="input-container">
            <input type="text" name="lastName" v-model="lastName" :disabled="!editing" placeholder="Etternavn" class="input">
          </div>
          <label for="email" class="label">E-post</label>
          <span v-if="errors.email" class="error">{{ errors.email }}</span>
          <div class="input-container">
            <input type="text" name="email" v-model="email" :disabled="!editing" placeholder="E-post" class="input">
          </div>
          <button class="btn" id="changePassword" @click="navigateToPage('/editPassword')" :hidden="!editing">Endre passord</button>

          <label for="knowledgeLevel"  class="label" :hidden="editing">Kunnskapsnivå</label>
          <input type="text" name="knowledgeLevel" :value="translateKnowledgeLevel(knowledgeLevel)" disabled class="input" v-show="!editing">

          <label for="changeOfHabit"  class="label" :hidden="editing">Vilje til vaneendring</label>
          <input type="text" name="changeOfHabit" :value="translateChangeOfHabitLevel(habitChange)" disabled class="input" v-show="!editing">

          <div class="knowledge">
            <EditKnowlegde v-if="editing && knowledgeLevel !== ''" :knowledgeLevel="knowledgeLevel" @knowledgeLevel="(knowledgelevel) => knowledgeLevel = knowledgelevel"/>
          </div>
          <div class="habit">
            <ChangeOfHabit v-if="editing && habitChange !== ''" :habitChange="habitChange" @habitChange="(habitlevel) => habitChange = habitlevel"/><br><br>
          </div>
          </div>
          <div class="uploadFile">
            <UploadFile
                v-if="!editing"
                v-model:status="visible"
                @update:status="fileUploaded"/>
          </div>

          <button class="btn" id="edit" @click="editing ? updateProfile() : toggleEditing()" :disabled="editing && (!meta.valid || !meta.dirty)">
            {{ editing ? 'Lagre' : 'Rediger' }}
          </button>

          <div v-if="emailInUse" class="error"> {{emailInUse}}</div>
          <button class="btn" id="logOut" @click="logout" :hidden="editing">Logg ut</button>
          <button class="btn" id="delete" @click="deleteProfile" :hidden="!editing">Slett bruker</button><br>
        </div>
      </ValidationForm>
    </div>
    <div class="footer">
        <nav-bar/>
    </div>
  </div>

</template>

<style scoped>

.profile-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: left;
  width: 100%;
  border-radius: 30px;
}

.label-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: left;
  width: 100%;
}
.main{
  height: auto;
}
.input-container {
  width: 100%;
  display: flex;
  justify-content: center;
  overflow: hidden;
}
.label {
  font-size: 16px;
  font-weight: bold;
}
.input:disabled{
  color: black;
  background-color: white;
}
.habit{
  margin-bottom: -100px;
  margin-top: -40px;
}
#edit, #logOut, #delete {
  align-self: center;
  margin-top: 15px;
}

.knowledge{
  margin-bottom: 20px;
  margin-top: -30px;
}

.uploadFile{
  margin-top: 100px;
}

#changePassword
{
  margin-bottom: 15px;
}
#edit{
  background-color: #A4CD92;
}
#edit:hover{
  background-color: #89a76c;
}
#logOut{
  background-color: #fb7782;
  margin-bottom: 10vh;
}
#logOut:hover{
  background-color: #fb4c4e;
}

</style>