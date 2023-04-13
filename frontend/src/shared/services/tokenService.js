const LOCAL_STORAGE_REFRESH_TOKEN_KEY = 'refreshToken'
const LOCAL_STORAGE_ACCESS_TOKEN_KEY = 'accessToken'

function parseJwt(token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}

function isTokenExpired(token) {
    let tokenExpiringDate = new Date(parseJwt(token).exp);
    return new Date() <= tokenExpiringDate;
}

export function getRefreshToken() {
    let refreshToken = localStorage.getItem(LOCAL_STORAGE_REFRESH_TOKEN_KEY)
    if (isTokenExpired(refreshToken)) {
        return null
    }
    return refreshToken
}

export function setRefreshToken(refreshToken) {
    localStorage.setItem(LOCAL_STORAGE_REFRESH_TOKEN_KEY, refreshToken)
}

export function getAccessToken() {
    let accessToken = localStorage.getItem(LOCAL_STORAGE_ACCESS_TOKEN_KEY)
    if (isTokenExpired(accessToken)) {
        return null
    }
    return accessToken
}

export function setAccessToken(accessToken) {
    localStorage.setItem(LOCAL_STORAGE_ACCESS_TOKEN_KEY, accessToken)
}

export function clearTokens() {
    localStorage.removeItem(LOCAL_STORAGE_ACCESS_TOKEN_KEY)
    localStorage.removeItem(LOCAL_STORAGE_REFRESH_TOKEN_KEY)
}