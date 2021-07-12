const access_token = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZW52ZXJAc3NkbHYuY29tIiwiaXNzIjoiaHR0cDovLzE5Mi4xNjguMS4xNDQ6OTk5OS9sb2dpbiIsImV4cCI6MTYyNjUzMDA5MCwiYXV0aG9yaXRpZXMiOlsiQ0FURUdPUlk6Q1JFQVRFIiwiQ0FURUdPUlk6REVMRVRFIiwiQ0FURUdPUlk6UkVBRCIsIkNBVEVHT1JZOlVQREFURSIsIkpPQjpDUkVBVEUiLCJKT0I6REVMRVRFIiwiSk9COlJFQUQiLCJKT0I6VVBEQVRFIiwiVVNFUjpDUkVBVEUiLCJVU0VSOkRFTEVURSIsIlVTRVI6UkVBRCIsIlVTRVI6VVBEQVRFIl19.aB9NpZeKplNMdhiweapFzgcKgqAF2yFMzHGTN_sWXqs'

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
