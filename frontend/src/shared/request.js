import axios from 'axios'

export const login = user => new Promise((resolve, reject) => {
    axios({url: 'http://localhost:8080/api/auth/signin', data: user, method: 'POST'})
        .then(resp => {
            const accessToken = resp.data.accessToken
            const refreshToken = resp.data.refreshToken
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
