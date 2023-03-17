<template>
  <v-text-field
      :label="label"
      :rules="rubleRules"
      @input="$emit('input',   $event.length > 0 ? $event : null)"
  ></v-text-field>
</template>

<script>
export default {
  name: "AppPositiveNumberInput",
  props: {
    label: String,
  },
  data: () => ({
    rubleRules: [
      v => (v == null || /^\d*[.,]?\d?\d?$/.test(v)) || 'Положительное число, макс. 2 знака после запятой'
    ]
  }),
}
</script>

<style scoped>

</style>