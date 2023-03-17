<template>
  <div>
    <v-form v-model="valid">
      <v-container>

        <v-row>
          <v-col cols="6">
            <v-text-field
                v-model="formContracts.title"
                label="Название контракта"
            ></v-text-field>
          </v-col>
        </v-row>

        <v-row>
          <v-col cols="3">
            <app-ruble-input
                v-model="formContracts.minSum"
                label="Минимальная сумма"
            ></app-ruble-input>
          </v-col>

          <v-col cols="3">
            <app-ruble-input
                v-model="formContracts.maxSum"
                label="Максимальная сумма"
            ></app-ruble-input>
          </v-col>
        </v-row>

        <v-row>
          <v-col cols="3">
            <app-range-date-picker
                v-model="formContracts.dates"
                label="Период"
            ></app-range-date-picker>
          </v-col>

          <v-col cols="3">
            <app-contract-type-select
                v-model="formContracts.contractType"
                label="Тип контракта"
            ></app-contract-type-select>
          </v-col>
        </v-row>

        <v-row>
          <v-col cols="3">
            <v-btn
                :disabled="!valid"
                color="blue" dark>Поиск
            </v-btn>
          </v-col>
        </v-row>

      </v-container>
    </v-form>

    <v-container>


      <div class="text-center">
        <v-pagination
            v-model="page"
            :length="paginationLength"
        ></v-pagination>
      </div>
    </v-container>
  </div>
</template>

<script>
import AppRangeDatePicker from "@/components/AppRangeDatePicker";
import AppContractTypeSelect from "@/components/AppContractTypeSelect";
import AppRubleInput from "@/components/AppRubleInput";

export default {
  name: "ContractsPage",
  components: {AppRubleInput, AppContractTypeSelect, AppRangeDatePicker},
  data: () => ({
    valid: false,
    formContracts: {
      title: null,
      minSum: null,
      maxSum: null,
      dates: null,
      contractType: null,
    },
    page: 1,
  }),
  computed: {
    paginationLength() {
      return 6;
    }
  }
}
</script>

<style scoped>

</style>