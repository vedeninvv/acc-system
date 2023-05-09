import axios from 'axios'
import * as tokenService from '@/shared/services/tokenService'
import router from "@/router";

let config = {
    baseURL: `http://${document.location.host.split(':')[0]}:8080/api`
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
        if (isGettingNotFound(error)) {
            return router.push("/404").then(() => {
                return Promise.reject(error)
            }).catch(() => {
                return Promise.reject(error)
            })
        }
        if (isGettingResourceForbidden(error)) {
            return router.push("/403").then(() => {
                return Promise.reject(error)
            }).catch(() => {
                return Promise.reject(error)
            })
        }
        if (isTokenExpiredError(error.response)) {
            return resetTokenAndReattemptRequest(error)
        }
        return Promise.reject(error)
    }
)

function isTokenExpiredError(errorResponse) {
    return errorResponse.status === 401 && errorResponse.data.path !== "/api/auth/signin"
}

function isGettingResourceForbidden(error) {
    return error.response.status === 403 && error.response.config.method === "get"
}

function isGettingNotFound(error) {
    return error.response.status === 404 && error.response.config.method === "get"
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
            }).catch(async (err) => {
                return Promise.reject(err);
            })
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
        await router.push("/signin")
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