import HTTP from "@/common/http";

const resource = "users";

export default {
  async findAll() {
    return (await HTTP.get(resource)).data;
  },

  async makeAdmin(id) {
    console.log("Hago la petición")
    return (await HTTP.put(`${resource}/${id}/admin`)).data;
  }

};