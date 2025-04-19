<template>

  <div class="container">
    <div class="circle_with_line">
      <div class="line"></div>
      <div class="circle">
        <div class="plus">+</div>
      </div>
    </div>

    <div class="new-challenge-text">Få en ny utfordring</div>
  </div>

</template>

<style scoped>

.container {
  display: flex;
  align-items: center;
}

.circle_with_line {
  position: relative;
}

.line {
  position: absolute;
  top: 0;
  left: 50%;
  width: 0;
  transform: translateX(-50%);
  height: 50px;
  border: none;
  border-left: 5px dotted #8B8484;
}

.circle {
  display: flex;
  align-items: center;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  text-align: center;
  line-height: 250px;
  overflow: hidden;
  position: relative;
  box-shadow: 0 0 0 3px #8B8484;
  margin: 50px;
}

.plus {
  flex: auto;
  font-size: 50px;
  color: #8B8484;
}

.new-challenge-text {
  flex: 1;
  margin-left: -30px;
}


.container:hover .circle,
.container:hover .plus {
  box-shadow: 0 0 0 3px #000000;
  color: #000000;
}

.container:hover{
  cursor: pointer;
}

.container:hover .new-challenge-text {
  text-decoration: underline;

}

</style>