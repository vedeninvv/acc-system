<template>
  <v-autocomplete
      v-model="selectedValue"
      :items="counterparties"
      item-text="title"
      item-value="id"
      :search-input.sync="searchStr"
      dense
      filled
      :label="label"
      @update:search-input="getCounterparties"
      :loading="loading"
      hide-no-data
      placeholder="Введите название для поиска"
      :rules="[v => nullable || !!v]"
      @change="selected"
  >
    <template v-slot:append-item>
      <div v-intersect="endIntersect"/>
    </template>
  </v-autocomplete>
</template>

<script>
import {apiGetAllCounterparties, apiGetCounterpartyById} from "@/shared/services/counterpartyService";

export default {
  name: "AppCounterpartySelect",

  props: {
    label: {
      type: String,
      default: "Контрагент"
    },
    nullable: {
      type: Boolean,
      default: true
    },
    value: Number
  },

  created() {
    if (this.value) {
      this.selectedValue = this.value
      apiGetCounterpartyById(this.selectedValue).then((counterparty) => {
        this.counterpartiesContent = [counterparty]
      })
    }
  },

  data: () => ({
    selectedValue: null,
    searchStr: null,
    counterpartiesContent: [],
    lastCounterpartyPages: null,
    loading: false,
    pageSize: 10,
  }),

  computed: {
    counterparties() {
      if (this.counterpartiesContent != null) {
        let counterparties = []
        for (let index in this.counterpartiesContent) {
          counterparties.push({
            id: this.counterpartiesContent[index].id,
            title: this.counterpartiesContent[index].title,
          })
        }
        return counterparties
      } else {
        return []
      }
    }
  },

  methods: {
    getCounterparties() {
      this.loading = true
      apiGetAllCounterparties({
        title: this.searchStr,
        size: this.pageSize,
        page: 0
      }).then((counterparties) => {
        this.counterpartiesContent = [...counterparties.content]
        this.lastCounterpartyPages = counterparties
        this.loading = false
      })
    },

    getCounterpartiesNextPage(searchStr) {
      if (this.lastCounterpartyPages.number > this.lastCounterpartyPages.totalPages) {
        return
      }
      this.loading = true
      apiGetAllCounterparties({
        title: searchStr,
        size: this.pageSize,
        page: this.lastCounterpartyPages.number + 1
      }).then((counterparties) => {
        this.counterpartiesContent = [...this.counterpartiesContent, ...counterparties.content]
        this.lastCounterpartyPages = counterparties
        this.loading = false
      })
    },

    endIntersect(entries, observer, isIntersecting) {
      if (isIntersecting && this.lastCounterpartyPages.number <= this.lastCounterpartyPages.totalPages && !this.loading) {
        this.getCounterpartiesNextPage(this.searchStr)
      }
    },

    selected(value) {
      this.$emit('input', value)
    }
  }
}
</script>

<style scoped>

</style>