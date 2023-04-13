import {httpClient} from "@/shared/request";
import * as tokenService from "@/shared/services/tokenService"

const LOCAl_STORAGE_ROLE_KEY = 'role'
export const roles = {
    admin: 'ADMIN',
    user: 'USER'
}
export const setRole = (role) => {
    localStorage.setItem(LOCAl_STORAGE_ROLE_KEY, role)
}

export const clearRole = () => {
    localStorage.removeItem(LOCAl_STORAGE_ROLE_KEY)
}

export const getRole = () => {
    return localStorage.getItem(LOCAl_STORAGE_ROLE_KEY)
}

export const isAuthenticated = async () => {
    return !!tokenService.getRefreshToken();
}

export const apiSignin = user => new Promise((resolve, reject) => {
    httpClient({url: '/auth/signin', data: user, method: 'POST'})
        .then(resp => {
            tokenService.setAccessToken(resp.data.accessToken)
            tokenService.setRefreshToken(resp.data.refreshToken)

            apiGetCurrentUser().then((user) => {
                console.log(user)
                setRole(user.role)
                resolve(resp)
            }).catch((err) => {
                reject(err)
            })
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
            clearRole()
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})