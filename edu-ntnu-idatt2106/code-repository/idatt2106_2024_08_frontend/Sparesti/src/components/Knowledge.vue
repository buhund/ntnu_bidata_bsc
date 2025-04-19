<script setup>
/**
 * A component that allows the user to select their knowledge level about economics.
 * The component includes radio buttons for low, medium, and high knowledge levels.
 * It emits the selected knowledge level to the parent component.
 */
import {onMounted} from "vue";

/**
 * Props for the component.
 * @type {Object}
 * @property {string} knowledgeLevel - The level of knowledge about economics.
 */
const props = defineProps(['knowledgeLevel'])

/**
 * Performed when the component is mounted.
 */
onMounted(() => {
  console.log(props.knowledgeLevel)
  if (props.knowledgeLevel === "low") {
    document.getElementById('low').checked = true;
  }
  if (props.knowledgeLevel === "medium") {
    document.getElementById('medium').checked = true;
  }
  if (props.knowledgeLevel === "high") {
    document.getElementById('high').checked = true;
  }
})
</script>

<template>
    <div class="main">
      <div class="flex-container3">
        <div class="box">
          <ul class="question">
            <div class="text">
              <h3>Hvordan vil du best beskrive din kunnskap om økonomi?</h3>
            </div>
            <div>
              <input  type="radio" id="low"
                      name="difficulty" value="low" @click="$emit('knowledgeLevel', 'low')">
              <label class="radio" for="low">Lav</label>
            </div>

            <div>
              <input type="radio" id="medium"
                     name="difficulty" value="medium" @click="$emit('knowledgeLevel', 'medium')">
              <label class="radio" for="medium">Middels</label>
            </div>
            <div>
              <input type="radio" id="high"
                     name="difficulty" value="high" @click="$emit('knowledgeLevel', 'high')">
              <label class="radio" for="high">Høy</label>
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
  height: 100%;
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
.main{
  height: auto;
}
@media screen and (max-width: 700px) {
  .box {
    max-width: 250px;
    padding-top: 0;

  }
}
</style>
