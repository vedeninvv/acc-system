<template>
  <v-form v-if="isShowContractStageForm"
          v-model="isValidContractStageForm"
          @submit.prevent="saveContractStage">
    <v-container>
      <v-row>
        <v-text-field v-model.trim="contractStageForm.title"
                      label="Название этапа"
                      :rules="[v => (!!v.trim() || 'Обязательно')]"
                      :loading="loading"
        ></v-text-field>
      </v-row>

      <v-row>
        <app-ruble-input v-model="contractStageForm.sum"
                         label="Сумма"
                         :nullable="false"
                         :loading="loading">
        </app-ruble-input>
      </v-row>

      <v-row>
        <app-date-picker-in-menu v-model="contractStageForm.planStartDate"
                                 label="Плановая дата начала"
                                 :loading="loading">
        </app-date-picker-in-menu>
      </v-row>
      <v-row>
        <app-date-picker-in-menu v-model="contractStageForm.planEndDate"
                                 label="Плановая дата конца"
                                 :loading="loading">
        </app-date-picker-in-menu>
      </v-row>
      <v-row>
        <app-date-picker-in-menu v-model="contractStageForm.factStartDate"
                                 label="Фактическая дата начала"
                                 :loading="loading">
        </app-date-picker-in-menu>
      </v-row>
      <v-row>
        <app-date-picker-in-menu v-model="contractStageForm.factEndDate"
                                 label="Фактическа дата конца"
                                 :loading="loading">
        </app-date-picker-in-menu>
      </v-row>
    </v-container>

    <v-container>
      <v-row>
        <h3>Список расходов</h3>
      </v-row>
      <v-row justify="center">
        <v-col>
          <v-card v-for="(expense, index) in contractStageForm.expenses"
                  :key="index"
                  class="mb-4">
            <v-card-text>
              <v-row>
                <v-col cols="4">
                  <v-text-field v-model.trim="expense.title"
                                label="Название вида расхода"
                                :rules="[v => (!!v.trim() || 'Обязательно')]"
                                :loading="loading"
                  ></v-text-field>
                </v-col>
                <v-col cols="4">
                  <app-ruble-input label="Планируемый расход"
                                   v-model="expense.planAmount"
                                   :nullable="false"
                                   :loading="loading">
                  </app-ruble-input>
                </v-col>
                <v-col cols="4">
                  <app-ruble-input label="Фактический расход"
                                   v-model="expense.factAmount"
                                   :nullable="false"
                                   :loading="loading">
                  </app-ruble-input>
                </v-col>
              </v-row>
            </v-card-text>
            <v-card-actions>
              <v-row>
                <v-col align="right">
                  <v-btn color="error"
                         @click="deleteExpense(index)"
                         type="button">Удалить
                  </v-btn>
                </v-col>
              </v-row>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>

      <v-row>
        <v-col align="right">
          <v-btn
              @click="addNewExpense"
              :class="{'disable-el': !isValidContractStageForm}"
              color="green" dark
              type="button">Добавить расход
          </v-btn>
        </v-col>
      </v-row>
    </v-container>

    <v-container>
      <v-row>
        <v-col align="right">
          <v-btn
              :class="{'disable-el': !isValidContractStageForm}"
              color="blue" dark
              type="submit">Сохранить
          </v-btn>
        </v-col>
      </v-row>
    </v-container>
  </v-form>
</template>

<script>
import AppRubleInput from "@/components/AppRubleInput";
import AppDatePickerInMenu from "@/components/AppDatePickerInMenu";
import {
  apiCreateContractStage,
  apiGetContractStageById,
  apiUpdateContractStageById
} from "@/shared/services/contractStageService";

export default {
  name: "ContractStageForm",
  components: {AppRubleInput, AppDatePickerInMenu},
  props: {
    contractId: {
      type: Number,
      required: true,
    },
    contractStageId: Number
  },

  created() {
    if (!this.isNewContractStage) {
      this.getContractStage()
    }
  },

  data: () => ({
    ContractStageLoaded: false,
    loading: false,

    isValidContractStageForm: true,
    contractStageForm: {
      title: "",
      sum: null,
      planStartDate: null,
      planEndDate: null,
      factStartDate: null,
      factEndDate: null,
      expenses: []
    },
  }),

  computed: {
    isNewContractStage() {
      return this.contractStageId == null
    },

    isShowContractStageForm() {
      return this.isNewContractStage || this.ContractStageLoaded
    }
  },

  methods: {
    deleteExpense(index) {
      this.contractStageForm.expenses.splice(index, 1)
    },

    addNewExpense() {
      this.contractStageForm.expenses.push({
        title: "",
        planAmount: null,
        factAmount: null,
      })
    },

    async getContractStage() {
      let counterpartyContract = await apiGetContractStageById(this.contractId, this.contractStageId)

      this.contractStageForm.title = counterpartyContract.title
      this.contractStageForm.sum = counterpartyContract.sum
      this.contractStageForm.expenses = counterpartyContract.expenses
      this.contractStageForm.planStartDate = counterpartyContract.planStartDate
      this.contractStageForm.planEndDate = counterpartyContract.planEndDate
      this.contractStageForm.factStartDate = counterpartyContract.factStartDate
      this.contractStageForm.factEndDate = counterpartyContract.factEndDate

      this.ContractStageLoaded = true
    },

    saveContractStage() {
      if (!this.isValidContractStageForm) {
        return
      }
      this.loading = true
      if (this.isNewContractStage) {
        this.createContractStage()
      } else {
        this.updateContractStage()
      }
    },

    createContractStage() {
      apiCreateContractStage(this.contractId, this.contractStageForm).then((contractStage) => {
            this.loading = false
            this.$router.push(`/contracts/${contractStage.contractId}`)
          }
      )
    },

    updateContractStage() {
      apiUpdateContractStageById(this.contractId, this.contractStageId, this.contractStageForm)
          .then((contractStage) => {
            this.loading = false
            this.$router.push(`/contracts/${contractStage.contractId}`)
          })
    },
  }
}
</script>

<style scoped>

</style>