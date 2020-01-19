import request from '@/utils/request'

export function getRoutes() {
  return request({
    url: '/routes',
    method: 'get'
  })
}

export function getRoles() {
  return request({
    url: '/roles',
    method: 'get'
  })
}

export function addRole(data) {
  return request({
    url: '/role',
    method: 'post',
    data
  })
}

export function updateRole(id, data) {
  return request({
    url: `/role/${id}`,
    method: 'put',
    data
  })
}

export function deleteRole(id) {
  return request({
    url: `/role/${id}`,
    method: 'delete'
  })
}

export function roleList(data) {
  return request({
    url: '/account/role/list',
    method: 'post',
    data
  })
}

export function roleSave(data) {
  return request({
    url: '/account/role/save',
    method: 'post',
    data
  })
}

export function roleStatus(data) {
  return request({
    url: '/account/role/status',
    method: 'post',
    data
  })
}

export function rolePermissionSave(data) {
  return request({
    url: '/account/role/permission/save',
    method: 'post',
    data
  })
}

export function roleTreeList() {
  return request({
    url: '/account/role/tree',
    method: 'post'
  })
}

export function currentPermissions(data) {
  return request({
    url: '/account/role/current/permissions',
    method: 'post',
    data
  })
}

