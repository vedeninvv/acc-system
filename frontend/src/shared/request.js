import axios from 'axios'
import * as tokenService from '@/shared/services/tokenService'

let config = {
    baseURL: 'http://localhost:8080/api'
};

const httpClient = axios.create(config);

httpClient.interceptors.request.use(config => {
    const accessToken = localStorage.getItem('accessToken')
    if (accessToken) {
        config.headers.Authorization = `Bearer ${accessToken}`;
    }

    return config;
});

httpClient.interceptors.response.use(
    function (response) {
        return response
    },
    function (error) {
        const errorResponse = error.response
        if (isTokenExpiredError(errorResponse)) {
            return resetTokenAndReattemptRequest(error)
        }
        return Promise.reject(error)
    }
)

function isTokenExpiredError(errorResponse) {
    return errorResponse.status === 401 && errorResponse.data.path !== "/api/auth/signin"
}

let isAlreadyFetchingAccessToken = false;

let subscribers = [];

async function resetTokenAndReattemptRequest(error) {
    try {
        const {response: errorResponse} = error;
        const refreshToken = tokenService.getRefreshToken();
        if (!refreshToken) {
            return Promise.reject(error);
        }

        const retryOriginalRequest = new Promise(resolve => {
            addSubscriber(access_token => {
                errorResponse.config.headers.Authorization = 'Bearer ' + access_token;
                resolve(axios(errorResponse.config));
            });
        });
        if (!isAlreadyFetchingAccessToken) {
            isAlreadyFetchingAccessToken = true;
            const response = await axios({
                method: 'post',
                url: `${config.baseURL}/auth/refreshtoken`,
                data: {
                    refreshToken: refreshToken
                }
            });
            if (!response.data) {
                return Promise.reject(error);
            }
            const newToken = response.data.accessToken;
            tokenService.setAccessToken(newToken);
            isAlreadyFetchingAccessToken = false;
            onAccessTokenFetched(newToken);
        }
        return retryOriginalRequest;
    } catch (err) {
        return Promise.reject(err);
    }
}

function onAccessTokenFetched(access_token) {
    subscribers.forEach(callback => callback(access_token));
    subscribers = [];
}

function addSubscriber(callback) {
    subscribers.push(callback);
}

export {httpClient};