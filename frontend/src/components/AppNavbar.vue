<template>
  <nav>
    <v-toolbar app color="blue" dark >
      <v-toolbar-title class="mr-8">
        НИЦ СПб ЭТУ - ЦРТРИС
      </v-toolbar-title>

      <v-btn color="blue" depressed class="ml-3" to="/contracts">Договоры</v-btn>
      <v-btn color="blue" depressed class="ml-3" to="/counterparties">Контрагенты</v-btn>
      <v-btn color="blue" depressed class="ml-3" to="/reports">Отчеты</v-btn>
      <v-btn color="blue" depressed class="ml-3" to="/administration">Администрирование</v-btn>

      <v-spacer></v-spacer>
      <v-btn outlined>Выход</v-btn>
    </v-toolbar>
  </nav>
</template>

<script>
export default {
  name: "NavbarMain"
}
</script>

<style scoped>

</style>