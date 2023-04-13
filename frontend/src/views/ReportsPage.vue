<template>
  <v-container>
    <h1>Отчеты</h1>

    <v-container>
      <reports-panels>
      </reports-panels>
    </v-container>
  </v-container>
</template>

<script>
import ReportsPanels from "@/components/ReportsPanels";

export default {
  name: "ReportsPage",
  components: {ReportsPanels}
}
</script>

<style scoped>

</style>