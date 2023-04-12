import {httpClient} from "@/shared/request";

export const apiCreateCounterparty = (counterparty) => new Promise((resolve, reject) => {
    httpClient.post(`/counterparties`, counterparty)
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})

export const apiGetAllCounterparties = (params) => new Promise((resolve, reject) => {
    httpClient.get(`/counterparties`, {
        params: params
    })
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})

export const apiGetCounterpartyById = (counterpartyId) => new Promise((resolve, reject) => {
    httpClient.get(`/counterparties/${counterpartyId}`)
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})

export const apiUpdateCounterpartyById = (counterpartyId, counterparty) => new Promise((resolve, reject) => {
    httpClient.put(`/counterparties/${counterpartyId}`, counterparty)
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})

export const apiDeleteCounterpartyById = (counterpartyId) => new Promise((resolve, reject) => {
    httpClient.delete(`/counterparties/${counterpartyId}`)
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})