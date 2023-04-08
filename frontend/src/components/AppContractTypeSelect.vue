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
    value: String,
    label: String,
    nullable: {
      type: Boolean,
      default: true,
    }
  },

  created() {
    if (this.value === this.items[0].value || this.value === this.items[1].value
        || this.value === this.items[2].value) {
      this.select = {text: "Любой", value: this.value}
    }
  },

  data: () => ({
    select: {text: "Любой", value: null},
  }),

  computed: {
    items() {
      if (this.nullable) {
        return [
          {text: 'Любой', value: null},
          {text: 'Закупка', value: 'PURCHASE'},
          {text: 'Поставка', value: 'SUPPLY'},
          {text: 'Работы', value: 'WORKS'}
        ]
      } else {
        return [
          {text: 'Закупка', value: 'PURCHASE'},
          {text: 'Поставка', value: 'SUPPLY'},
          {text: 'Работы', value: 'WORKS'}
        ]
      }
    }
  },

  methods: {
    selected(e) {
      if (e.value === "ALL") {
        this.$emit('input', null)
      } else {
        this.$emit('input', e.value)
      }
    },
  }
}
</script>

<style scoped>

</style>