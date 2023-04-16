<template>
  <v-container>
    <v-row>
      <v-col>
        <h3>{{ label }}</h3>
      </v-col>
      <v-spacer></v-spacer>
      <v-col align="right">
        <v-btn
            color="blue" dark
            @click="$emit('post')"
        >Создать
        </v-btn>
      </v-col>

    </v-row>
    <v-row>
      <app-paging-table :headers="headers"
                        :items="items"
                        :total-pages="totalPages"
                        :init-page="initPage"
                        :label="label"
                        @pageSelected="e => $emit('pageSelected', e)">
        <template v-slot:additional-th>
          <th>Изменить</th>
          <th>Удалить</th>
        </template>

        <template v-slot:additional-td="slotProps">
          <td class="text-center">
            <v-icon
                small
                class="mr-2"
                @click="$emit('put', slotProps.item.id)"
            >
              mdi-pencil
            </v-icon>
          </td>
          <td class="text-center">
            <v-icon
                small
                @click="$emit('delete', slotProps.item.id)"
            >
              mdi-delete
            </v-icon>
          </td>
        </template>
      </app-paging-table>
    </v-row>
  </v-container>
</template>

<script>
import AppPagingTable from "@/components/AppPagingTable";

export default {
  name: "AppPagingTableCrud",
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