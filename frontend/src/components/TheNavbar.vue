<template>
  <nav>
    <v-app-bar app color="blue" dark>
      <v-app-bar-title class="mr-8">
        НИЦ СПб ЭТУ - ЦРТРИС
      </v-app-bar-title>

      <template v-if="$route.path !== '/signin'">
        <v-btn to="/contracts"
               color="blue"
               depressed
               class="ml-3"
        >
          Договоры
        </v-btn>

        <v-btn to="/counterparties"
               color="blue"
               depressed
               class="ml-3"
        >
          Контрагенты
        </v-btn>

        <v-btn to="/reports"
               color="blue"
               depressed
               class="ml-3"
        >
          Отчеты
        </v-btn>
        <v-btn v-if="role === 'ADMIN'"
               to="/administration"
               color="blue"
               depressed
               class="ml-3"
        >
          Администрирование
        </v-btn>

        <v-btn v-if="role === 'USER'"
               :to="profileUrl"
               color="blue"
               depressed
               class="ml-3"
        >
          Профиль
        </v-btn>

        <v-spacer></v-spacer>
        <v-btn outlined
               @click="signout">
          Выход
        </v-btn>
      </template>

      <template v-else>
        <span class="title">Вход</span>
      </template>

    </v-app-bar>
  </nav>
</template>

<script>

import {apiSignout, getRole, getUserId} from "@/shared/services/userService";

export default {
  name: "TheNavbar",

  updated() {
    this.role = getRole()
    this.userId = getUserId()
  },

  data: () => ({
    role: null,
    userId: null
  }),
  computed: {
    profileUrl() {
      return "/users/" + this.userId
    }
  },
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