<template>
  <v-form v-model="isValidSearchFormCounterpartyContracts"
          @submit.prevent="getCounterpartyContracts"
  >
    <v-container>

      <v-row justify="center">
        <v-col cols="6">
          <v-text-field
              v-model.trim="searchFormCounterpartyContracts.title"
              label="Название этапа"
          ></v-text-field>
        </v-col>
      </v-row>

      <v-row justify="center">
        <v-col cols="3">
          <app-ruble-input
              v-model="searchFormCounterpartyContracts.minSum"
              label="Минимальная сумма"
          ></app-ruble-input>
        </v-col>

        <v-col cols="3">
          <app-ruble-input
              v-model="searchFormCounterpartyContracts.maxSum"
              label="Максимальная сумма"
          ></app-ruble-input>
        </v-col>
      </v-row>

      <v-row justify="center">
        <v-col cols="3">
          <app-range-date-picker
              v-model="searchFormCounterpartyContracts.dates"
              label="Период"
          ></app-range-date-picker>
        </v-col>
        <v-col cols="3">
          <app-contract-type-select
              v-model="searchFormCounterpartyContracts.contractType"
              label="Тип контракта"
          ></app-contract-type-select>
        </v-col>
      </v-row>

      <v-row justify="center">
        <v-col cols="6">
          <app-counterparty-select v-model="searchFormCounterpartyContracts.counterpartyId"></app-counterparty-select>
        </v-col>
      </v-row>

      <v-row justify="center">
        <v-col cols="6">
          <v-spacer></v-spacer>
        </v-col>
        <v-col cols="2">
          <v-btn
              :class="{'disable-el': !isValidSearchFormCounterpartyContracts}"
              color="blue" dark
              type="submit">Поиск
          </v-btn>
        </v-col>
      </v-row>

      <app-paging-table-crud label="Договоры с контрагентами"
                             :headers="CounterpartyContractsHeaders"
                             :items="counterpartyContractsForTable"
                             :total-pages="totalPages"
                             :initPage="page"
                             @post="$router.push(`/contracts/${contractId}/counterparty-contracts/new`)"
                             @put="updateCounterpartyContract"
                             @delete="deleteCounterpartyContract"
                             @pageSelected="changePage"/>

    </v-container>
  </v-form>
</template>

<script>
import AppRangeDatePicker from "@/components/AppRangeDatePicker";
import AppRubleInput from "@/components/AppRubleInput";
import AppPagingTableCrud from "@/components/AppPagingTableCrud";
import AppCounterpartySelect from "@/components/AppCounterpartySelect";
import AppContractTypeSelect from "@/components/AppContractTypeSelect";
import {
  apiDeleteCounterpartyContractById,
  apiGetAllCounterpartyContracts
} from "@/shared/services/counterpartyContractService";

export default {
  name: "CounterpartyContractSearchWithOutputTable",
  components: {AppCounterpartySelect, AppRangeDatePicker, AppRubleInput, AppPagingTableCrud, AppContractTypeSelect},

  created() {
    this.page = this.initPage
    this.getCounterpartyContracts()
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
    counterpartyContractPages: null,

    isValidSearchFormCounterpartyContracts: true,
    searchFormCounterpartyContracts: {
      title: "",
      minSum: null,
      maxSum: null,
      dates: null,
      contractType: null,
      counterpartyId: null
    },

    CounterpartyContractsHeaders: [
      "ID",
      "Название",
      "Тип",
      "Контрагент",
      "Сумма",
      "План. дата начала",
      "План. дата конца",
      "Факт. дата начала",
      "Факт. дата конца"
    ],
  }),

  computed: {
    totalPages() {
      if (!this.counterpartyContractPages) {
        return null
      }
      return this.counterpartyContractPages.totalPages
    },

    counterpartyContractsForTable() {
      if (this.counterpartyContractPages) {
        let counterpartyContracts = []
        for (let index in this.counterpartyContractPages.content) {
          counterpartyContracts.push({
            id: this.counterpartyContractPages.content[index].id,
            title: this.counterpartyContractPages.content[index].title,
            contractType: this.counterpartyContractPages.content[index].contractType,
            counterparty: this.counterpartyContractPages.content[index].counterpartyId,
            sum: this.counterpartyContractPages.content[index].sum,
            planStartDate: this.counterpartyContractPages.content[index].planStartDate,
            planEndDate: this.counterpartyContractPages.content[index].planEndDate,
            factStartDate: this.counterpartyContractPages.content[index].factStartDate,
            factEndDate: this.counterpartyContractPages.content[index].factEndDate
          })
        }

        return counterpartyContracts
      } else {
        return []
      }
    }
  },

  methods: {
    async getCounterpartyContracts() {
      if (this.isValidSearchFormCounterpartyContracts) {
        let startPeriod = null
        let endPeriod = null
        if (this.searchFormCounterpartyContracts.dates != null) {
          startPeriod = this.searchFormCounterpartyContracts.dates[0] || null
          endPeriod = this.searchFormCounterpartyContracts.dates[1] || null
        }

        this.counterpartyContractPages = await apiGetAllCounterpartyContracts(this.contractId, {
          title: this.searchFormCounterpartyContracts.title,
          contractType: this.searchFormCounterpartyContracts.contractType,
          counterpartyId: this.searchFormCounterpartyContracts.counterpartyId,
          minSum: this.searchFormCounterpartyContracts.minSum,
          maxSum: this.searchFormCounterpartyContracts.maxSum,
          startPeriod: startPeriod,
          endPeriod: endPeriod,
          page: this.page - 1,
          size: this.pageSize,
        })
      }
    },

    updateCounterpartyContract(counterpartyContractId) {
      this.$router.push(`/contracts/${this.contractId}/counterparty-contracts/${counterpartyContractId}`)
    },

    deleteCounterpartyContract(counterpartyContractId) {
      apiDeleteCounterpartyContractById(this.contractId, counterpartyContractId).then(() => {
            this.counterpartyContractPages.content = this.counterpartyContractPages.content.filter((counterpartyContract) => {
              return counterpartyContract.id !== counterpartyContractId
            });
            this.getCounterpartyContracts()
          }
      )
    },

    changePage(page) {
      this.page = page
      this.getCounterpartyContracts()
    },
  }
}
</script>

<style scoped>

</style>