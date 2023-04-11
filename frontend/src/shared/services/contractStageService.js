import {httpClient} from "@/shared/request";

export const apiGetContractStageById = (contractId, contractStageId) => new Promise((resolve, reject) => {
    httpClient.get(`/contracts/${contractId}/stages/${contractStageId}`)
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})

export const apiGetAllContractStagesByContractId = (id, params) => new Promise((resolve, reject) => {
    httpClient.get(`/contracts/${id}/stages`, {
        params: params
    })
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})

export const apiCreateContractStage = (contractId, contractStage) =>
    new Promise((resolve, reject) => {
        httpClient.post(`/contracts/${contractId}/stages`, contractStage)
            .then(resp => {
                resolve(resp.data)
            })
            .catch(err => {
                reject(err)
            })
    })

export const apiUpdateContractStageById = (contractId, contractStageId, contractStage) =>
    new Promise((resolve, reject) => {
        httpClient.put(`/contracts/${contractId}/stages/${contractStageId}`, contractStage)
            .then(resp => {
                resolve(resp.data)
            })
            .catch(err => {
                reject(err)
            })
    })

export const apiDeleteContractStageById = (contractId, contractStageId) => new Promise((resolve, reject) => {
    httpClient.delete(`/contracts/${contractId}/stages/${contractStageId}`)
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})
