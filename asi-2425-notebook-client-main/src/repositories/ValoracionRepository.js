import HTTP from "@/common/http";

const resource = "/valoraciones";

export default {
  async create(valoracion) {
    try {
      const response = await HTTP.post(`${resource}`, valoracion);
      return response.data;
    } catch (error) {
      throw new Error("Error al crear la valoración: " + (error.response?.data?.message || error.message));
    }
  },

  async update(id, valoracion) {
    try {
      if (id !== valoracion.id) {
        throw new Error("El ID proporcionado no coincide con el ID de la valoración.");
      }
      const response = await HTTP.put(`${resource}/${id}`, valoracion);
      return response.data;
    } catch (error) {
      throw new Error("Error al actualizar la valoración: " + (error.response?.data?.message || error.message));
    }
  },

  async delete(id) {
    try {
      await HTTP.delete(`${resource}/${id}`);
    } catch (error) {
      throw new Error("Error al eliminar la valoración: " + (error.response?.data?.message || error.message));
    }
  },

  async findAllByGranPremio(granPremioId) {
    try {
      const response = await HTTP.get(`${resource}/granPremio/${granPremioId}`);
      const valoraciones = response.data;
  
      // Ordenar las valoraciones por la fecha más reciente (de mayor a menor)
      valoraciones.sort((a, b) => new Date(b.fechaValoracion) - new Date(a.fechaValoracion));
        console.log("EEEEEEEE",valoraciones)
      return valoraciones;
    } catch (error) {
      throw new Error("Error al obtener las valoraciones del gran premio: " + (error.response?.data?.message || error.message));
    }
  },

  async findAllByUser() {
    try {
      const response = await HTTP.get(`${resource}/usuario`);
      return response.data;
    } catch (error) {
      throw new Error("Error al obtener las valoraciones del usuario: " + (error.response?.data?.message || error.message));
    }
  },
};
