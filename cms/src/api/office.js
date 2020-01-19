import request from '@/utils/request'

export function officeTreeList(data) {
  return request({
    url: '/account/office/tree',
    method: 'post',
    data
  })
}

export function officeList(data) {
  return request({
    url: '/account/office/list',
    method: 'post',
    data
  })
}

export function officeSave(data) {
  return request({
    url: '/account/office/save',
    method: 'post',
    data
  })
}

export function officeStatus(data) {
  return request({
    url: '/account/office/status',
    method: 'post',
    data
  })
}
