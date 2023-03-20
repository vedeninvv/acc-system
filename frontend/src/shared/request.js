import axios from 'axios'

const DEFAULT_SERVER_URL = 'http://localhost:8081'

export const signin = user => new Promise((resolve, reject) => {
    axios({url: DEFAULT_SERVER_URL + '/api/auth/signin', data: user, method: 'POST'})
        .then(resp => {
            const accessToken = resp.data.accessToken
            const refreshToken = resp.data.refreshToken
            axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`
            localStorage.setItem('accessToken', accessToken)
            localStorage.setItem('refreshToken', refreshToken)
            resolve(resp)
        })
        .catch(err => {
            localStorage.removeItem('accessToken')
            localStorage.removeItem('refreshToken')
            reject(err)
        })
})

export const getCurrentUser = () => new Promise((resolve, reject) => {
    axios({url: DEFAULT_SERVER_URL + '/api/users/current', method: 'GET'})
        .then(resp => {
            resolve(resp.data)
        })
        .catch(err => {
            reject(err)
        })
})