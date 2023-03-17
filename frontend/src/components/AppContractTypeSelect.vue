<template>
  <v-select
      v-model="select"
      :hint="`${select.value}`"
      :items="items"
      item-text="text"
      item-value="value"
      :label="label"
      persistent-hint
      return-object
      @change="selected"
  ></v-select>
</template>

<script>
export default {
  name: "AppContractTypeSelect",
  props: {
    label: String,
  },
  data: () => ({
    select: {text: 'Любой', value: 'ALL'},
    items: [
      {text: 'Любой', value: 'ALL'},
      {text: 'Закупка', value: 'PURCHASE'},
      {text: 'Поставка', value: 'SUPPLY'},
      {text: 'Работы', value: 'WORKS'}
    ],
  }),
  methods: {
    selected(e) {
      if (e.value === "ALL") {
        this.$emit('input', null)
      } else {
        this.$emit('input', e.value)
      }
    }
  }
}
</script>

<style scoped>

</style>