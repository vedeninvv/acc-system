<template>
  <v-expansion-panel
  >
    <v-expansion-panel-header>
      <h3>Контракты текущего пользователя, входящие в заданный период по плановым срокам</h3>
    </v-expansion-panel-header>
    <v-expansion-panel-content>
      <v-alert
          outlined
          shaped
          text
          type="info"
      >
        Создать excel-отчет, содержащий все контракты (включая контракты с контрагентами) пользователя в заданном
        периоде
        по планируемым срокам. Для контрактов с контрагентами в отчет включаются контракты, к которым они относятся
        (даже
        если по плановым датам этот контракт не входит в заданный период)
      </v-alert>

      <v-form v-model="isValidContractSearchForm"
              @submit.prevent="getContractsInPlanPeriodReport"
      >
        <v-container>
          <v-row>
            <v-col>
              <app-date-picker-in-menu v-model="contractSearchForm.periodStart"
                                       label="Начало"
                                       :nullable="false">
              </app-date-picker-in-menu>
            </v-col>
          </v-row>

          <v-row>
            <v-col>
              <app-date-picker-in-menu v-model="contractSearchForm.periodEnd"
                                       label="Конец"
                                       :nullable="false">
              </app-date-picker-in-menu>
            </v-col>
          </v-row>

          <v-row>
            <v-col align="right">
              <v-btn
                  :class="{'disable-el': !isValidContractSearchForm}"
                  color="blue" dark
                  type="submit">
                Скачать
              </v-btn>
            </v-col>
          </v-row>
        </v-container>
      </v-form>
    </v-expansion-panel-content>
  </v-expansion-panel>

</template>

<script>
import AppDatePickerInMenu from "@/components/AppDatePickerInMenu";
import {
  apiGetContractsInPlanPeriodReport,
  CONTRACTS_IN_PLAN_PERIOD_REPORT_FILE_NAME,
  sendFileToUser
} from "@/shared/services/reportService";

export default {
  name: "ReportsPanelsContractsInPlanPeriodPanel",
  components: {AppDatePickerInMenu},

  data: () => ({
    isValidContractSearchForm: true,
    contractSearchForm: {
      periodStart: null,
      periodEnd: null,
    }
  }),

  methods: {
    getContractsInPlanPeriodReport() {
      apiGetContractsInPlanPeriodReport(this.contractSearchForm).then((fileData) => {
        sendFileToUser(fileData, CONTRACTS_IN_PLAN_PERIOD_REPORT_FILE_NAME)
      })
    }
  }
}
</script>

<style scoped>

</style>