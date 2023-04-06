const LOCAL_STORAGE_REFRESH_TOKEN_KEY = 'refreshToken'
const LOCAL_STORAGE_ACCESS_TOKEN_KEY = 'accessToken'

export function getRefreshToken() {
    return localStorage.getItem(LOCAL_STORAGE_REFRESH_TOKEN_KEY)
}

export function setRefreshToken(refreshToken) {
    localStorage.setItem(LOCAL_STORAGE_REFRESH_TOKEN_KEY, refreshToken)
}

export function getAccessToken() {
    return localStorage.getItem(LOCAL_STORAGE_ACCESS_TOKEN_KEY)
}

export function setAccessToken(accessToken) {
    localStorage.setItem(LOCAL_STORAGE_ACCESS_TOKEN_KEY, accessToken)
}

export function clearTokens() {
    localStorage.removeItem(LOCAL_STORAGE_ACCESS_TOKEN_KEY)
    localStorage.removeItem(LOCAL_STORAGE_REFRESH_TOKEN_KEY)
}