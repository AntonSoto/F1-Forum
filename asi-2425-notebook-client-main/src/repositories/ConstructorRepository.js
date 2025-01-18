import HTTP from "@/common/http";

const resource = "/constructores";


export default {

  async findByAno(ano) {
    try {
      const response = await HTTP.get(`${resource}/${ano}`);
      
      // Si la respuesta es exitosa, devolver los datos del circuito
      return response.data;
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
    console.log("AQUI CABRON", constructor)
      return (await HTTP.post(`${resource}`, constructor)).data;
  },
}; 