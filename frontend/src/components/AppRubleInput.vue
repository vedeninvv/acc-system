<template>
  <v-text-field
      :label="label"
      :rules="rubleRules"
      @input="$emit('input',   $event.length > 0 ? $event : null)"
      :value="rubleValue"
  ></v-text-field>
</template>

<script>
export default {
  name: "AppRubleInput",
  props: ['value', 'label'],

  created() {
    this.rubleValue = this.value
  },

  data: () => ({
    rubleValue: null,
    rubleRules: [
      v => (v == null || /^\d*[.,]?\d?\d?$/.test(v)) || 'Положительное число, макс. 2 знака после запятой'
    ]
  }),
}
</script>

<style scoped>

</style>