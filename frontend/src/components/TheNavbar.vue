<template>
  <nav>
    <v-app-bar app color="blue" dark>
      <v-app-bar-title class="mr-8">
        НИЦ СПб ЭТУ - ЦРТРИС
      </v-app-bar-title>

      <v-btn color="blue" depressed class="ml-3" to="/contracts">Договоры</v-btn>
      <v-btn color="blue" depressed class="ml-3" to="/counterparties">Контрагенты</v-btn>
      <v-btn color="blue" depressed class="ml-3" to="/reports">Отчеты</v-btn>
      <v-btn color="blue" depressed class="ml-3" to="/administration">Администрирование</v-btn>

      <v-spacer></v-spacer>
      <v-btn outlined
             @click="signout">Выход
      </v-btn>

    </v-app-bar>
  </nav>
</template>

<script>

import {apiSignout} from "@/shared/services/userService";

export default {
  name: "TheNavbar",
  methods: {
    signout() {
      apiSignout().then(() => {
        this.$router.push("/signin")
      })
    }
  }
}
</script>

<style scoped>

</style>