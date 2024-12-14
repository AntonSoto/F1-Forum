import HTTP from "@/common/http";

const resource = "users";

export default {
  async findAll() {
    return (await HTTP.get(resource)).data;
  }
};