import HTTP from "@/common/http";

const resource = "/circuitos";


export default {
  async findAll() {
    /*const response = (await HTTP.get(resource));
    console.log(response)
    return response.data*/

    return await HTTP.get(resource)
  },
  async findAllByAno(ano) {
    /*const response = (await HTTP.get(resource));
    console.log(response)
    return response.data*/

    return await HTTP.get(`${resource}/ano/${ano}`)
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
     //console.log("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
    console.log(circuit)
    return (await HTTP.post(`${resource}`, circuit)).data;
    //}
  },

  async delete(circuitoId) {
    return await HTTP.delete(`${resource}/${circuitoId}`);
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