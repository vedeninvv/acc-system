<template>
  <v-form v-if="isShowContractForm"
          v-model="isValidContractForm"
          @submit.prevent="saveContract">
    <v-row>
      <v-text-field v-model.trim="contractForm.title"
                    label="Название договора"
                    :rules="[v => (!!v.trim() || 'Обязательно')]"
      ></v-text-field>
    </v-row>

    <v-row>
      <app-contract-type-select v-model="contractForm.contractType"
                                :nullable="false"
                                label="Тип">
      </app-contract-type-select>
    </v-row>

    <v-row>
      <app-ruble-input v-model="contractForm.sum"
                       label="Сумма">
      </app-ruble-input>
    </v-row>

    <v-row>
      <app-date-picker-in-menu v-model="contractForm.planStartDate"
                               label="Плановая дата начала">
      </app-date-picker-in-menu>
    </v-row>
    <v-row>
      <app-date-picker-in-menu v-model="contractForm.planEndDate"
                               label="Плановая дата конца">
      </app-date-picker-in-menu>
    </v-row>
    <v-row>
      <app-date-picker-in-menu v-model="contractForm.factStartDate"
                               label="Фактическая дата начала">
      </app-date-picker-in-menu>
    </v-row>
    <v-row>
      <app-date-picker-in-menu v-model="contractForm.factEndDate"
                               label="Фактическа дата конца">
      </app-date-picker-in-menu>
    </v-row>

    <v-row>
      <v-spacer></v-spacer>
      <v-btn
          :class="{'disable-el': !isValidContractForm}"
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
import {apiCreateContract, apiGetContractById, apiUpdateContractById} from "@/shared/services/contractService";

export default {
  name: "ContractForm",
  components: {AppContractTypeSelect, AppRubleInput, AppDatePickerInMenu},
  props: {
    contractId: Number
  },

  created() {
    if (!this.isNewContract) {
      this.getContract()
    }
  },

  data: () => ({
    contractLoaded: false,

    isValidContractForm: true,
    contractForm: {
      title: "",
      contractType: null,
      sum: null,
      planStartDate: null,
      planEndDate: null,
      factStartDate: null,
      factEndDate: null,
    },
  }),

  computed: {
    isNewContract() {
      return this.contractId == null
    },

    isShowContractForm() {
      return this.isNewContract || this.contractLoaded
    }
  },

  methods: {
    async getContract() {
      let contract = await apiGetContractById(this.contractId)

      this.contractForm.title = contract.title
      this.contractForm.contractType = contract.contractType
      this.contractForm.sum = contract.sum
      this.contractForm.planStartDate = contract.planStartDate
      this.contractForm.planEndDate = contract.planEndDate
      this.contractForm.factStartDate = contract.factStartDate
      this.contractForm.factEndDate = contract.factEndDate

      this.contractLoaded = true
    },

    saveContract() {
      if (!this.isValidContractForm) {
        return
      }
      if (this.isNewContract) {
        this.createContract()
      } else {
        this.updateContract()
      }
    },

    createContract() {
      apiCreateContract(this.contractForm).then((contract) => {
        this.$router.push(`/contracts/${contract.id}`).then(() => {
              this.$router.go()
            }
        )
      })
    },

    updateContract() {
      apiUpdateContractById(this.contractId, this.contractForm)
    },
  }
}
</script>

<style scoped>

</style>