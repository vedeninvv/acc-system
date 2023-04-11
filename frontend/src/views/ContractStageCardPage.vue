<template>
  <v-container>
    <h1 v-if="isNewContractStage">Новый этап договора</h1>
    <h1 v-else>Этап договора №{{ contractStageId }}</h1>
    <v-container>
      <contract-stage-form :contract-id="contractId"
                           :contract-stage-id="contractStageId">
      </contract-stage-form>
    </v-container>
  </v-container>
</template>

<script>
import ContractStageForm from "@/components/ContractStageForm";

export default {
  name: "ContractStageCardPage",
  components: {ContractStageForm},

  created() {
    this.contractId = +this.$route.params.contractId

    let urlContractStageNew = new RegExp("/contracts/(\\d+)/stages/new")
    if (!urlContractStageNew.test(this.$route.path)) {
      this.contractStageId = +this.$route.params.id
    }
  },

  data: () => ({
    contractId: null,
    contractStageId: null,
  }),

  computed: {
    isNewContractStage() {
      return this.contractStageId == null
    }
  },
}
</script>

<style scoped>

</style>