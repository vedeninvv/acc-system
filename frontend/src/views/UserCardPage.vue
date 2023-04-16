<template>
  <v-container>
    <v-container>
      <h1>Профиль пользователя</h1>
      <user-form :user-id="userId">
      </user-form>
    </v-container>

    <v-container v-if="isShowAuthSettingsForm">
      <h2>Параметры авторизации</h2>
      <user-auth-settings-form :user-id="userId">
      </user-auth-settings-form>
    </v-container>
  </v-container>
</template>

<script>
import UserForm from "@/components/UserForm";
import {getRole, roles} from "@/shared/services/userService";
import UserAuthSettingsForm from "@/components/UserAuthSettingsForm";

export default {
  name: "UserCardPage",
  components: {UserAuthSettingsForm, UserForm},

  created() {
    if (this.$route.path !== "/users/new") {
      this.userId = +this.$route.params.id
    }
  },

  data: () => ({
    userId: null,
  }),

  computed: {
    isNewUser() {
      return this.userId == null
    },

    isShowAuthSettingsForm() {
      return !this.isNewUser && getRole() === roles.admin
    }
  },
}
</script>

<style scoped>

</style>