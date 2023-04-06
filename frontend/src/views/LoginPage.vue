<template>
  <v-sheet width="300" class="mx-auto">
    <v-form
        @submit.prevent="submit"
        v-model="valid">
      <v-text-field
          v-model="loginForm.username"
          label="Логин"
          :rules="loginRules"
          required
      ></v-text-field>

      <v-text-field
          v-model="loginForm.password"
          label="Пароль"
          type="password"
          :rules="loginRules"
          required
      ></v-text-field>
      <v-btn type="submit"
             block
             :class="{'disable-el': !valid, 'mt-2': true}"
             color="blue"
             dark
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
        apiSignin(this.loginForm).then(() => {
          this.$router.push('/')
        })
      }
    }
  }
}
</script>

<style scoped>

</style>