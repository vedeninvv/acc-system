<template>
  <v-form v-if="isShowAuthSettingsForm"
          v-model="isValidAuthSettingsForm"
          @submit.prevent="saveUserAuthSettings">
    <v-alert
        v-model="isShowErrorAlert"
        dismissible
        type="error"
    >Параметры авторизации суперпользователя не могут быть обновлены
    </v-alert>
    <v-alert
        v-model="isShowAuthSettingsSavedAlert"
        dismissible
        type="success"
    >Сохранено
    </v-alert>
    <v-row no-gutters>
      <v-col>
        <app-role-select v-model="authSettingsForm.role"
                         :nullable="false"
                         label="Роль"
                         :loading="loading"
        >
        </app-role-select>
      </v-col>
    </v-row>

    <v-row no-gutters>
      <v-col>
        <app-date-picker-in-menu v-model="authSettingsForm.dateUserExpired"
                                 label="Дата действия аккаунта"
                                 :loading="loading">
        </app-date-picker-in-menu>
      </v-col>
    </v-row>

    <v-row>
      <v-col align="right">
        <v-btn
            :class="{'disable-el': !isValidAuthSettingsForm || loading}"
            color="blue" dark
            type="submit">Сохранить
        </v-btn>
      </v-col>
    </v-row>
  </v-form>
</template>

<script>
import AppDatePickerInMenu from "@/components/AppDatePickerInMenu";
import AppRoleSelect from "@/components/AppRoleSelect";
import {apiGetUserById, apiUpdateUserAuthSettingsById} from "@/shared/services/userService";

export default {
  name: "UserAuthSettingsForm",
  components: {AppDatePickerInMenu, AppRoleSelect},
  props: {
    userId: Number
  },

  created() {
    if (!this.isNewUser) {
      this.getUserAuthSettings()
    }
  },

  data: () => ({
    loading: false,
    userLoaded: false,
    isShowErrorAlert: false,
    isShowAuthSettingsSavedAlert: false,

    isValidAuthSettingsForm: true,
    authSettingsForm: {
      role: null,
      dateUserExpired: null,
    },
  }),

  computed: {
    isNewUser() {
      return this.userId == null
    },

    isShowAuthSettingsForm() {
      return this.isNewUser || this.userLoaded
    }
  },

  methods: {
    async getUserAuthSettings() {
      let user = await apiGetUserById(this.userId)

      this.authSettingsForm.role = user.role
      this.authSettingsForm.dateUserExpired = user.dateUserExpired

      this.userLoaded = true
    },

    saveUserAuthSettings() {
      this.isShowErrorAlert = false
      this.isShowAuthSettingsSavedAlert = false
      if (!this.isValidAuthSettingsForm) {
        return
      }
      this.loading = true
      apiUpdateUserAuthSettingsById(this.userId, this.authSettingsForm)
          .then(() => {
            this.loading = false
            this.isShowAuthSettingsSavedAlert = true
          })
          .catch(() => {
            this.loading = false
            this.isShowErrorAlert = true
          })
    },
  }
}
</script>

<style scoped>

</style>