/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/layout'

const baseRouter = {
  path: '/base',
  component: Layout,
  redirect: '/base/user',
  meta: {
    title: 'sysManage',
    icon: 'nested',
    roles: ['ROLE_SYSTEM_MANAGE']
  },
  children: [
    {
      path: 'user',
      component: () => import('@/views/base/user/index'), // Parent router-view
      name: 'user',
      meta: { title: 'userManage', icon: 'example', roles: ['ROLE_USER_LIST'] }
    },
    {
      path: 'office',
      name: 'office',
      component: () => import('@/views/base/office/index'),
      meta: { title: 'officeManage', icon: 'example', roles: ['ROLE_OFFICE_LIST'] }
    },
    {
      path: 'role',
      name: 'role',
      component: () => import('@/views/base/role/index'),
      meta: { title: 'roleManage', icon: 'example', roles: ['ROLE_LIST'] }
    },
    {
      path: 'permission',
      name: 'permission',
      component: () => import('@/views/base/permission/index'),
      meta: { title: 'permissionManage', icon: 'example', roles: ['ROLE_PERMISSION_LIST'] }
    }
  ]
}

export default baseRouter
