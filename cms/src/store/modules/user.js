import {getInfo, login, logout} from '@/api/user'
import {getToken, removeToken, setToken} from '@/utils/auth'
import router, {resetRouter} from '@/router'
import {Message} from 'element-ui'

const state = {
  token: getToken(),
  name: '',
  officeId: '',
  avatar: '',
  introduction: '',
  roles: []
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_INTRODUCTION: (state, introduction) => {
    state.introduction = introduction
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_OFFICE: (state, name) => {
    state.officeId = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { loginname, password } = userInfo
    return new Promise((resolve, reject) => {
      login({
        username: loginname.trim(),
        password: password,
        grant_type: 'password',
        client_id: 'web',
        client_secret: '%588&!2('
      }).then(response => {
        commit('SET_TOKEN', response.access_token)
        setToken(response.access_token, response.expires_in)
        resolve()
      }).catch(error => {
        Message({
          message: '登录失败',
          type: 'error',
          duration: 5 * 1000
        })
        reject(error)
      })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo().then(response => {
        const { authorities, name, principal } = response

        if (!authorities || authorities.length <= 0) {
          reject('getInfo: roles must be a non-null array!')
        }

        const roles = []
        for (let i = 0; i < authorities.length; i++) {
          roles.push(authorities[i].authority)
        }
        roles.push('home')
        commit('SET_ROLES', roles)
        commit('SET_NAME', principal.loginName)
        commit('SET_OFFICE', principal.officeId)
        commit('SET_AVATAR', '')
        commit('SET_INTRODUCTION', '')
        resolve(principal)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout().then(() => {
        commit('SET_TOKEN', '')
        commit('SET_ROLES', [])
        removeToken()
        resetRouter()
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      commit('SET_ROLES', [])
      removeToken()
      resolve()
    })
  },

  // Dynamically modify permissions
  changeRoles({ commit, dispatch }, role) {
    return new Promise(async resolve => {
      const token = role + '-token'

      commit('SET_TOKEN', token)
      setToken(token)

      const { roles } = await dispatch('getInfo')

      resetRouter()

      // generate accessible routes map based on roles
      const accessRoutes = await dispatch('permission/generateRoutes', roles, { root: true })

      // dynamically add accessible routes
      router.addRoutes(accessRoutes)

      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
