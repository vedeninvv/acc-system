<template>
  <div>
    <v-form v-model="valid"
            @submit.prevent="findContracts"
    >
      <v-container>

        <v-row>
          <v-col cols="6">
            <v-text-field
                v-model="formContracts.title"
                label="Название договора"
            ></v-text-field>
          </v-col>
        </v-row>

        <v-row>
          <v-col cols="3">
            <app-ruble-input
                v-model="formContracts.minSum"
                label="Минимальная сумма"
            ></app-ruble-input>
          </v-col>

          <v-col cols="3">
            <app-ruble-input
                v-model="formContracts.maxSum"
                label="Максимальная сумма"
            ></app-ruble-input>
          </v-col>
        </v-row>

        <v-row>
          <v-col cols="3">
            <app-range-date-picker
                v-model="formContracts.dates"
                label="Период"
            ></app-range-date-picker>
          </v-col>

          <v-col cols="3">
            <app-contract-type-select
                v-model="formContracts.contractType"
                label="Тип контракта"
            ></app-contract-type-select>
          </v-col>
        </v-row>

        <v-row>
          <v-col cols="3">
            <v-btn
                :class="{'disable-el': !valid}"
                color="blue" dark
                type="submit">Поиск
            </v-btn>
          </v-col>
        </v-row>

      </v-container>
    </v-form>

    <v-container>
      <v-row>
        <v-spacer></v-spacer>
        <v-btn
            color="blue" dark
            @click="$router.push('contracts/new')"
        >Создать
        </v-btn>
      </v-row>
      <v-row justify="center">
        <app-elements-table :headers="headersForContractsHeaders"
                            :items="contractsForTable"
                            @put="changeContract"
                            @delete="deleteContract">
        </app-elements-table>
      </v-row>
    </v-container>

    <v-container>
      <div class="text-center"
           v-if="totalPages >= 1">
        <v-pagination
            v-model="page"
            :length="totalPages"
            @input="findContracts"
        ></v-pagination>
      </div>
      <div class="text-center"
           v-else>
        Нет данных
      </div>
    </v-container>
  </div>
</template>

<script>
import AppRangeDatePicker from "@/components/AppRangeDatePicker";
import AppContractTypeSelect from "@/components/AppContractTypeSelect";
import AppRubleInput from "@/components/AppRubleInput";
import AppElementsTable from "@/components/AppElementsTable"
import {apiDeleteContract, apiGetManagingContracts} from "@/shared/services/contractService";

export default {
  name: "ContractsPage",
  components: {AppElementsTable, AppRubleInput, AppContractTypeSelect, AppRangeDatePicker},

  data: () => ({
    valid: false,
    formContracts: {
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
    async findContracts() {
      let startPeriod = null
      let endPeriod = null
      if (this.formContracts.dates != null) {
        startPeriod = this.formContracts.dates[0] || null
        endPeriod = this.formContracts.dates[1] || null
      }

      let contractsPages = await apiGetManagingContracts({
        title: this.formContracts.title,
        contractType: this.formContracts.contractType,
        minSum: this.formContracts.minSum,
        maxSum: this.formContracts.maxSum,
        startPeriod: startPeriod,
        endPeriod: endPeriod,
        page: this.page - 1,
        size: this.pageSize,
      })

      this.contracts = contractsPages.content
      this.totalPages = contractsPages.totalPages
    },
    changeContract(id) {
      this.$router.push(`/contracts/${id}`)
    },
    deleteContract(id) {
      apiDeleteContract(id).then(this.findContracts)
    }
  }
}
</script>

<style scoped>

</style>