import {httpClient} from "@/shared/request";

export const apiGetCounterpartyContractById = (contractId, counterpartyContractId) => new Promise((resolve, reject) => {
    httpClient.get(`/contracts/${contractId}/counterparty-contracts/${counterpartyContractId}`)
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})

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

export const apiCreateCounterpartyContract = (contractId, counterpartyContract) =>
    new Promise((resolve, reject) => {
        httpClient.post(`/contracts/${contractId}/counterparty-contracts`, counterpartyContract)
            .then(resp => {
                resolve(resp.data)
            })
            .catch(err => {
                reject(err)
            })
    })

export const apiUpdateCounterpartyContractById = (contractId, counterpartyContractId, counterpartyContract) =>
    new Promise((resolve, reject) => {
        httpClient.put(`/contracts/${contractId}/counterparty-contracts/${counterpartyContractId}`, counterpartyContract)
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