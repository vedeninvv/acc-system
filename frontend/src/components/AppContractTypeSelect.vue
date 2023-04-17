<template>
  <app-base-item-select :items="items"
                        :value="value"
                        :label="label"
                        :nullable="nullable"
                        @input="selected"
                        :loading="loading">
  </app-base-item-select>
</template>

<script>
import AppBaseItemSelect from "@/components/AppBaseItemSelect";

export default {
  name: "AppContractTypeSelect",
  components: {AppBaseItemSelect},
  props: {
    value: String,
    label: String,
    nullable: {
      type: Boolean,
      default: true,
    },
    loading: Boolean
  },

  data: () => ({
    items: [
      {text: 'Закупка', value: 'PURCHASE'},
      {text: 'Поставка', value: 'SUPPLY'},
      {text: 'Работы', value: 'WORKS'}
    ]
  }),

  methods: {
    selected(e) {
      this.$emit('input', e)
    },
  }
}
</script>

<style scoped>

</style>