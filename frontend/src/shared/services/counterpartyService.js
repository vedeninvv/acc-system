import {httpClient} from "@/shared/request";

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