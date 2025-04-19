<template>
  <div class="about-container">
    <div class="about">
      <div>
      <h1 class="h1-title">Dost Thou Stray?</h1>
      <br>
      <p>
        <img alt="The Prophet professing that every day, we stray further from god."
             class="img-stray"
             src="@/assets/stray-from-god.png"/>
        <br>
        Thou art hereby Graced by the intricate Depths of the Divine Numerical Doctrine.
        The foremost scholars of Ascendant Numerology have devoutly beseeched the Holy Above,
        and Delivered unto us The Ethereal Sacrum Abacus of Oratio Infusa Teologus Numericus.
        <br>
        <br>
        So take heed, lest thou Stray into the Shadows, as claiming comprehension of Numeri Sacra is Cognitio Prohibita.
        Should Enlightenment descend upon thee, thou hast transgressed and art urged to present thyself to the Ministerium Verae Cognitionis
        to have thy Sins cleansed from thy Soul. His Vengeance is Resolute.
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

.img-stray {
  display: block;
  margin: 0 auto 2rem;
  border-radius: 8%;
  border: 8px solid darkcyan;
  width: auto;
  max-width: 80%;
  height: auto;
  transition: opacity 0.5s ease-in-out;
  opacity: 1;
}

.h1-title {
  text-align: center;
  padding-bottom: 12px;
  font-weight: bolder;
  font-size: 28px;
  color: black;
}

.about-container {
  width: 100%;
  max-height: 98vh;
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






@media (min-width: 1024px) {
  .about {
    min-height: 80vh;
    display: flex;
    align-items: flex-start;
  }
}
</style>


<script setup lang="ts">

</script>