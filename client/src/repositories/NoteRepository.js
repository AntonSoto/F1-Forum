import HTTP from "@/common/http";

const resource = "notes";

function applyDate(note) {
  note.timestamp = new Date(note.timestamp);
  return note;
}

export default {
  async findAll() {
    const response = await HTTP.get(`${resource}`);
    response.data.forEach(applyDate);
    return response.data;
  },

  async findOne(id) {
    return applyDate((await HTTP.get(`${resource}/${id}`)).data);
  },

  async save(note) {
    if (note.id) {
      return applyDate((await HTTP.put(`${resource}/${note.id}`, note)).data);
    } else {
      return applyDate((await HTTP.post(`${resource}`, note)).data);
    }
  },

  async delete(id) {
    return await HTTP.delete(`${resource}/${id}`);
  }
};
