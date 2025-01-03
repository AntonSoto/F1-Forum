import HTTP from "@/common/http";

const resource = "/pilotos";


export default {
  async findAll() {
    const response = await HTTP.get(`${resource}`);
    return response.data;
  },

  async findOne(pilotoId) {
    try {
      const response = await HTTP.get(`${resource}/${pilotoId}`);
      
      // Si la respuesta es exitosa, devolver los datos del circuito
      return response.data;
    } catch (error) {
      // Verificar si el error es un 404 (circuito no encontrado)
      if (error.response && error.response.status === 404) {
        throw new Error(`Piloto con ID ${pilotoId} no encontrado`);
      }
      // Si es otro tipo de error (por ejemplo, error en la red), lanzar el error
      throw new Error("Error al obtener los datos del circuito");
    }
  },

  async save(piloto) {
    if (piloto.pilotoId) {
      return (await HTTP.put(`${resource}/${piloto.pilotoId}`, piloto)).data;
    } else {
      return (await HTTP.post(`${resource}`, circuit)).data;
    }
  },

  async delete(pilotoId) {
    return await HTTP.delete(`${resource}/${pilotoId}`);
  }
}; 