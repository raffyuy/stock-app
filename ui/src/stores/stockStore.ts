import { computed, ref, watch } from 'vue'
import { defineStore } from 'pinia'
import type { Summaries } from '@/model/monthlySummary';

export const useStockStore = defineStore('stock', () => {

  const stockId = ref();

  const monthlySummaries = ref<Summaries>();

  const fetchError = ref();

  watch(stockId, () => {
    fetchError.value = undefined;

    if (stockId.value) {
      fetchStock();
    }
  })
  
  function fetchStock() {

    if (!stockId.value) {
      fetchError.value = 'Invalid stockId';
      return;
    }

    fetch(`http://localhost:8080/monthly-summary/${stockId.value}`)
      .then(async (response) => {
        monthlySummaries.value = await response.json() as Summaries;
      })
      .catch(error => {
        fetchError.value = error?.message;
      })
  }

  const numberOfSummaries = computed(() => 
    monthlySummaries.value?.summaries?.length
  );

  return { fetchError, fetchStock, monthlySummaries, numberOfSummaries, stockId }
})
