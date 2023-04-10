import {httpClient} from "@/shared/request";

export const apiGetAllCounterpartyContracts = (contractId, params) => new Promise((resolve, reject) => {
    httpClient.get(`/contracts/${contractId}/counterparty-contracts`, {
        params: params
    })
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})

export const apiDeleteCounterpartyContractById = (contractId, counterpartyContractId) => new Promise((resolve, reject) => {
    httpClient.delete(`/contracts/${contractId}/counterparty-contracts/${counterpartyContractId}`)
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})