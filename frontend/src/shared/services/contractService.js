import {httpClient} from "@/shared/request";

export const apiCreateContract = (contract) => new Promise((resolve, reject) => {
    httpClient.post('/contracts', contract)
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})

export const apiGetManagingContracts = (params) => new Promise((resolve, reject) => {
    httpClient.get('/contracts/managing', {
        params: params
    })
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})

export const apiGetContractById = (id) => new Promise((resolve, reject) => {
    httpClient.get(`/contracts/${id}`)
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})

export const apiUpdateContractById = (id, contract) => new Promise((resolve, reject) => {
    httpClient.put(`/contracts/${id}`, contract)
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})

export const apiDeleteContract = (id) => new Promise((resolve, reject) => {
    httpClient.delete(`/contracts/${id}`)
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})