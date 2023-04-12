<template>
  <v-text-field
      :label="label"
      :rules="innRules"
      @input="$emit('input',   $event.length > 0 ? $event : null)"
      :value="innValue"
  ></v-text-field>
</template>

<script>
export default {
  name: "AppInnInput",
  props: {
    value: [Number, String],
    label: String,
    nullable: {
      type: Boolean,
      default: true,
    }
  },

  created() {
    this.innValue = this.value
  },

  data: () => ({
    innValue: null,
  }),

  computed: {
    innRules() {
      return [
        v => (this.nullable || !!v),
        v => (/^\d*$/.test(v)) || 'Только цифры',
        v => (v.length <= 12 || 'ИНН не может превышать 12 цирф')
      ]
    }
  }
}
</script>

<style scoped>

</style>