<template>
  <v-expansion-panels popout>
    <reports-panels-contracts-in-plan-period-panel/>
    <reports-panels-contract-stages-panel/>
  </v-expansion-panels>
</template>

<script>
import ReportsPanelsContractsInPlanPeriodPanel from "@/components/ReportsPanelsContractsInPlanPeriodPanel";
import ReportsPanelsContractStagesPanel from "@/components/ReportsPanelsContractStagesPanel";

export default {
  name: "ReportsPanels",
  components: {ReportsPanelsContractStagesPanel, ReportsPanelsContractsInPlanPeriodPanel}
}
</script>

<style scoped>

</style>