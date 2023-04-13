<template>
  <v-form v-model="isValidContractSearchForm"
          @submit.prevent="getContracts"
  >
    <v-row justify="center">
      <v-col cols="6">
        <v-text-field
            v-model="searchFormContracts.title"
            label="Название договора"
        ></v-text-field>
      </v-col>
    </v-row>

    <v-row justify="center">
      <v-col cols="3">
        <app-ruble-input
            v-model="searchFormContracts.minSum"
            label="Минимальная сумма"
        ></app-ruble-input>
      </v-col>

      <v-col cols="3">
        <app-ruble-input
            v-model="searchFormContracts.maxSum"
            label="Максимальная сумма"
        ></app-ruble-input>
      </v-col>
    </v-row>

    <v-row justify="center">
      <v-col cols="3">
        <app-range-date-picker
            v-model="searchFormContracts.dates"
            label="Период"
        ></app-range-date-picker>
      </v-col>

      <v-col cols="3">
        <app-contract-type-select
            v-model="searchFormContracts.contractType"
            label="Тип контракта"
        ></app-contract-type-select>
      </v-col>
    </v-row>

    <v-row>
      <v-spacer></v-spacer>
      <v-col cols="3">
        <v-btn
            :class="{'disable-el': !isValidContractSearchForm}"
            color="blue" dark
            type="submit">Поиск
        </v-btn>
      </v-col>
    </v-row>

    <v-container>
      <v-row justify="center">
        <app-paging-table-crud label="Договоры"
                               :headers="headersForContractsHeaders"
                               :items="contractsForTable"
                               :total-pages="totalPages"
                               @post="$router.push('contracts/new')"
                               @put="updateContract"
                               @delete="deleteContract"
                               @pageSelected="changePage">
        </app-paging-table-crud>
      </v-row>
    </v-container>
  </v-form>
</template>

<script>
import AppPagingTableCrud from "@/components/AppPagingTableCrud";
import AppRubleInput from "@/components/AppRubleInput";
import AppContractTypeSelect from "@/components/AppContractTypeSelect";
import AppRangeDatePicker from "@/components/AppRangeDatePicker";
import {apiDeleteContract, apiGetManagingContracts} from "@/shared/services/contractService";

export default {
  name: "ContractSearchWithOutputTable",
  components: {AppPagingTableCrud, AppRubleInput, AppContractTypeSelect, AppRangeDatePicker},

  created() {
    this.getContracts()
  },

  data: () => ({
    isValidContractSearchForm: true,
    searchFormContracts: {
      title: null,
      minSum: null,
      maxSum: null,
      dates: null,
      contractType: null,
    },

    page: 1,
    pageSize: 10,
    totalPages: null,

    contracts: null,
    headersForContractsHeaders: [
      "ID",
      "Нзавание",
      "Тип",
      "Сумма",
      "План. дата начала",
      "План. дата конца",
      "Факт. дата начала",
      "Факт. дата конца"
    ]
  }),

  computed: {
    contractsForTable() {
      let data = []
      if (this.contracts != null) {
        for (let i = 0; i < this.contracts.length; i++) {
          data.push({
            id: this.contracts[i].id,
            title: this.contracts[i].title,
            contractType: this.contracts[i].contractType,
            sum: this.contracts[i].sum,
            planStartDate: this.contracts[i].planStartDate,
            planEndDate: this.contracts[i].planEndDate,
            factStartDate: this.contracts[i].factStartDate,
            factEndDate: this.contracts[i].factEndDate
          })
        }
      }
      return data
    }
  },

  methods: {
    async getContracts() {
      if (!this.isValidContractSearchForm) {
        return
      }
      let startPeriod = null
      let endPeriod = null
      if (this.searchFormContracts.dates != null) {
        startPeriod = this.searchFormContracts.dates[0] || null
        endPeriod = this.searchFormContracts.dates[1] || null
      }

      let contractsPages = await apiGetManagingContracts({
        title: this.searchFormContracts.title,
        contractType: this.searchFormContracts.contractType,
        minSum: this.searchFormContracts.minSum,
        maxSum: this.searchFormContracts.maxSum,
        startPeriod: startPeriod,
        endPeriod: endPeriod,
        page: this.page - 1,
        size: this.pageSize,
      })

      this.contracts = contractsPages.content
      this.totalPages = contractsPages.totalPages
    },

    updateContract(id) {
      this.$router.push(`/contracts/${id}`)
    },

    deleteContract(id) {
      apiDeleteContract(id).then(() => {
        this.contracts = this.contracts.filter((contract) => {
          return contract.id !== id
        })
        this.getContracts()
      })
    },

    changePage(page) {
      this.page = page
      this.getContracts()
    }
  }
}
</script>

<style scoped>

</style>