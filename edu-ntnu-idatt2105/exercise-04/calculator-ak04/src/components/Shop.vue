<script setup lang="ts">
import { defineComponent } from 'vue';

interface Product {
  id: number;
  name: string;
  brand: string;
  description: string;
  details: string[];
  inStock: number;
  imageUrl: string;
}

export default defineComponent({
  name: 'ProductList',
  setup() {
    const products: Product[] = [
      {
        id: 1,
        name: 'Indexus Accusus',
        brand: 'Sveratus Filangis',
        description: 'The most Holiest of Appendages. The Indexus Accusus!',
        details: ['110% Authentic', 'Such Holy', 'Very Blessed', 'Collectors Edition'],
        inStock: 3,
        imageUrl: 'src/assets/shop/finger-01-premium.png',
      },
      {
        id: 2,
        name: 'Tomulus Tottus Oposus',
        brand: 'Sveratus Filangis',
        description: 'That which separates Man from Beasts.',
        details: ['Very Authentic', 'Blessed', 'Holy', 'Absolves Sin'],
        inStock: 7,
        imageUrl: 'src/assets/shop/finger-02-medium.png',
      },
      {
        id: 3,
        name: 'Nino Filangis',
        brand: 'Sveratus Filangis',
        description: 'For the Lowborn',
        details: ['Authentic', 'Blessed', 'Holy', 'May Heal Consumption'],
        inStock: 11,
        imageUrl: 'src/assets/shop/finger-03-low.png',
      },
    ];

    return { products };
  },
});
</script>

<template>
  <div class="shop-container">
  <div class="product-list">
    <div v-for="product in products" :key="product.id" class="product">
      <div class="product-header">
        <h2>{{ product.name }}</h2>
        <h4>{{ product.brand }}</h4>
      </div>
      <div class="product-body">
        <img :src="product.imageUrl" alt="" class="product-image">
        <p>{{ product.description }}</p>
        <li v-for="detail in product.details"> {{ detail }}</li>
      </div>
      <div class="product-footer">
        <span>In stock: {{ product.inStock }}</span>
      </div>
    </div>
  </div>
  </div>
</template>

<style>
.product {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 20px;
}
.product-header h2 {
  margin: 0;
}
.product-header h4 {
  margin: 5px 0;
}
.product-body {
  display: flex;
}
.product-image {
  width: 200px;
  height: 200px;
  object-fit: cover;
  margin-right: 10px;
  border-radius: 10px;
  border-color: #00bd7e;
}

.shop-container {
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
</style>
