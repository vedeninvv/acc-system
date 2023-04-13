<template>
  <v-form v-model="isValidSearchFormCounterparties"
          @submit.prevent="getCounterparties"
  >
    <v-container>

      <v-row justify="center">
        <v-col cols="6">
          <v-text-field
              v-model.trim="searchFormCounterparties.title"
              label="Название"
          ></v-text-field>
        </v-col>
      </v-row>

      <v-row justify="center">
        <v-col cols="6">
          <v-text-field
              v-model.trim="searchFormCounterparties.address"
              label="Адрес"
          ></v-text-field>
        </v-col>
      </v-row>

      <v-row justify="center">
        <v-col cols="6">
          <app-inn-input v-model="searchFormCounterparties.inn"
                         label="ИНН">
          </app-inn-input>
        </v-col>
      </v-row>

      <v-row justify="center">
        <v-col cols="6">
          <v-spacer></v-spacer>
        </v-col>
        <v-col cols="2">
          <v-btn
              :class="{'disable-el': !isValidSearchFormCounterparties}"
              color="blue" dark
              type="submit">Поиск
          </v-btn>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-alert
              v-model="isShowAlert"
              dismissible
              type="error"
          >Контрагент не может быть удален, пока существуют контракты, связанные с ним
          </v-alert>
        </v-col>
      </v-row>

      <v-row>
        <app-paging-table-crud label="Договоры с контрагентами"
                               :headers="CounterpartyHeaders"
                               :items="counterpartiesForTable"
                               :total-pages="totalPages"
                               :initPage="page"
                               @post="$router.push(`/counterparties/new`)"
                               @put="updateCounterparty"
                               @delete="deleteCounterparty"
                               @pageSelected="changePage"/>
      </v-row>
    </v-container>
  </v-form>
</template>

<script>
import {apiDeleteCounterpartyById, apiGetAllCounterparties} from "@/shared/services/counterpartyService";
import AppPagingTableCrud from "@/components/AppPagingTableCrud";
import AppInnInput from "@/components/AppInnInput";

export default {
  name: "CounterpartySearchWithOutputTable",
  components: {AppInnInput, AppPagingTableCrud},

  created() {
    this.page = this.initPage
    this.getCounterparties()
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
  },

  data: () => ({
    page: null,
    counterpartyPages: null,

    isValidSearchFormCounterparties: true,
    searchFormCounterparties: {
      title: "",
      address: "",
      inn: "",
    },

    CounterpartyHeaders: [
      "ID",
      "Название",
      "Адрес",
      "ИНН",
    ],

    isShowAlert: false,
  }),

  computed: {
    totalPages() {
      if (!this.counterpartyPages) {
        return null
      }
      return this.counterpartyPages.totalPages
    },

    counterpartiesForTable() {
      if (this.counterpartyPages) {
        let counterparties = []
        for (let index in this.counterpartyPages.content) {
          counterparties.push({
            id: this.counterpartyPages.content[index].id,
            title: this.counterpartyPages.content[index].title,
            address: this.counterpartyPages.content[index].address,
            inn: this.counterpartyPages.content[index].inn,
          })
        }

        return counterparties
      } else {
        return []
      }
    }
  },

  methods: {
    async getCounterparties() {
      if (this.isValidSearchFormCounterparties) {
        this.counterpartyPages = await apiGetAllCounterparties({
          title: this.searchFormCounterparties.title,
          address: this.searchFormCounterparties.address,
          INN: this.searchFormCounterparties.inn,
          page: this.page - 1,
          size: this.pageSize,
        })
      }
    },

    updateCounterparty(counterpartyId) {
      this.$router.push(`/counterparties/${counterpartyId}`)
    },

    deleteCounterparty(counterpartyId) {
      apiDeleteCounterpartyById(counterpartyId)
          .then(() => {
                this.counterpartyPages.content = this.counterpartyPages.content.filter((counterparty) => {
                  return counterparty.id !== counterpartyId
                });
                this.getCounterparties()
              }
          )
          .catch((error) => {
            if (error.response.status === 403) {
              this.isShowAlert = true
            }
          })
    },

    changePage(page) {
      this.page = page
      this.getCounterparties()
    },
  }
}
</script>

<style scoped>

</style>