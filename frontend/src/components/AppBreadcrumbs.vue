<template>
  <v-breadcrumbs :items="items">
    <template v-slot:divider>
      <v-icon>mdi-chevron-right</v-icon>
    </template>
  </v-breadcrumbs>
</template>

<script>
export default {
  name: "AppBreadcrumbs",

  computed: {
    items() {
      let items = []
      let routeItems = this.$route.fullPath.split("/")
      let path = ""
      for (let i = 0; i < routeItems.length; i++) {
        if (i !== 0 && !routeItems[i]) {
          continue
        }
        items.push(this.processRouteItem(routeItems[i], path))
        path += routeItems[i] + "/"
      }
      return items
    }
  },

  methods: {
    processRouteItem(routeItemKey, currentPath) {
      console.log(routeItemKey)
      let routeItemsMap = {
        "contracts": {
          text: "Договоры",
          href: currentPath + "contracts"
        },
        "signin": {
          text: "Вход",
          href: currentPath + "signin"
        },
        "counterparty-contracts": {
          text: "Договоры с контрагентом",
          href: currentPath
        },
        "stages": {
          text: "Этапы",
          href: currentPath
        },
        "new": {
          text: "Создание",
          href: currentPath + "new"
        },
        "counterparties": {
          text: "Контрагенты",
          href: currentPath + "counterparties"
        },
        "reports": {
          text: "Отчеты",
          href: currentPath + "reports"
        },
        "administration": {
          text: "Администрирование",
          href: currentPath + "administration"
        },
        "users": {
          text: "Пользователи",
          href: currentPath + "administration"
        },
        "403": {
          text: "Доступ запрещен",
          href: currentPath + "403"
        },
        "404": {
          text: "Не найдено",
          href: currentPath + "404"
        }
      }
      if (routeItemsMap[routeItemKey]) {
        return routeItemsMap[routeItemKey]
      } else {
        if (!routeItemKey) {
          return {
            text: "/",
            href: currentPath
          }
        }
        if (!isNaN(+routeItemKey)) {
          return {
            text: "№" + routeItemKey,
            href: currentPath + routeItemKey
          }
        }
      }
    }
  }
}
</script>

<style scoped>

</style>