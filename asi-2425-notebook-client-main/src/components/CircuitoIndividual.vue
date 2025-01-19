<template>
  <div class="container">
    <div class="left-column">
      <h1>{{ circuit.name }}</h1>
      <p><strong>Ubicación:</strong> {{ circuit.location }}, {{ circuit.country }}</p>

      <p><strong>Horarios:</strong></p>
      <ul>
        <li><strong>Libres 1:</strong> {{ formatFecha(circuit.fechaHoraLibres1) }}</li>
        <li><strong>Libres 2:</strong> {{ formatFecha(circuit.fechaHoraLibres2) }}</li>
        <li><strong>Libres 3:</strong> {{ formatFecha(circuit.fechaHoraLibres3) }}</li>
      </ul>

      <ul><strong>Clasificación:</strong> {{ formatFecha(circuit.fechaHoraClasificacion) }}</ul>
      <ul><strong>Sprint:</strong> {{ formatFecha(circuit.fechaHoraSprint) }}</ul>
      <ul><strong>Carrera:</strong> {{ formatFecha(circuit.fechaHoraCarrera) }}</ul>

      <!-- Nuevo botón -->
      <button
        class="toggle-status"
        :class="{ 'not-viewed': !isViewed, 'viewed': isViewed }"
        @click="toggleViewStatus"
      >
        {{ isViewed ? 'Visto' : 'No Visto' }}
      </button>
    </div>
    <div class="rigth-column">
      <div id="map" class="map-container"></div>
    </div>
  </div>

  <div class="valoraciones">
    <h2>Valoraciones</h2>
    <!-- Formulario para añadir valoración -->
    <div class="add-valoracion">
      <textarea v-model="nuevaValoracion.comentario" placeholder="Escribe tu valoración aquí..." rows="4"></textarea>
      <select v-model="nuevaValoracion.puntuacion">
        <option disabled value="">Selecciona una puntuación</option>
        <option v-for="puntuacion in [1, 2, 3, 4, 5]" :key="puntuacion" :value="puntuacion">
          {{ puntuacion }}
        </option>
      </select>
      <button @click="crearValoracion">Añadir Valoración</button>
    </div>

    <!-- Lista de valoraciones -->
    <div class="lista-valoraciones" v-if="valoraciones.length > 0">
      <h3>Valoraciones del circuito</h3>
      <ul>
        <li v-for="(valoracion, index) in valoraciones" :key="valoracion.id">
          <strong>{{ valoracion.user }}</strong>
          <strong>{{ formatFecha(valoracion.fechaValoracion) }}</strong>
          <span class="puntuacion">Puntuación: {{ valoracion.puntuacion }}</span>
          <p>{{ valoracion.comentario }}</p>
          <button @click="editarValoracion(valoracion)" v-if="valoracion.user === user.login">Editar</button>
          <button @click="eliminarValoracion(valoracion.id)" v-if="valoracion.user === user.login">Eliminar</button>
          <button @click="deleteUser(valoracion.idUser)" v-if="admin()">Eliminar Usuario</button>
        </li>
      </ul>
    </div>
    <p v-else>Este circuito aún no tiene valoraciones. ¡Sé el primero en opinar!</p>
  </div>
</template>



<script>
import Swal from 'sweetalert2';
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import CircuitRepository from "@/repositories/CircuitRepository";
import ValoracionRepository from "@/repositories/ValoracionRepository";
import AccountRepository from "@/repositories/AccountRepository";
import UserRepository from '@/repositories/UserRepository';
import auth from "@/common/auth";
import VisualizaRepository from '@/repositories/VisualizaRepository';

export default {
  data() {
    return {
      user: {
        id: null,
        login: "",
      },
      circuit: {
        name: "",
        location: "",
        country: "",
        latitude: null,
        longitude: null,
        rating: null,
        grandesPremios: {
          fechaHoraLibres1: "",
          fechaHoraLibres2: "",
          fechaHoraLibres3: "",
          fechaHoraClasificacion: "",
          fechaHoraCarrera: "",
          fechaHoraSprint: "",
          granPremioId: null,
        },
      },
      valoraciones: [], // Lista de valoraciones
      nuevaValoracion: {
        comentario: "",
        puntuacion: "",
        granPremio: "", // Nueva propiedad para la puntuación
      },
      isEditing: false, // Control para mostrar el formulario de edición
      editarValoracionForm: {
        id: null,
        comentario: "",
        puntuacion: "",
      },
      isViewed: false,
      idVisualizacion: null,
    };
  },
  async mounted() {
    // Obtiene los datos del usuario actual
    const fetchedUser = await AccountRepository.getAccount();
    this.user = {
      userId: fetchedUser.id,
      login: fetchedUser.login,

    };
    await this.fetchCircuitData();

    const circuitoId = this.$route.params.circuitoId;
    const circuitData = await CircuitRepository.findOne(circuitoId);
    const granPremioId = circuitData.grandesPremios?.[0]?.id;

    try {
      const visualizacion = await VisualizaRepository.findOneByUserAndGP(granPremioId);
      console.log("TESTEANDO ANDO",visualizacion)
      //if(visualizacion.response)
      if(visualizacion.data.length>=1) this.isViewed = true;
    } catch (error) {
      console.error("Error al verificar la visualización:", error.message);
      this.isViewed = false; // Si ocurre un error, el estado es false
    }

  },
  methods: {
    toggleViewStatus() {
      this.isViewed = !this.isViewed;
    },
    admin() {
      return auth.isAdmin();
    },
    async toggleViewStatus() {
    const circuitoId = this.$route.params.circuitoId;
    const circuitData = await CircuitRepository.findOne(circuitoId);
    const granPremioId = circuitData.grandesPremios?.[0]?.id;


    if (this.isViewed) {
      // Eliminar visualización
      try {
        await VisualizaRepository.delete( this.idVisualizacion);
        this.isViewed = false; // Actualizar el estado a no visto
        Swal.fire("Éxito", "Se ha eliminado la visualización.", "success");
      } catch (error) {
        console.error("Error al eliminar la visualización:", error.message);
        Swal.fire("Error", "No se pudo eliminar la visualización.", "error");
      }
    } else {
      // Crear visualización
      try {
        const visualizacionCompleta = await VisualizaRepository.save({ gpId: granPremioId });
        this.idVisualizacion = visualizacionCompleta.data.id;
        this.isViewed = true; // Actualizar el estado a visto
        console.log(this.idVisualizacion)
        
        Swal.fire("Éxito", "Se ha marcado como visto.", "success");
      } catch (error) {
        console.error("Error al guardar la visualización:", error.message);
        Swal.fire("Error", "No se pudo guardar la visualización.", "error");
      }
    }
  },

    async deleteUser(id) {
      Swal.fire({
        title: "¿Estás seguro?",
        text: "Esta acción eliminará al usuario y todas sus valoraciones.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "Eliminar"
      }).then(async (result) => {
        if (result.isConfirmed) {
          try {
            await UserRepository.deleteUser(id);
            Swal.fire({
              title: "Eliminado",
              text: "El usuario ha sido eliminado.",
              icon: "success",
              timer: 2000
            });
            const circuitoId = this.$route.params.circuitoId;
            this.fetchValoraciones(circuitoId); // Recargar las valoraciones
          } catch (err) {
            console.error(err);
            Swal.fire({
              title: "Error",
              text: "No se pudo eliminar al usuario.",
              icon: "error"
            });
          }
        }
      });

    },
    async editarValoracion(valoracion) {
    this.isEditing = valoracion.id; // Mostrar el formulario de edición para este comentario
    this.editarValoracionForm.id = valoracion.id;
    this.editarValoracionForm.comentario = valoracion.comentario;
    this.editarValoracionForm.puntuacion = valoracion.puntuacion;
  },
  async actualizarValoracion(valoracionId) {
    try {
      if (!this.editarValoracionForm.comentario.trim()) {
        alert("El texto de la valoración no puede estar vacío.");
        return;
      }
      if (!this.editarValoracionForm.puntuacion) {
        alert("Debe seleccionar una puntuación.");
        return;
      }

      const circuitoId = this.$route.params.circuitoId;
      await ValoracionRepository.update(valoracionId, this.editarValoracionForm);

      this.isEditing = false; // Cerrar el formulario
      this.fetchValoraciones(circuitoId); // Recargar las valoraciones
    } catch (error) {
      console.error("Error al actualizar la valoración:", error.message);
    }
  },
    async eliminarValoracion(valoracionId) {
      try {
        const confirm = window.confirm("¿Estás seguro de que deseas eliminar esta valoración?");
        if (!confirm) return;

        const circuitoId = this.$route.params.circuitoId;
        await ValoracionRepository.delete(valoracionId);  // Suponiendo que ValoracionRepository tiene un método delete

        // Recargar las valoraciones después de eliminar
        this.fetchValoraciones(circuitoId);
      } catch (error) {
        console.error("Error al eliminar la valoración:", error.message);
      }
    },
    async fetchCircuitData() {
      const circuitoId = this.$route.params.circuitoId;
      try {
        const circuitData = await CircuitRepository.findOne(circuitoId);

        this.circuit = {
          name: circuitData.nombreCircuito || "Sin nombre",
          location: circuitData.localidad || "Sin localidad",
          country: circuitData.pais || "Sin país",
          latitude: circuitData.latitud || null,
          longitude: circuitData.longitud || null,
          rating: null,
          fechaHoraLibres1: circuitData.grandesPremios?.[0]?.fechaHoraLibres1,
          fechaHoraLibres2: circuitData.grandesPremios?.[0]?.fechaHoraLibres2,
          fechaHoraLibres3: circuitData.grandesPremios?.[0]?.fechaHoraLibres3,
          fechaHoraClasificacion: circuitData.grandesPremios?.[0]?.fechaHoraClasificacion,
          fechaHoraCarrera: circuitData.grandesPremios?.[0]?.fechaHoraCarrera,
          fechaHoraSprint: circuitData.grandesPremios?.[0]?.fechaHoraSprint,
          fechaHoraSprint: circuitData.grandesPremios?.[0]?.granPremioId
        };

        this.initializeMap();
        this.fetchValoraciones(circuitoId);
      } catch (error) {
        console.error("Hubo un error al obtener los datos del circuito:", error);
      }
    },
    async fetchValoraciones(circuitoId) {
      try {
        const circuitData = await CircuitRepository.findOne(circuitoId);
        const granPremioId = circuitData.grandesPremios?.[0]?.id
        this.valoraciones = await ValoracionRepository.findAllByGranPremio(granPremioId);
        
      } catch (error) {
        console.error("Error al obtener las valoraciones:", error.message);
      }
    },
    async crearValoracion() {
      if (!this.nuevaValoracion.comentario.trim()) {
        alert("El texto de la valoración no puede estar vacío.");
        return;
      }
      if (!this.nuevaValoracion.puntuacion) {
        alert("Debe seleccionar una puntuación.");
        return;
      }

      try {
        const circuitoId = this.$route.params.circuitoId;
        const circuitData = await CircuitRepository.findOne(circuitoId);

        const nuevaValoracion = {
          comentario: this.nuevaValoracion.comentario,
          puntuacion: this.nuevaValoracion.puntuacion, // Incluir puntuación
          granPremio: circuitData.grandesPremios?.[0]?.id, // Asociar la valoración al circuito
        };

        await ValoracionRepository.create(nuevaValoracion);
        this.nuevaValoracion.comentario = ""; // Limpiar el campo
        this.nuevaValoracion.puntuacion = ""; // Limpiar el campo
        this.fetchValoraciones(circuitoId); // Refrescar la lista de valoraciones
      } catch (error) {
        console.error("Error al añadir la valoración:", error.message);
      }
    },
    initializeMap() {
      if (this.circuit.latitude && this.circuit.longitude) {
        const map = L.map("map").setView([this.circuit.latitude, this.circuit.longitude], 13);

        L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
          maxZoom: 19,
        }).addTo(map);

        L.marker([this.circuit.latitude, this.circuit.longitude])
          .addTo(map)
          .bindPopup(this.circuit.name)
          .openPopup();
      }
    },
    formatFecha(fecha) {
      if (fecha) {
        const date = new Date(fecha); // Convierte la fecha a un objeto Date
        return date.toLocaleString("es-ES", { day: 'numeric', month: 'long', year: 'numeric', hour: '2-digit', minute: '2-digit' });
        // Cambia el formato a '22 de enero de 2025, 14:30'
      }
      return "No disponible";
    }
  },
};
</script>


<style scoped>
.circuit-info {
  padding: 20px;
}

.map-container {
  width: 100%;
  height: 500px;
  margin-top: 20px;
  border: 3px solid #e55858;
}

/* Container que divide la pantalla en dos columnas */
.container {
  display: flex;
  flex-direction: row;
  width: 100%;
  height: 60vh;
}

/* Columna izquierda para el mapa */
.rigth-column {
  flex: 2;
  /* Esto asegura que se ocupe la mitad */
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
}

/* Columna derecha para la información con altura fija */
.left-column {
  flex: 1;
  /* Esto asegura que se ocupe la mitad */
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px;
}

.valoraciones {
  padding: 20px;
  margin-top: 20px;
  border-top: 1px solid #ddd;
}

.add-valoracion {
  margin-bottom: 20px;
}

.add-valoracion textarea {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  margin-bottom: 10px;
}

.add-valoracion button {
  padding: 10px 20px;
  font-size: 16px;
  background-color: #ee5151;
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 5px;
}

.add-valoracion button:hover {
  background-color: #b3272e;
}

.lista-valoraciones ul {
  list-style-type: none;
  padding: 0;
}

.lista-valoraciones li {
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.lista-valoraciones li strong {
  display: block;
  margin-bottom: 5px;
}

.add-valoracion select {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  margin-bottom: 10px;
}

.lista-valoraciones .puntuacion {
  font-weight: bold;
  color: #ee5151;
}
.edit-valoracion {
  padding: 20px;
  margin-top: 20px;
  border-top: 1px solid #ddd;
}

.edit-valoracion textarea {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  margin-bottom: 10px;
}

.edit-valoracion select {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  margin-bottom: 10px;
}

.edit-valoracion button {
  padding: 10px 20px;
  font-size: 16px;
  background-color: #ee5151;
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 5px;
}

.edit-valoracion button:hover {
  background-color: #b3272e;
}
.toggle-status {
  padding: 10px 20px;
  font-size: 16px;
  border: none;
  cursor: pointer;
  border-radius: 5px;
  color: white;
  margin-top: 20px;
}

.toggle-status.not-viewed {
  background-color: red;
}

.toggle-status.viewed {
  background-color: green;
}
</style>
