const access_token = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZW52ZXJAc3NkbHYuY29tIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo5OTk5L2xvZ2luIiwibmFtZSI6IkNhcm9scyBERU5WRVIiLCJpZCI6MSwiZXhwIjoxNjMzMTY4MzIyLCJzbHVnIjoiY2Fyb2xzLWRlbnZlciIsImF1dGhvcml0aWVzIjpbIkNBVEVHT1JZOkNSRUFURSIsIkNBVEVHT1JZOkRFTEVURSIsIkNBVEVHT1JZOlJFQUQiLCJDQVRFR09SWTpVUERBVEUiLCJKT0I6Q1JFQVRFIiwiSk9COkRFTEVURSIsIkpPQjpSRUFEIiwiSk9COlVQREFURSIsIk9GRkVSOkNSRUFURSIsIk9GRkVSOkRFTEVURSIsIk9GRkVSOlJFQUQiLCJPRkZFUjpVUERBVEUiXX0.QQ0-70lNdKZe3iAPRnqlsJSWMFCJ__ryghnOTwJvdi4'

export const state = () => ({
  counter: 0,
  headers: {
    'Authorization': `Bearer ${access_token}`,
    'Content-Type': 'application/json'
  },
  baseUrl: 'http://localhost:9998/'
})

export const mutations = {
  increment(state) {
    state.counter++
  }
}
