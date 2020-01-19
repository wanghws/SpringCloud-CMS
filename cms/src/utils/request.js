import axios from 'axios'
import {Message, MessageBox} from 'element-ui'
import store from '@/store'
import {getToken} from '@/utils/auth'

export const gateway = () => {
  if (process.env.NODE_ENV === 'development') {
    return '/'
  } else {
    return process.env.VUE_APP_API_DOMAIN
  }
}

export const json2formData = (json = {}) => {
  const formData = new FormData()
  for (const [key, value] of Object.entries(json)) {
    if (value === undefined) continue
    formData.append(key, value)
  }
  return formData
}

// create an axios instance
const service = axios.create({
  baseURL: gateway(), // api 的 base_url
  withCredentials: true, // 跨域请求时发送 cookies
  timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    let token = getToken();
    if (token){
      config.headers['Authorization'] = 'Bearer '+token;
      return config;
    }
    config.data = json2formData(config.data);
    return config;
  },
  error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  response => {
    if(!response.data){
      return response;
    }
    const res = response.data;
    if(!res.code){
      return res;
    }
    if (res.code === '0') {
      return res;
    }else if(res.code === '400'){
      MessageBox.confirm('你已被登出，可以取消继续留在该页面，或者重新登录', '确定登出', {
        confirmButtonText: '重新登录',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        store.dispatch('user/resetToken').then(() => {
          location.reload()
        })
      })
      return Promise.reject('error');
    }else{
      Message({
        message: '操作失败: ' + res.code+' '+res.message,
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject('error');
    }

  },
  error => {
    let message = "";
    if (error.response.status === 401) {
      message = '登录失败';
    } else if (error.response.status === 400) {
      message = '账号密码错误';
    } else if (error.response.status === 403) {
      message = '没有权限';
    } else if (error.response.status === 500) {
      message = "操作失败: 请稍后再试";
    } else if (error.response.status === 503) {
      message = "操作失败: 服务不可用";
    } else{
      message = '操作失败: '+error;
    }
    Message({
      message: message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
