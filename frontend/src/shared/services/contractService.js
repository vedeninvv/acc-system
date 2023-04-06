import {httpClient} from "@/shared/request";

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

export const apiDeleteContract = (id) => new Promise((resolve, reject) => {
    httpClient.delete(`/contracts/${id}`)
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})