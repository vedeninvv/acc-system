<template>
  <v-container>
    <v-row justify="center">
      <h1>Пользователи</h1>
    </v-row>

    <v-container>
      <user-search-with-output-table>
      </user-search-with-output-table>
    </v-container>
  </v-container>
</template>

<script>
import UserSearchWithOutputTable from "@/components/UserSearchWithOutputTable";

export default {
  name: "AdministrationPage",
  components: {UserSearchWithOutputTable},
}
</script>

<style scoped>

</style>