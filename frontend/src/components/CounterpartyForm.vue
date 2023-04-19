<template>
  <v-form v-if="isShowCounterpartyForm"
          v-model="isValidCounterpartyForm"
          @submit.prevent="saveCounterparty">
    <v-row>
      <v-col>
        <v-alert
            v-model="isShowCounterpartyExistAlert"
            dismissible
            type="error"
        >Контрагент с введенными данными уже существует. Название или ИНН уже существуют.
        </v-alert>
      </v-col>
    </v-row>

    <v-row no-gutters>
      <v-col>
        <v-text-field v-model.trim="counterpartyForm.title"
                      label="Название"
                      :rules="[v => (!!v.trim() || 'Обязательно')]"
                      :loading="loading"
        ></v-text-field>
      </v-col>
    </v-row>

    <v-row no-gutters>
      <v-col>
        <v-text-field v-model.trim="counterpartyForm.address"
                      label="Адрес"
                      :rules="[v => (!!v.trim() || 'Обязательно')]"
                      :loading="loading"
        ></v-text-field>
      </v-col>
    </v-row>

    <v-row no-gutters>
      <v-col>
        <app-inn-input v-model="counterpartyForm.inn"
                       label="ИНН"
                       :nullable="false"
                       :loading="loading"
        >
        </app-inn-input>
      </v-col>
    </v-row>

    <v-row>
      <v-spacer></v-spacer>
      <v-btn
          :class="{'disable-el': !isValidCounterpartyForm || loading}"
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
    loading: false,
    counterpartyLoaded: false,
    isShowCounterpartyExistAlert: false,

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
      this.loading = true
      if (this.isNewCounterparty) {
        this.createCounterparty()
      } else {
        this.updateCounterpartyContract()
      }
    },

    createCounterparty() {
      this.isShowCounterpartyExistAlert = false
      apiCreateCounterparty(this.counterpartyForm)
          .then(() => {
            this.loading = false
            this.$router.push(`/counterparties`)
          })
          .catch(() => {
            this.loading = false
            this.isShowCounterpartyExistAlert = true
          })
    },

    updateCounterpartyContract() {
      this.isShowCounterpartyExistAlert = false
      apiUpdateCounterpartyById(this.counterpartyId, this.counterpartyForm)
          .then(() => {
            this.loading = false
            this.$router.push(`/counterparties`)
          })
          .catch(() => {
            this.loading = false
            this.isShowCounterpartyExistAlert = true
          })
    },
  }
}
</script>

<style scoped>

</style>