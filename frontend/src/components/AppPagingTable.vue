<template>
  <v-container>

    <v-simple-table>
      <thead>
      <tr>
        <th class="text-center"
            v-for="header in headers"
            :key="'table-headers-' + header">
          {{ header }}
        </th>
        <slot name="additional-th"></slot>
      </tr>
      </thead>
      <tbody>
      <tr
          v-for="item in items"
          :key="item.id"
      >
        <td v-for="(value, key) in item"
            :key="key + item.id"
            class="text-center">
          {{ value }}
        </td>

        <slot name="additional-td"
              :item="item"></slot>
      </tr>
      </tbody>
    </v-simple-table>

    <v-container v-if="totalPages != null" class="pb-16">
      <div class="text-center"
           v-if="items != null && items.length >= 1">
        <v-pagination
            v-model="page"
            :length="totalPages"
            @input="$emit('pageSelected', page)"
        ></v-pagination>
      </div>
      <div class="text-center"
           v-else>
        Нет данных
      </div>
    </v-container>

  </v-container>
</template>

<script>
export default {
  name: "AppPagingTable",
  created() {
    this.page = this.initPage
  },
  props: {
    headers: Array,
    items: Array,
    totalPages: Number,
    initPage: {
      type: Number,
      default: 1
    },
    label: String
  },
  data: () => ({
    page: null
  })
}
</script>

<style scoped>

</style>