<template>
  <v-form v-model="isValidUserSearchForm"
          @submit.prevent="getUsers"
  >
    <v-row justify="center">
      <v-col cols="6">
        <v-text-field
            v-model="searchFormUsers.searchStr"
            label="Поиск по ФИО или username"
            :loading="loading"
        ></v-text-field>
      </v-col>
    </v-row>

    <v-row justify="center">
      <v-col cols="6">
        <app-role-select v-model="searchFormUsers.role"
                         :loading="loading">
        </app-role-select>
      </v-col>
    </v-row>

    <v-row>
      <v-spacer></v-spacer>
      <v-col cols="3">
        <v-btn
            :class="{'disable-el': !isValidUserSearchForm || loading}"
            color="blue" dark
            type="submit">Поиск
        </v-btn>
      </v-col>
    </v-row>

    <v-container>
      <v-row>
        <v-col>
          <v-alert
              v-model="isShowAlert"
              dismissible
              type="error"
          >Пользователь не может быть удален, пока у него существуют договора, или если это суперпользователь
          </v-alert>
        </v-col>
      </v-row>

      <v-row justify="center">
        <app-paging-table-crud label="Пользователи"
                               :headers="headersForUsersHeaders"
                               :items="usersForTable"
                               :total-pages="totalPages"
                               @post="$router.push('users/new')"
                               @put="updateUser"
                               @delete="deleteUser"
                               @pageSelected="changePage">
        </app-paging-table-crud>
      </v-row>
    </v-container>
  </v-form>
</template>

<script>
import AppPagingTableCrud from "@/components/AppPagingTableCrud";
import AppRoleSelect from "@/components/AppRoleSelect";
import {apiDeleteUserById, apiGetAllUsers} from "@/shared/services/userService";

export default {
  name: "UserSearchWithOutputTable",
  components: {AppRoleSelect, AppPagingTableCrud},

  created() {
    this.getUsers()
  },

  data: () => ({
    loading: false,
    isShowAlert: false,
    isValidUserSearchForm: true,
    searchFormUsers: {
      searchStr: null,
      role: null
    },

    page: 1,
    pageSize: 10,
    totalPages: null,

    users: null,
    headersForUsersHeaders: [
      "ID",
      "Роль",
      "Username",
      "Имя",
      "Фамилия",
      "Отчество",
      "Дата действия аккаунта",
    ]
  }),

  computed: {
    usersForTable() {
      let data = []
      if (this.users != null) {
        for (let i = 0; i < this.users.length; i++) {
          data.push({
            id: this.users[i].id,
            role: this.processRole(this.users[i].role),
            username: this.users[i].username,
            name: this.users[i].name,
            surname: this.users[i].surname,
            middleName: this.users[i].middleName,
            dateUserExpired: this.users[i].dateUserExpired,
          })
        }
      }
      return data
    }
  },

  methods: {
    async getUsers() {
      if (!this.isValidUserSearchForm) {
        return
      }

      this.loading = true
      let contractsPages = await apiGetAllUsers({
        ...this.searchFormUsers,
        page: this.page - 1,
        size: this.pageSize,
      })
      this.loading = false

      this.users = contractsPages.content
      this.totalPages = contractsPages.totalPages
    },

    updateUser(id) {
      this.$router.push(`/users/${id}`)
    },

    deleteUser(id) {
      apiDeleteUserById(id)
          .then(() => {
            this.users = this.users.filter((user) => {
              return user.id !== id
            })
            this.getUsers()
          })
          .catch((err) => {
            if (err.response.status === 403) {
              this.isShowAlert = true
            }
          })
    },

    changePage(page) {
      this.page = page
      this.getUsers()
    },

    processRole(role) {
      let roles = {
        "ADMIN": "Админ",
        "USER": "Пользователь",
      }
      return roles[role]
    }
  }
}
</script>

<style scoped>

</style>