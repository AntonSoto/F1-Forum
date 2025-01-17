import HTTP from "@/common/http";

const resource = "/circuitos";


export default {
  async findAll() {
    const response = await HTTP.get(`${resource}`);
    return response.data;
  },

  async findOne(circuitoId) {
    try {
      const response = await HTTP.get(`${resource}/${circuitoId}`);
      console.log(response.data)
      // Si la respuesta es exitosa, devolver los datos del circuito
      return response.data;
    } catch (error) {
      // Verificar si el error es un 404 (circuito no encontrado)
      if (error.response && error.response.status === 404) {
        throw new Error(`Circuito con ID ${circuitoId} no encontrado`);
      }
      // Si es otro tipo de error (por ejemplo, error en la red), lanzar el error
      throw new Error("Error al obtener los datos del circuito");
    }
  },

  async save(circuit) {
    /*if (circuit.circuitoId) {
      return (await HTTP.put(`${resource}/${circuit.circuitoId}`, circuit)).data;
    } else {
      */
    return (await HTTP.post(`${resource}`, circuit)).data;
    //}
  },

  async delete(circuitoId) {
    return await HTTP.delete(`${resource}/${circuitoId}`);
  }
}; 