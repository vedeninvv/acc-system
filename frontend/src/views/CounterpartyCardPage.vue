<template>
  <v-container>
    <h1 v-if="isNewCounterparty">Новый контрагент</h1>
    <h1 v-else>№{{ counterpartyId }} Контрагент</h1>

    <v-container>
      <counterparty-form :counterparty-id="counterpartyId">
      </counterparty-form>
    </v-container>
  </v-container>
</template>

<script>
import CounterpartyForm from "@/components/CounterpartyForm";

export default {
  name: "CounterpartyCardPage",
  components: {CounterpartyForm},

  created() {
    if (this.$route.path !== "/counterparties/new") {
      this.counterpartyId = +this.$route.params.id
    }
  },

  data: () => ({
    counterpartyId: null,
  }),

  computed: {
    isNewCounterparty() {
      return this.counterpartyId == null
    }
  },
}
</script>

<style scoped>

</style>