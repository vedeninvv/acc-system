<template>
  <v-sheet width="300" class="mx-auto">
    <v-alert
        v-model="isShowAlert"
        dismissible
        type="error"
    >Неверные данные входа
    </v-alert>
    <v-form
        @submit.prevent="submit"
        v-model="valid">
      <v-text-field
          v-model="loginForm.username"
          label="Логин"
          :rules="loginRules"
          required
          :loading="isLoading"
      ></v-text-field>

      <v-text-field
          v-model="loginForm.password"
          label="Пароль"
          type="password"
          :rules="loginRules"
          required
          :loading="isLoading"
      ></v-text-field>
      <v-btn type="submit"
             block
             :class="{'disable-el': !valid, 'mt-2': true}"
             color="blue"
             dark
             :loading="isLoading"
      >
        Вход
      </v-btn>
    </v-form>
  </v-sheet>
</template>

<script>
import {apiSignin} from "@/shared/services/userService";

export default {
  name: "LoginPage",
  data: () => ({
    isShowAlert: false,
    isLoading: false,
    loginForm: {
      username: null,
      password: null,
    },
    valid: false,
    loginRules: [
      v => !!v || 'Обязательно'
    ]
  }),
  methods: {
    submit() {
      if (this.valid) {
        this.isShowAlert = false
        this.isLoading = true
        apiSignin(this.loginForm)
            .then(() => {
              this.$router.push('/')
            })
            .catch(() => {
              this.isShowAlert = true
              this.isLoading = false
            })
      }
    }
  }
}
</script>

<style scoped>

</style>