import HTTP from "@/common/http";

const resource = "users";

export default {
  async findAll() {
    return (await HTTP.get(resource)).data;
  },

  async makeAdmin(id) {
    console.log("Hago la petición");
    return (await HTTP.put(`${resource}/${id}/admin`)).data;
  },

  async deleteUser(id) {
    console.log(`Eliminando usuario con ID ${id}`);
    return await HTTP.delete(`${resource}/${id}`);
  }
};
