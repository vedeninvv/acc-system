<template>
  <v-form v-model="isValidSearchFormContractStages"
          @submit.prevent="getContractStages"
  >
    <v-container>

      <v-row justify="center">
        <v-col cols="6">
          <v-text-field
              v-model.trim="searchFormContractStages.title"
              label="Название этапа"
          ></v-text-field>
        </v-col>
      </v-row>

      <v-row justify="center">
        <v-col cols="3">
          <app-ruble-input
              v-model="searchFormContractStages.minSum"
              label="Мин. сумма договора"
          ></app-ruble-input>
        </v-col>

        <v-col cols="3">
          <app-ruble-input
              v-model="searchFormContractStages.maxSum"
              label="Макс. сумма договора"
          ></app-ruble-input>
        </v-col>
      </v-row>

      <v-row justify="center">
        <v-col cols="3">
          <app-ruble-input
              v-model="searchFormContractStages.minPlanTotalExpenses"
              label="Мин. сумма план. расходов"
          ></app-ruble-input>
        </v-col>

        <v-col cols="3">
          <app-ruble-input
              v-model="searchFormContractStages.maxPlanTotalExpenses"
              label="Макс. сумма план. расходов"
          ></app-ruble-input>
        </v-col>
      </v-row>

      <v-row justify="center">
        <v-col cols="3">
          <app-ruble-input
              v-model="searchFormContractStages.minFactTotalExpenses"
              label="Мин. сумма факт. расходов"
          ></app-ruble-input>
        </v-col>

        <v-col cols="3">
          <app-ruble-input
              v-model="searchFormContractStages.maxFactTotalExpenses"
              label="Макс. сумма факт. расходов"
          ></app-ruble-input>
        </v-col>
      </v-row>

      <v-row justify="center">
        <v-col cols="6">
          <app-range-date-picker
              v-model="searchFormContractStages.dates"
              label="Период"
          ></app-range-date-picker>
        </v-col>
      </v-row>

      <v-row justify="center">
        <v-col cols="6">
          <v-spacer></v-spacer>
        </v-col>
        <v-col cols="2">
          <v-btn
              :class="{'disable-el': !isValidSearchFormContractStages}"
              color="blue" dark
              type="submit">Поиск
          </v-btn>
        </v-col>
      </v-row>

      <app-elements-paging-table label="Этапы договора"
                                 :headers="contractStagesHeaders"
                                 :items="contractStagesForTable"
                                 :total-pages="totalPages"
                                 :initPage="page"
                                 @post="$router.push(`/contracts/${contractId}/stages/new`)"
                                 @put="updateContractStage"
                                 @delete="deleteContractStage"
                                 @pageSelected="changePage"/>

    </v-container>
  </v-form>
</template>

<script>
import {apiDeleteContractStageById, apiGetAllContractStagesByContractId} from "@/shared/services/contractStageService";
import AppRangeDatePicker from "@/components/AppRangeDatePicker";
import AppRubleInput from "@/components/AppRubleInput";
import AppElementsPagingTable from "@/components/AppElementsPagingTable";

export default {
  name: "ContractStageSearchWithOutputTable",
  components: {AppRangeDatePicker, AppRubleInput, AppElementsPagingTable},

  created() {
    this.page = this.initPage
    this.getContractStages()
  },

  props: {
    initPage: {
      type: Number,
      default: 1
    },
    pageSize: {
      type: Number,
      default: 10
    },
    contractId: Number
  },

  data: () => ({
    page: null,
    contractStagePages: null,

    isValidSearchFormContractStages: true,
    searchFormContractStages: {
      title: "",
      minSum: null,
      maxSum: null,
      minPlanTotalExpenses: null,
      maxPlanTotalExpenses: null,
      minFactTotalExpenses: null,
      maxFactTotalExpenses: null,
      dates: null,
      contractType: null,
    },

    contractStagesHeaders: [
      "ID",
      "Название",
      "Сумма",
      "Сумма план. расходов",
      "Сумма факт. расходов",
      "План. дата начала",
      "План. дата конца",
      "Факт. дата начала",
      "Факт. дата конца"
    ],
  }),

  computed: {
    totalPages() {
      if (!this.contractStagePages) {
        return null
      }
      return this.contractStagePages.totalPages
    },

    contractStagesForTable() {
      if (this.contractStagePages) {
        let contractStages = []
        for (let index in this.contractStagePages.content) {
          contractStages.push({
            id: this.contractStagePages.content[index].id,
            title: this.contractStagePages.content[index].title,
            sum: this.contractStagePages.content[index].sum,
            planTotalExpenses: this.contractStagePages.content[index].planTotalExpenses,
            factTotalExpenses: this.contractStagePages.content[index].factTotalExpenses,
            planStartDate: this.contractStagePages.content[index].planStartDate,
            planEndDate: this.contractStagePages.content[index].planEndDate,
            factStartDate: this.contractStagePages.content[index].factStartDate,
            factEndDate: this.contractStagePages.content[index].factEndDate
          })
        }

        return contractStages
      } else {
        return []
      }
    }
  },

  methods: {
    async getContractStages() {
      if (this.isValidSearchFormContractStages) {
        let startPeriod = null
        let endPeriod = null
        if (this.searchFormContractStages.dates != null) {
          startPeriod = this.searchFormContractStages.dates[0] || null
          endPeriod = this.searchFormContractStages.dates[1] || null
        }

        this.contractStagePages = await apiGetAllContractStagesByContractId(this.contractId, {
          title: this.searchFormContractStages.title,
          contractType: this.searchFormContractStages.contractType,
          minSum: this.searchFormContractStages.minSum,
          maxSum: this.searchFormContractStages.maxSum,
          minPlanTotalExpenses: this.searchFormContractStages.minPlanTotalExpenses,
          maxPlanTotalExpenses: this.searchFormContractStages.maxPlanTotalExpenses,
          minFactTotalExpenses: this.searchFormContractStages.minFactTotalExpenses,
          maxFactTotalExpenses: this.searchFormContractStages.maxFactTotalExpenses,
          startPeriod: startPeriod,
          endPeriod: endPeriod,
          page: this.page - 1,
          size: this.pageSize,
        })
      }
    },

    updateContractStage(contractStageId) {
      this.$router.push(`/contracts/${this.contractId}/stages/${contractStageId}`)
    },

    deleteContractStage(contractStageId) {
      apiDeleteContractStageById(this.contractId, contractStageId).then(() => {
            this.contractStagePages.content = this.contractStagePages.content.filter((contractStage) => {
              return contractStage.id !== contractStageId
            })
            this.getContractStages()
          }
      )
    },

    changePage(page) {
      this.page = page
      this.getContractStages()
    },
  }
}
</script>

<style scoped>

</style>