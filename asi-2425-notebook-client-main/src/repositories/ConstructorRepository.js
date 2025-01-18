import HTTP from "@/common/http";

const resource = "/constructores";


export default {

  async findByAno(ano) {
    try {
    console.log("BUSCANDO CONSTRUCTOR POR AÑO")
      const response = await HTTP.get(`${resource}/${ano}`);
      console.log("RECUPERADOS CONSTRUCTOR POR AÑO")
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
    console.log("GUARDANDO CONSTRUCTOR")
      return (await HTTP.post(`${resource}`, constructor)).data;
  },
}; 