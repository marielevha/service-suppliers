export const state = () => ({
  baseUrl: 'http://localhost:9999/',
  tokens: {
    access: localStorage.getItem('access_token'),
    refresh: localStorage.getItem('refresh_token')
  }
})

export const mutations = {
  increment(state) {
    state.counter++
  }
}

export const getters = {
  getAccessToken: state => {
    return state.tokens.access
  },

  getRefreshToken: state => {
    return state.tokens.refresh
  },
}

export const actions = {

}
