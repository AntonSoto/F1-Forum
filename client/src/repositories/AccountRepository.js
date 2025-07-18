import HTTP from "../common/http";

export default {
  async authenticate(credentials) {
    return (await HTTP.post(`authenticate`, credentials)).data;
  },

  async getAccount() {
    return (await HTTP.get(`account`)).data;
  },

  async registerAccount(user) {
    return (await HTTP.post(`register`, user)).data;
  },
  async updatePassword(data) {
    return (await HTTP.put(`changePwd`, data)).data;
  }

};
