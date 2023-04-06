<template>
  <v-menu
      ref="menuDatePicker"
      v-model="menuDatePicker"
      :close-on-content-click="false"
      :return-value.sync="dates"
      transition="scale-transition"
      offset-y
      min-width="auto"
  >
    <template v-slot:activator="{ on, attrs }">
      <v-text-field
          v-model="datesRange"
          :label="label"
          prepend-icon="mdi-calendar"
          readonly
          v-bind="attrs"
          v-on="on"
      ></v-text-field>
    </template>
    <v-date-picker
        v-model="dates"
        no-title
        scrollable
        range
    >
      <v-btn
          text
          color="primary"
          @click="dates = []"
      >
        Clear
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn
          text
          color="primary"
          @click="menuDatePicker = false"
      >
        Cancel
      </v-btn>
      <v-btn
          text
          color="primary"
          @click="saveDates"
      >
        OK
      </v-btn>
    </v-date-picker>
  </v-menu>
</template>

<script>
export default {
  name: "AppRangeDatePicker",
  props: {
    label: String
  },
  data: () => ({
    dates: [],
    menuDatePicker: false,
  }),
  computed: {
    datesRange() {
      if (this.dates.length === 2) {
        return this.dates[0] + " - " + this.dates[1]
      } else if (this.dates.length === 1) {
        return this.dates[0] + " - "
      }
      else
        return null
    }
  },
  methods: {
    saveDates() {
      this.$refs.menuDatePicker.save(this.dates.sort())
      this.$emit('input', this.dates)
    }
  }
}
</script>

<style scoped>

</style>