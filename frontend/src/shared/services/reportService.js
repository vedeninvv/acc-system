import {httpClient} from "@/shared/request";

export const CONTRACT_STAGES_REPORT_FILE_NAME = "stages_report.xls"
export const CONTRACTS_IN_PLAN_PERIOD_REPORT_FILE_NAME = "contracts_in_period.xls"

export const sendFileToUser = (fileData, fileName) => {
    let fileURL = window.URL.createObjectURL(new Blob([fileData]));
    let fileLink = document.createElement('a');
    fileLink.href = fileURL;
    fileLink.setAttribute('download', fileName);
    document.body.appendChild(fileLink);
    fileLink.click();
}

export const apiGetContractStagesReport = (contractId) => new Promise((resolve, reject) => {
    httpClient.get(`/reports/contracts/${contractId}/stages`, {
        responseType: "blob"
    })
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})

export const apiGetContractsInPlanPeriodReport = (planPeriod) => new Promise((resolve, reject) => {
    httpClient.get(`/reports/contracts`, {
        params: planPeriod,
        responseType: "blob"
    })
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})
