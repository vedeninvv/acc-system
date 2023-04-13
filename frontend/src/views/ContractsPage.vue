<template>
  <v-container>
    <v-row justify="center">
      <h1>Договоры</h1>
    </v-row>

    <v-container>
      <contract-search-with-output-table></contract-search-with-output-table>
    </v-container>

  </v-container>
</template>

<script>

import ContractSearchWithOutputTable from "@/components/ContractSearchWithOutputTable";

export default {
  name: "ContractsPage",
  components: {ContractSearchWithOutputTable},
}
</script>

<style scoped>

</style>