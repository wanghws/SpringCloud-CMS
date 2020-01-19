import request from '@/utils/request'

export function permissionTreeList(data) {
  return request({
    url: '/account/permission/tree',
    method: 'post',
    data
  })
}

export function permissionList(data) {
  return request({
    url: '/account/permission/list',
    method: 'post',
    data
  })
}

export function permissionSave(data) {
  return request({
    url: '/account/permission/save',
    method: 'post',
    data
  })
}

export function permissionStatus(data) {
  return request({
    url: '/account/permission/status',
    method: 'post',
    data
  })
}
