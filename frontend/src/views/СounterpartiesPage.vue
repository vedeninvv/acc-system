<template>
  <v-container>
    <v-row justify="center">
      <h1>Контрагенты</h1>
    </v-row>

    <v-container>
      <counterparty-search-with-output-table>
      </counterparty-search-with-output-table>

    </v-container>
  </v-container>
</template>

<script>
import CounterpartySearchWithOutputTable from "@/components/CounterpartySearchWithOutputTable";

export default {
  name: "CounterpartiesPage",
  components: {CounterpartySearchWithOutputTable}
}
</script>

<style scoped>

</style>