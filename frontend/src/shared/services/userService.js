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

const LOCAl_STORAGE_USER_ID_KEY = 'userId'
export const setUserId = (userId) => {
    localStorage.setItem(LOCAl_STORAGE_USER_ID_KEY, userId)
}

export const clearUserId = () => {
    localStorage.removeItem(LOCAl_STORAGE_USER_ID_KEY)
}

export const getUserId = () => {
    return localStorage.getItem(LOCAl_STORAGE_USER_ID_KEY)
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
                setRole(user.role)
                setUserId(user.id)
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

export const apiCreateUser = (user) => new Promise((resolve, reject) => {
    httpClient.post('/users', user)
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
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

export const apiGetAllUsers = (params) => new Promise((resolve, reject) => {
    httpClient.get('/users', {
        params: params
    })
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})

export const apiGetUserById = (userId) => new Promise((resolve, reject) => {
    httpClient.get(`/users/${userId}`)
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})

export const apiUpdateUserById = (userId, user) => new Promise((resolve, reject) => {
    httpClient.put(`/users/${userId}`, user)
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})

export const apiDeleteUserById = (userId) => new Promise((resolve, reject) => {
    httpClient.delete(`/users/${userId}`)
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})

export const apiUpdateUserAuthSettingsById = (userId, authSettings) => new Promise((resolve, reject) => {
    httpClient.put(`/users/auth-settings/${userId}`, authSettings)
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
            clientSignout()
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})

export const clientSignout = () => {
    tokenService.clearTokens()
    clearRole()
    clearUserId()
}