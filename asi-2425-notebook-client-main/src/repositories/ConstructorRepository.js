import HTTP from "@/common/http";

const resource = "/constructores";


export default {

  async findByAno(ano) {
    try {
      const response = await HTTP.get(`${resource}/ano/${ano}`);
      // Si la respuesta es exitosa, devolver los datos del circuito
      const constructores = response.data;
      constructores.sort((a, b) => b.puntos - a.puntos);
      return constructores;
    } catch (error) {
      // Verificar si el error es un 404 (circuito no encontrado)
      if (error.response && error.response.status === 404) {
        throw new Error(`Ano con ID ${ano} no encontrado`);
      }
      // Si es otro tipo de error (por ejemplo, error en la red), lanzar el error
      throw new Error("Error al obtener los datos del constructor");
    }
  },

  async save(constructor) {
      return (await HTTP.post(`${resource}`, constructor)).data;
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
  },

  async findById(id) {
    const response = await HTTP.get(`${resource}/${id}`);
    return response.data;
  }



}; 