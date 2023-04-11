<template>
  <v-container>
    <h1 v-if="isNewCounterpartyContract">Новый договор с контрагентом</h1>
    <h1 v-else>№{{ counterpartyContractId }} Договор с контрагентом</h1>

    <v-container>
      <counterparty-contract-form :contract-id="contractId"
                                  :counterparty-contract-id="counterpartyContractId">
      </counterparty-contract-form>
    </v-container>
  </v-container>
</template>

<script>
import CounterpartyContractForm from "@/components/CounterpartyContractForm";

export default {
  name: "CounterpartyContractCardPage",
  components: {CounterpartyContractForm},

  created() {
    this.contractId = +this.$route.params.contractId

    let urlCounterpartyContractNew = new RegExp("/contracts/(\\d+)/counterparty-contracts/new")
    if (!urlCounterpartyContractNew.test(this.$route.path)) {
      this.counterpartyContractId = +this.$route.params.id
    }
  },

  data: () => ({
    contractId: null,
    counterpartyContractId: null,
  }),

  computed: {
    isNewCounterpartyContract() {
      return this.counterpartyContractId == null
    }
  },
}
</script>

<style scoped>

</style>