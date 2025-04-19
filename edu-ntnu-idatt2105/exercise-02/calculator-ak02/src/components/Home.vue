<template>
  <div class="home-container">
    <div class="home">
      <div>
        <h1 class="h1-title">Be Welcomed, One and All!</h1>
        <br>
        <img src="/src/assets/sveratus-crusifixus-01.png"
             class="img-home"
             alt="Cross">
        <br>
        <br>
        <h2 class="h2-title"></h2>
        <p>Strayest thou from thine God? Feareth thou for thine Soul and thy Ascendance into the Kingdom of Heaven?
          <br>
          <br>
          Feareth not, for thou art Blessed with the SFH Historicus Tapus Visus, pioneered by the most eminent of the Fideles Templarii;
          He who was martyred in honor of the Summus Evangelicus Mathematicus Maximus;
          He who boldly spoke against the haereses of Satanic Physicalism, as disseminated by the
          alluring Universitas Norvegica Intelligentiae Scientiae.
        </p>      </div>
    </div>
  </div>
</template>

<style>

.img-home {
  display: block;
  margin: 0 auto;
  width: 500px;
  height: auto;
  border-radius: 50%;
  border: 8px solid goldenrod;
}

.h1-title {
  text-align: center;
  font-weight: bolder;
  font-size: 36px;
}

.h2-title {
  text-align: center;
  font-weight: bold;
}

p {
  font-weight: bold;
}

.home-container {
  width: 100%;
  max-height: 90vh;
  min-height: 90vh;
  overflow-y: auto;
  padding-top: 22px;
  margin: 0 auto;
  padding-left: 8px;
  padding-right: 8px;
  display: flex;
  /*background-color: rgba(255, 235, 205, 0.86);*/
  background-color: rgba(215, 215, 215, 0.86);
  border: 2px solid #222222;
  align-content: flex-start;
}

@media (min-width: 1024px) {
  .home {
    min-height: 80vh;
    display: flex;
    align-items: flex-start;
  }
}
</style>

<script setup lang="ts">
</script>
