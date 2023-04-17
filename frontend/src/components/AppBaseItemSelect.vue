<template>
  <v-select
      v-model="select"
      :hint="`${select.value}`"
      :items="selectingItems"
      item-text="text"
      item-value="value"
      :label="label"
      persistent-hint
      return-object
      @change="selected"
      :rules="[v => nullable || !!v.value]"
      :loading="loading"
  ></v-select>
</template>

<script>
export default {
  name: "AppBaseItemSelect",
  props: {
    items: {
      type: Array,
      required: true,
    },
    value: String,
    label: String,
    nullable: {
      type: Boolean,
      default: true,
    },
    loading: Boolean
  },

  created() {
    for (let index in this.selectingItems) {
      if (this.value === this.selectingItems[index].value) {
        this.select = this.selectingItems[index]
      }
    }
  },

  data: () => ({
    select: {text: "Любой", value: null},
  }),

  computed: {
    selectingItems() {
      if (this.nullable) {
        return [
          {text: 'Любой', value: null},
          ...this.items
        ]
      } else {
        return [
          ...this.items
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