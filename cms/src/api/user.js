import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/passport/oauth/token',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/passport/user',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/passport/user/logout',
    method: 'get'
  })
}

export function userList(data) {
  return request({
    url: '/account/user/list',
    method: 'post',
    data
  })
}

export function userCreate(data) {
  return request({
    url: '/account/user/create',
    method: 'post',
    data
  })
}

export function userUpdate(data) {
  return request({
    url: '/account/user/update',
    method: 'post',
    data
  })
}

export function userStatus(data) {
  return request({
    url: '/account/user/status',
    method: 'post',
    data
  })
}

export function userRoleSave(data) {
  return request({
    url: '/account/user/role/save',
    method: 'post',
    data
  })
}

export function userCurrentRoles(data) {
  return request({
    url: '/account/user/current/roles',
    method: 'post',
    data
  })
}

