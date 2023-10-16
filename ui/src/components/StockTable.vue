<script setup lang="ts">
import { useStockStore } from '@/stores/stockStore';
import { storeToRefs } from 'pinia';

const { monthlySummaries, numberOfSummaries, stockId, fetchError } = storeToRefs(useStockStore());

</script>

<template>
  <div class="page">
    <h1 class="title">Stock Summary</h1>
    <div class="stock-id-panel">
      <input v-model="stockId" class="stock-id-input" placeholder="Query Stock ID" />
    </div>
    <div v-if="numberOfSummaries === 0" class="no-stock">
      No stock found for ID: {{  stockId }}
    </div>
    <div class="error">
      {{  fetchError }}
    </div>
    <div class="summary-table">
      <table v-if="numberOfSummaries > 0 && !fetchError">
        <tr class="summary-row summary-header">
          <th class="summary-col">
            Month
          </th>
          <th class="summary-col">
            Average Rating
          </th>
          
          <th class="summary-col">
            Final Head
          </th>
          <th class="summary-col">
            Final Weight
          </th>
          <th class="summary-col">
            Final Price
          </th>
          <th class="summary-col">
            Number of Updates
          </th>
          <th class="summary-col">
            Month Head Change
          </th>
        </tr >
        <tr v-for="summary in monthlySummaries?.summaries" class="summary-row">
          <td class="summary-col">
            {{  summary.month }}
          </td>
          <td class="summary-col">
            {{  summary.averageMonthlyRating }}
          </td>
          
          <td class="summary-col">
            {{  summary.finalReadings.head }}
          </td>
          <td class="summary-col">
            {{  summary.finalReadings.weight }} kg
          </td>
          <td class="summary-col">
            ${{ summary.finalReadings.price }}
          </td>
          <td class="summary-col">
            {{  summary.recordCount }}
          </td>
          <td class="summary-col">
            {{  summary.headChange }}
          </td>
        </tr>
      </table>
    </div>

    
  </div>
</template>

<style scoped>
h1 {
  font-weight: 500;
  font-size: 2.6rem;
  position: relative;
  top: -10px;
  text-align: center;
}

table {
  min-width: 800px;
  border-collapse: collapse;
}

.page {
  overflow: hidden;
}

.stock-id-input {
  padding: 5px;
  margin-bottom: 15px;
}
.stock-id-panel {
  min-width: 800px;
}

.summary-table {
  height: 500px;
  overflow: auto;
}

.summary-header .summary-col {
  font-weight: bold;
}

.summary-row {
  border-bottom: 1px solid #ddd;
}

.summary-col {
  border: 1px solid #ddd;
  padding: 10px;
}


.error {
  color: crimson;
}

@media (min-width: 1024px) {
  h1 {
    text-align: left;
  }
}

</style>