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
  name: "AppRoleSelect",
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
      {text: 'Админ', value: 'ADMIN'},
      {text: 'Пользователь', value: 'USER'},
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