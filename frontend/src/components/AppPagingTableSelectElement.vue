<template>
  <app-paging-table :headers="headers"
                    :items="items"
                    :total-pages="totalPages"
                    :init-page="initPage"
                    :label="label">
    <template v-slot:additional-th>
      <th class="text-center">Выбрать</th>
    </template>

    <template v-slot:additional-td="slotProps">
      <td class="text-center">
        <v-btn @click="$emit('select', slotProps.item.id)"
               color="blue" dark
        type="button">
          Выбрать
        </v-btn>
      </td>
    </template>
  </app-paging-table>
</template>

<script>
import AppPagingTable from "@/components/AppPagingTable";

export default {
  name: "AppPagingTableSelectElement",
  components: {AppPagingTable},
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