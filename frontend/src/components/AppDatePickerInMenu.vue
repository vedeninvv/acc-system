<template>
  <v-menu
      ref="menu"
      v-model="menu"
      :close-on-content-click="false"
      :return-value.sync="date"
      transition="scale-transition"
      offset-y
      min-width="auto"
  >
    <template v-slot:activator="{ on, attrs }">
      <v-text-field
          v-model="date"
          :label="label"
          prepend-icon="mdi-calendar"
          readonly
          v-bind="attrs"
          v-on="on"
          :rules="[v => !!v || nullable]"
      ></v-text-field>
    </template>
    <v-date-picker
        v-model="date"
        no-title
        scrollable
    >
      <v-btn
          text
          color="primary"
          @click="clear"
      >
        Clear
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn
          text
          color="primary"
          @click="menu = false"
      >
        Cancel
      </v-btn>
      <v-btn
          text
          color="primary"
          @click="picked"
      >
        OK
      </v-btn>
    </v-date-picker>
  </v-menu>
</template>

<script>
export default {
  name: "AppDatePickerInMenu",
  props: {
    label: String,
    value: String,
    nullable: {
      type: Boolean,
      default: true
    }
  },

  created() {
    this.date = this.value;
  },

  data: () => ({
    date: null,
    menu: false,
  }),
  methods: {
    picked() {
      this.$refs.menu.save(this.date)
      this.$emit('input', this.date)
    },

    clear() {
      this.date = null
      this.picked()
      this.menu = false
    }
  }
}
</script>

<style scoped>

</style>