<script setup lang="ts">
import StockTable from './components/StockTable.vue'
</script>

<template>
  <StockTable />

</template>

<style scoped>
header {
  line-height: 1.5;
  max-height: 100vh;
}

/* @media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

} */
</style>
