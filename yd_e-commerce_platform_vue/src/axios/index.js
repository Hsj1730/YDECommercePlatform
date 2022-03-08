import axios from 'axios'
import router from '../router'
import Element from 'element-ui'
import settings from '../settings'

const isServer = settings.isServer
const serverServerAddress = settings.serverServerAddress
const serverLocalAddress = settings.serverLocalAddress

// axios.defaults.baseURL = isServer ? serverServerAddress : serverLocalAddress

const service = axios.create({
  timeout: 10000, // 设置网络请求超时时间 毫秒
  headers: {
    'Content-Type': 'application/json; charset=utf-8'
  }
})

// 请求拦截器，在请求之前进行拦截
service.interceptors.request.use(config => {
  config.headers.Authorization = localStorage.getItem('token') // 设置请求头带上token
  return config
})

// 设置响应之后的拦截，进行一些请求错误的统一提示
service.interceptors.response.use(response => {
  const res = response.data
  if (res.code === 200) {
    return response
  } else {
    Element.Message.error(!res.msg ? '系统异常' : res.msg)
    return Promise.reject(response.data.msg)
  }
}, error => {
  if (error.response.data) {
    error.massage = error.response.data.msg
  }
  if (error.response.status === 401) {
    router.push('/login').then()
  }
  Element.Message.error(error.massage)
  return Promise.reject(error)
})

export default service
