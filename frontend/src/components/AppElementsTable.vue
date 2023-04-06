<template>
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
</template>

<script>
export default {
  name: "AppElementsTable",
  props: {
    headers: Array,
    items: Array,
  },

}

</script>

<style scoped>

</style>