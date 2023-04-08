import {httpClient} from "@/shared/request";

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

export const apiDeleteContractStageById = (contractId, contractStageId) => new Promise((resolve, reject) => {
    httpClient.delete(`/contracts/${contractId}/stages/${contractStageId}`)
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})
