<template>
  <v-form v-if="isShowCounterpartyContractForm"
          v-model="isValidCounterpartyContractForm"
          @submit.prevent="saveCounterpartyContract">
    <v-row>
      <v-text-field v-model.trim="counterpartyContractForm.title"
                    label="Название договора"
                    :rules="[v => (!!v.trim() || 'Обязательно')]"
                    :loading="loading"
      ></v-text-field>
    </v-row>

    <v-row>
      <app-contract-type-select v-model="counterpartyContractForm.contractType"
                                :nullable="false"
                                label="Тип"
                                :loading="loading">
      </app-contract-type-select>
    </v-row>

    <v-row>
      <app-ruble-input v-model="counterpartyContractForm.sum"
                       label="Сумма"
                       :nullable="false"
                       :loading="loading">
      </app-ruble-input>
    </v-row>

    <v-row>
      <app-counterparty-select :nullable="false"
                               label="Контрагент"
                               v-model="counterpartyContractForm.counterpartyId"
                               :loading="loading"></app-counterparty-select>
    </v-row>

    <v-row>
      <app-date-picker-in-menu v-model="counterpartyContractForm.planStartDate"
                               label="Плановая дата начала"
                               :loading="loading">
      </app-date-picker-in-menu>
    </v-row>
    <v-row>
      <app-date-picker-in-menu v-model="counterpartyContractForm.planEndDate"
                               label="Плановая дата конца"
                               :loading="loading">
      </app-date-picker-in-menu>
    </v-row>
    <v-row>
      <app-date-picker-in-menu v-model="counterpartyContractForm.factStartDate"
                               label="Фактическая дата начала"
                               :loading="loading">
      </app-date-picker-in-menu>
    </v-row>
    <v-row>
      <app-date-picker-in-menu v-model="counterpartyContractForm.factEndDate"
                               label="Фактическа дата конца"
                               :loading="loading">
      </app-date-picker-in-menu>
    </v-row>

    <v-row>
      <v-spacer></v-spacer>
      <v-btn
          :class="{'disable-el': !isValidCounterpartyContractForm || loading}"
          color="blue" dark
          type="submit">Сохранить
      </v-btn>
    </v-row>
  </v-form>
</template>

<script>
import AppContractTypeSelect from "@/components/AppContractTypeSelect";
import AppRubleInput from "@/components/AppRubleInput";
import AppDatePickerInMenu from "@/components/AppDatePickerInMenu";
import AppCounterpartySelect from "@/components/AppCounterpartySelect";
import {
  apiCreateCounterpartyContract,
  apiGetCounterpartyContractById,
  apiUpdateCounterpartyContractById
} from "@/shared/services/counterpartyContractService";

export default {
  name: "CounterpartyContractForm",
  components: {AppCounterpartySelect, AppContractTypeSelect, AppRubleInput, AppDatePickerInMenu},
  props: {
    contractId: {
      type: Number,
      required: true,
    },
    counterpartyContractId: Number
  },

  created() {
    if (!this.isNewCounterpartyContract) {
      this.getCounterpartyContract()
    }
  },

  data: () => ({
    counterpartyContractLoaded: false,
    loading: false,

    isValidCounterpartyContractForm: true,
    counterpartyContractForm: {
      title: "",
      contractType: null,
      sum: null,
      counterpartyId: null,
      planStartDate: null,
      planEndDate: null,
      factStartDate: null,
      factEndDate: null,
    },
  }),

  computed: {
    isNewCounterpartyContract() {
      return this.counterpartyContractId == null
    },

    isShowCounterpartyContractForm() {
      return this.isNewCounterpartyContract || this.counterpartyContractLoaded
    }
  },

  methods: {
    async getCounterpartyContract() {
      let counterpartyContract = await apiGetCounterpartyContractById(this.contractId, this.counterpartyContractId)

      this.counterpartyContractForm.title = counterpartyContract.title
      this.counterpartyContractForm.contractType = counterpartyContract.contractType
      this.counterpartyContractForm.sum = counterpartyContract.sum
      this.counterpartyContractForm.counterpartyId = counterpartyContract.counterparty.id
      this.counterpartyContractForm.planStartDate = counterpartyContract.planStartDate
      this.counterpartyContractForm.planEndDate = counterpartyContract.planEndDate
      this.counterpartyContractForm.factStartDate = counterpartyContract.factStartDate
      this.counterpartyContractForm.factEndDate = counterpartyContract.factEndDate

      this.counterpartyContractLoaded = true
    },

    saveCounterpartyContract() {
      if (!this.isValidCounterpartyContractForm) {
        return
      }
      this.loading = true
      if (this.isNewCounterpartyContract) {
        this.createCounterpartyContract()
      } else {
        this.updateCounterpartyContract()
      }
    },

    createCounterpartyContract() {
      apiCreateCounterpartyContract(this.contractId, this.counterpartyContractForm).then((counterpartyContract) => {
            this.loading = false
            this.$router.push(`/contracts/${counterpartyContract.contractId}`)
          }
      )
    },

    updateCounterpartyContract() {
      apiUpdateCounterpartyContractById(this.contractId, this.counterpartyContractId, this.counterpartyContractForm)
          .then((counterpartyContract) => {
            this.loading = false
            this.$router.push(`/contracts/${counterpartyContract.contractId}`)
          })
    },
  }
}
</script>

<style scoped>

</style>