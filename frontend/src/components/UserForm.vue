<template>
  <v-form v-if="isShowUserForm"
          v-model="isValidUserForm"
          @submit.prevent="saveUser">
    <v-row>
      <v-col>
        <v-alert
            v-model="isShowUsernameExistAlert"
            dismissible
            type="error"
        >Username уже занят. Попробуйте другой
        </v-alert>
        <v-alert
            v-model="isShowUserSavedAlert"
            dismissible
            type="success"
        >Сохранено
        </v-alert>
      </v-col>
    </v-row>

    <v-row no-gutters>
      <v-col>
        <v-text-field v-model.trim="userForm.username"
                      label="Username"
                      :rules="loginRules"
        ></v-text-field>
      </v-col>
    </v-row>

    <v-row no-gutters>
      <v-col>
        <v-text-field v-model.trim="userForm.password"
                      :label="isNewUser? 'Пароль': 'Новый пароль'"
                      :rules="loginRules"
                      type="password"
        ></v-text-field>
      </v-col>
    </v-row>

    <v-row no-gutters>
      <v-col>
        <v-text-field v-model.trim="userForm.name"
                      label="Имя"
                      :rules="[v => (!!v.trim() || 'Обязательно')]"
        ></v-text-field>
      </v-col>
    </v-row>

    <v-row no-gutters>
      <v-col>
        <v-text-field v-model.trim="userForm.surname"
                      label="Фамилия"
                      :rules="[v => (!!v.trim() || 'Обязательно')]"
        ></v-text-field>
      </v-col>
    </v-row>

    <v-row no-gutters>
      <v-col>
        <v-text-field v-model.trim="userForm.middleName"
                      label="Отчество"
                      :rules="[v => (!!v.trim() || 'Обязательно')]"
        ></v-text-field>
      </v-col>
    </v-row>

    <template v-if="isNewUser">
      <h2>Параметры авторизации</h2>
      <v-row no-gutters>
        <v-col>
          <app-role-select v-model="authSettingsForm.role"
                           :nullable="false"
                           label="Роль"
          >
          </app-role-select>
        </v-col>
      </v-row>

      <v-row no-gutters>
        <v-col>
          <app-date-picker-in-menu v-model="authSettingsForm.dateUserExpired"
                                   label="Дата действия аккаунта">
          </app-date-picker-in-menu>
        </v-col>
      </v-row>
    </template>

    <v-row>
      <v-col align="right">
        <v-btn
            :class="{'disable-el': !isValidUserForm}"
            color="blue" dark
            type="submit">Сохранить
        </v-btn>
      </v-col>
    </v-row>
  </v-form>
</template>

<script>
import {apiCreateUser, apiGetUserById, apiUpdateUserById} from "@/shared/services/userService";
import AppRoleSelect from "@/components/AppRoleSelect";
import AppDatePickerInMenu from "@/components/AppDatePickerInMenu";

export default {
  name: "UserForm",
  components: {AppDatePickerInMenu, AppRoleSelect},
  props: {
    userId: Number
  },

  created() {
    if (!this.isNewUser) {
      this.getUser()
    }
  },

  data: () => ({
    isShowUsernameExistAlert: false,
    isShowUserSavedAlert: false,
    userLoaded: false,

    isValidUserForm: true,
    userForm: {
      username: "",
      password: "",
      name: "",
      surname: "",
      middleName: "",
    },
    authSettingsForm: {
      role: null,
      dateUserExpired: null,
    },

    loginRules: [
      v => !!v.trim() || 'Обязательно',
      v => v.length >= 6 || 'Мин. 6 символов',
      v => v.length <= 20 || 'Макс. 20 символов',
    ],
  }),

  computed: {
    isNewUser() {
      return this.userId == null
    },

    isShowUserForm() {
      return this.isNewUser || this.userLoaded
    }
  },

  methods: {
    async getUser() {
      let user = await apiGetUserById(this.userId)

      this.userForm.username = user.username
      this.userForm.name = user.name
      this.userForm.surname = user.surname
      this.userForm.middleName = user.middleName

      this.userLoaded = true
    },

    saveUser() {
      this.isShowUserSavedAlert = false
      this.isShowUsernameExistAlert = false
      if (!this.isValidUserForm) {
        return
      }
      if (this.isNewUser) {
        this.createUser()
      } else {
        this.updateUser()
      }
    },

    createUser() {
      apiCreateUser({
        ...this.userForm,
        ...this.authSettingsForm
      })
          .then(() => {
            this.isShowUserSavedAlert = true
            this.$router.push("/administration")
          })
          .catch(() => {
            this.isShowUsernameExistAlert = true
          })
    },

    updateUser() {
      apiUpdateUserById(this.userId, this.userForm)
          .then(() => {
            this.isShowUserSavedAlert = true
          })
          .catch(() => {
            this.isShowUsernameExistAlert = true
          })
    },
  }
}
</script>

<style scoped>

</style>