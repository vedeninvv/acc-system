<template>
  <v-container>
    <h1 v-if="isNewContract">Новый договор</h1>
    <h1 v-else>Договор №{{ contractId }}</h1>
    <v-container>
      <contract-form :contract-id="contractId"/>
    </v-container>

    <v-container v-if="!isNewContract">
      <v-row>
        <v-tabs v-model="tab" centered>
          <v-tab>Этапы договора</v-tab>
          <v-tab>Договоры с контрагентом</v-tab>
        </v-tabs>
      </v-row>

      <v-row>
        <v-tabs-items v-model="tab">
          <v-tab-item>
            <v-card flat>

              <contract-stage-search-with-output-table :contract-id="contractId"/>

            </v-card>
          </v-tab-item>

          <v-tab-item>
            <v-card flat>
              договоры с контрагентами
            </v-card>
          </v-tab-item>
        </v-tabs-items>
      </v-row>
    </v-container>
  </v-container>
</template>

<script>
import ContractStageSearchWithOutputTable from "@/components/ContractStageSearchWithOutputTable";
import ContractForm from "@/components/ContractForm";

export default {
  components: {
    ContractForm,
    ContractStageSearchWithOutputTable,
  },
  created() {
    if (this.$route.path !== "/contracts/new") {
      this.contractId = +this.$route.params.id
    }
  },

  name: "ContractCardPage",
  data: () => ({
    tab: null,
    contractId: null,

    counterpartyContractPages: [],
  }),

  computed: {
    isNewContract() {
      return this.contractId == null
    }
  },
}
</script>

<style scoped>

</style>