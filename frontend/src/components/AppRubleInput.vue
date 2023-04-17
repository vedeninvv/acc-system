<template>
  <v-text-field
      :label="label"
      :rules="rubleRules"
      @input="$emit('input',   $event.length > 0 ? $event : null)"
      :value="rubleValue"
      :loading="loading"
  ></v-text-field>
</template>

<script>
export default {
  name: "AppRubleInput",
  props: {
    value: [Number, String],
    label: String,
    nullable: {
      type: Boolean,
      default: true,
    },
    loading: Boolean
  },

  created() {
    this.rubleValue = this.value
  },

  data: () => ({
    rubleValue: null,
  }),

  computed: {
    rubleRules() {
      return [
        v => (this.nullable || !!v),
        v => (!v) || (/^\d*[.,]?\d?\d?$/.test(v)) || 'Положительное число, макс. 2 знака после запятой'
      ]
    }
  }
}
</script>

<style scoped>

</style>