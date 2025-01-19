import HTTP from "@/common/http";

const resource = "/pilotos";


export default {
  async findAll() {
    const response = await HTTP.get(`${resource}`);
    return response.data;
  },

  async findByAnoPiloto(ano) {
    try {
      const response = await HTTP.get(`${resource}/ano/${ano}`);
      // Si la respuesta es exitosa, devolver los datos del circuito
      const pilotos = response.data;
      pilotos.sort((a, b) => b.puntos - a.puntos);
      return pilotos;
    } catch (error) {
      // Verificar si el error es un 404 (circuito no encontrado)
      if (error.response && error.response.status === 404) {
        throw new Error(`Ano con ID ${ano} no encontrado`);
      }
      // Si es otro tipo de error (por ejemplo, error en la red), lanzar el error
      throw new Error("Error al obtener los datos del constructor");
    }
  },

  async save(piloto) {
      return (await HTTP.post(`${resource}`, piloto)).data;
  },

  async update(pilotoId, piloto){
    return (await HTTP.put(`${resource}/${pilotoId}`, piloto)).data;
  },

  async delete(pilotoId) {
    return await HTTP.delete(`${resource}/${pilotoId}`);
  },

  async findById(pilotoId) {
    const response = await HTTP.get(`${resource}/${pilotoId}`);
    return response.data;
  },

  async saveImage(id, file) {
    const formData = new FormData();
    formData.append("file", file);
    const response = await HTTP.post(`${resource}/${id}/imagen`, formData, {
      headers: {
        "Content-Type": "multipart/form-data"
      }
    });
    return response.data;
  },

  async deleteImage(id) {
    const response = await HTTP.delete(`${resource}/${id}/imagen`);
    return response.data;
  }

}; 