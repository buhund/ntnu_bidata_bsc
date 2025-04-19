<script setup>
/**
 * A Vue component to determine a user's willingness to change habits with a radio selection.
 * It initializes by checking the appropriate radio input based on the passed habitChange prop.
 *
 * @component
 * @props
 *  {string} habitChange - Describes the user's level of willingness to change habits (low, medium, high).
 *
 * @onMounted
 *  Sets the corresponding radio button ('lav', 'middels', 'hoy') to checked based on the habitChange prop value.
 *
 * @template
 *  Displays a question about willingness to change habits with three levels to choose from. Users can select
 *  their willingness level which emits the respective change back to the parent component.
 *
 * @example
 * <template>
 *   <div class="main">
 *     <div class="flex-container3">
 *       <div class="box">
 *         <ul class="question">
 *           <h3>Hvor villig er du til å endre dine vaner?</h3>
 *           <input type="radio" id="lav" name="grad" value="lav" @click="$emit('habitChange', 'low')">
 *           <label for="lav">Litt</label>
 *           <input type="radio" id="middels" name="grad" value="middels" @click="$emit('habitChange', 'medium')">
 *           <label for="middels">Middels</label>
 *           <input type="radio" id="hoy" name="grad" value="hoy" @click="$emit('habitChange', 'high')">
 *           <label for="hoy">Veldig</label>
 *         </ul>
 *       </div>
 *     </div>
 *   </div>
 * </template>
 */

import {onMounted} from "vue";

/**
 * Props for the component.
 * @type {Object}
 * @property {string} habitChange - The level of change of habits.
 */
const props = defineProps(['habitChange'])

console.log('mount')

/**
 * Performed when the component is mounted.
 */
onMounted(() => {
  console.log(props.habitChange)
  if (props.habitChange === "low") {
    document.getElementById('lav').checked = true;
  }
  if (props.habitChange === "medium") {
    document.getElementById('middels').checked = true;
  }
  if (props.habitChange === "high") {
    document.getElementById('hoy').checked = true;
  }
})
</script>

<template>
  <div class="main">
    <div class="flex-container3">
      <div class="box">
        <ul class="question">
          <div class="text">
            <h3>Hvor villig er du til å endre dine vaner?</h3>
          </div>
          <div>
            <input  type="radio" id="lav"
                    name="grad" value="lav" @click="$emit('habitChange', 'low')">
            <label class="radio" for="lav">Litt</label>
          </div>

          <div>
            <input type="radio" id="middels"
                   name="grad" value="middels" @click="$emit('habitChange', 'medium')">
            <label class="radio" for="middels">Middels</label>
          </div>
          <div>
            <input type="radio" id="hoy"
                   name="grad" value="hoy" @click="$emit('habitChange', 'high')">
            <label class="radio" for="hoy">Veldig</label>
          </div>
        </ul>

      </div>
    </div>
  </div>
</template>

<style scoped>
.flex-container3 {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  width: 100vw;
}

ul{
  justify-content: space-between;
  padding-top: 10px;
}

.box{
  background-color: white;
  min-height: 30vh;
  max-width: 350px;
  padding: 10px;
  border-radius: 30px;

}

button{
  text-align: center;
  min-width: 20vw;
  margin-bottom: 10px;
}

h3 {
  padding-right: 30px;
}

label{
  display: flex;
  align-items: center;
  padding: 0.375em 0.75em 0.375em 0.375em;
}
button:hover{
  background-color: #89a76c !important;
}

[type="radio"] {
  display: none;

  &:checked + .radio:after {
    display: block;
  }

  &:checked + .radio:before {
    box-shadow: inset 0 0 5px #A4CD92;
  }
}

.radio {
  padding-left: 40px;
  display: block;
  height: 40px;
  line-height: 40px;
  position: relative;

  &:before {
    content: "";
    display: block;
    width: 30px;
    height: 30px;
    position: absolute;
    border: 1px solid #ccc;
    border-radius: 50%;
    left: 5px;
    top: 50%;
    margin-top: -15px;
  }

  &:after {
    content: "";
    background: #A4CD92;
    display: none;
    width: 16px;
    height: 16px;
    position: absolute;
    border: 1px solid #ccc;
    border-radius: 50%;
    left: 12px;
    top: 50%;
    margin-top: -8px;
  }
}
@media screen and (max-width: 700px) {
  .box {
    max-width: 250px;
    padding-top: 0;

  }
}
.main{
  height: auto;
}
</style>
