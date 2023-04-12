<template>
  <v-form v-if="isShowCounterpartyForm"
          v-model="isValidCounterpartyForm"
          @submit.prevent="saveCounterparty">
    <v-row>
      <v-text-field v-model.trim="counterpartyForm.title"
                    label="Название"
                    :rules="[v => (!!v.trim() || 'Обязательно')]"
      ></v-text-field>
    </v-row>

    <v-row>
      <v-text-field v-model.trim="counterpartyForm.address"
                    label="Адрес"
                    :rules="[v => (!!v.trim() || 'Обязательно')]"
      ></v-text-field>
    </v-row>

    <v-row>
      <app-inn-input v-model="counterpartyForm.inn"
                     label="ИНН"
                     :nullable="false"
      >
      </app-inn-input>
    </v-row>

    <v-row>
      <v-spacer></v-spacer>
      <v-btn
          :class="{'disable-el': !isValidCounterpartyForm}"
          color="blue" dark
          type="submit">Сохранить
      </v-btn>
    </v-row>
  </v-form>
</template>

<script>
import AppInnInput from "@/components/AppInnInput";
import {
  apiCreateCounterparty,
  apiGetCounterpartyById,
  apiUpdateCounterpartyById
} from "@/shared/services/counterpartyService";

export default {
  name: "CounterpartyForm",
  components: {AppInnInput},

  props: {
    counterpartyId: Number
  },

  created() {
    if (!this.isNewCounterparty) {
      this.getCounterparty()
    }
  },

  data: () => ({
    counterpartyLoaded: false,

    isValidCounterpartyForm: true,
    counterpartyForm: {
      title: "",
      address: "",
      inn: ""
    },
  }),

  computed: {
    isNewCounterparty() {
      return this.counterpartyId == null
    },

    isShowCounterpartyForm() {
      return this.isNewCounterparty || this.counterpartyLoaded
    }
  },

  methods: {
    async getCounterparty() {
      let counterparty = await apiGetCounterpartyById(this.counterpartyId)

      this.counterpartyForm.title = counterparty.title
      this.counterpartyForm.address = counterparty.address
      this.counterpartyForm.inn = counterparty.inn

      this.counterpartyLoaded = true
    },

    saveCounterparty() {
      if (!this.isValidCounterpartyForm) {
        return
      }
      if (this.isNewCounterparty) {
        this.createCounterparty()
      } else {
        this.updateCounterpartyContract()
      }
    },

    createCounterparty() {
      apiCreateCounterparty(this.counterpartyForm).then(() => {
            this.$router.push(`/counterparties`)
          }
      )
    },

    updateCounterpartyContract() {
      apiUpdateCounterpartyById(this.counterpartyId, this.counterpartyForm)
          .then(() => {
            this.$router.push(`/counterparties`)
          })
    },
  }
}
</script>

<style scoped>

</style>