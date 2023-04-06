import {httpClient} from "@/shared/request";
import * as tokenService from "@/shared/services/tokenService"

export const apiSignin = user => new Promise((resolve, reject) => {
    httpClient({url: '/auth/signin', data: user, method: 'POST'})
        .then(resp => {
            tokenService.setAccessToken(resp.data.accessToken)
            tokenService.setRefreshToken(resp.data.refreshToken)

            resolve(resp)
        })
        .catch(err => {
            tokenService.clearTokens()
            reject(err)
        })
})

export const apiGetCurrentUser = () => new Promise((resolve, reject) => {
    httpClient({url: '/users/current', method: 'GET'})
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})

export const apiSignout = () => new Promise((resolve, reject) => {
    httpClient.post(`auth/signout`)
        .then(resp => {
            tokenService.clearTokens()
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})

export const isAuthenticated = async () => {
    if (tokenService.getAccessToken() && tokenService.getRefreshToken()) {
        return apiGetCurrentUser()
            .then(() => {
                return true
            })
            .catch(() => {
                return false
            })
    } else {
        return false
    }
}