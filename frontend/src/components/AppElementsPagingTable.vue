<template>
  <v-container>
    <v-row ju>
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
      <v-col>
        <v-simple-table>
          <thead>
          <tr>
            <th class="text-center"
                v-for="header in headers"
                :key="'table-headers-' + header">
              {{ header }}
            </th>
            <th>Изменить</th>
            <th>Удалить</th>
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

            <td class="text-center">
              <v-icon
                  small
                  class="mr-2"
                  @click="$emit('put', item.id)"
              >
                mdi-pencil
              </v-icon>
            </td>
            <td class="text-center">
              <v-icon
                  small
                  @click="$emit('delete', item.id)"
              >
                mdi-delete
              </v-icon>
            </td>
          </tr>
          </tbody>
        </v-simple-table>
      </v-col>

      <v-container v-if="totalPages != null" class="pb-16">
        <div class="text-center"
             v-if="totalPages >= 1">
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
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "AppElementsPagingTable",
  props: {
    headers: Array,
    items: Array,
    totalPages: Number,
    label: String
  },
  data: () => ({
    page: 1
  })
}

</script>

<style scoped>

</style>