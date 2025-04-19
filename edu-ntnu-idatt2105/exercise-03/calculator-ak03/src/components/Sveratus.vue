<template>
  <div class="sveratus-container">
    <div class="sveratus">
      <div>
        <h1 class="h1-title">Sveratus Rexgloriosus Frognati Haugensis Sanctus</h1>
        <p class="p-center">
          Profetus Apostlus Ascensus Percutere.
          <br>
          Sanctitas Sua te observat dum speciale tempus solus agis post tabernaculum.
          <br>
          <br>
          <img src="/src/assets/sveratus-01.png"
               class="img-sanctus-sveratus"
               alt="Sveratus Rexgloriosus Frognerius Haugensis Sanctus">
          <br>
          In His name shall the View be Secondary to the Calculatory Divine, and thus appear Below the Projectorus ex Infinitus!
          <br>

          <br>
          <br>

          <br>

        </p>
      </div>
    </div>
  </div>
</template>

<style scoped>

.img-sanctus-sveratus {
  display: block;
  margin: 0 auto;
  width: 500px;
  height: auto;
  border-radius: 8%;
  border: 8px solid goldenrod;
}

.h1-title {
  text-align: center;
  padding-bottom: 12px;
  font-weight: bolder;
  font-size: 26px;
  color: black;
}

.p-center {
  text-align: center;
  color: black;
}

.sveratus-container {
  width: 100%;
  max-height: 90vh;
  overflow-y: auto;
  padding-top: 22px;
  margin: 0 auto;
  padding-left: 8px;
  padding-right: 8px;
  display: flex;
  background-color: rgba(215, 215, 215, 0.86);
  border: 2px solid #222222;
  align-content: flex-start;
}


p {
  font-weight: bold;
}


@media (min-width: 1024px) {
  .sveratus {
    min-height: 80vh;
    display: flex;
    align-items: flex-start;
    }
  }

</style>
<script setup lang="ts">
</script>