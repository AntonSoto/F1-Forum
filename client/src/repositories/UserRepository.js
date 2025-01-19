import HTTP from "@/common/http";

const resource = "users";

export default {
  async findAll() {
    return (await HTTP.get(resource)).data;
  },

  async makeAdmin(id) {
    return (await HTTP.put(`${resource}/${id}/admin`)).data;
  },

  async deleteUser(id) {
    return await HTTP.delete(`${resource}/${id}`);
  }
};
