<template>
  <div class="container">
    <div class="left-column">
      <h1>{{ circuit.name }}</h1>
      <p><strong>Ubicación:</strong> {{ circuit.location }}, {{ circuit.country }}</p>

      <p><strong>Horarios:</strong></p>
      <ul>
        <li><strong>Libres 1:</strong> {{ circuit.fechaHoraLibres1 }}</li>
        <li><strong>Libres 2:</strong> {{ circuit.fechaHoraLibres2 }}</li>
        <li><strong>Libres 3:</strong> {{ circuit.fechaHoraLibres3 }}</li>
      </ul>
      
      <ul><strong>Clasificación:</strong> {{ circuit.fechaHoraClasificacion }}</ul>
      <ul><strong>Sprint:</strong> {{ circuit.fechaHoraSprint }}</ul>
      <ul><strong>Carrera:</strong> {{ circuit.fechaHoraCarrera }}</ul>
    </div>
    <div class="rigth-column">
      <div id="map" class="map-container"></div>
    </div>
    
  </div>
  <div class="valoraciones">
    <h2>Valoraciones</h2> 
    <p>{{ circuit.rating || "Sin valoraciones" }}</p>
  </div>
</template>


<script>
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import CircuitRepository from "@/repositories/CircuitRepository";

export default {
  data() {
  return {
    circuit: {
      name: "",
      location: "",
      country: "",
      latitude: null,
      longitude: null,
      rating: null, // Placeholder
      grandesPremios: {
        fechaHoraLibres1: "",
        fechaHoraLibres2: "",
        fechaHoraLibres3: "",
        fechaHoraClasificacion: "",
        fechaHoraCarrera: "",
        fechaHoraSprint: "",
      }
    },
  };
},
  methods: {
    async fetchCircuitData() {
      const circuitoId = this.$route.params.circuitoId;
      try {
        // Intentamos obtener el circuito desde el backend
        const circuitData = await CircuitRepository.findOne(circuitoId);

        this.circuit = {
          name: circuitData.nombreCircuito || "Sin nombre",
          location: circuitData.localidad || "Sin localidad",
          country: circuitData.pais || "Sin país",
          latitude: circuitData.latitud || null,
          longitude: circuitData.longitud || null,
          rating: null, // Este valor podría llenarse en otro momento
          fechaHoraLibres1: circuitData.grandesPremios?.[0]?.fechaHoraLibres1 || "No disponible",
          fechaHoraLibres2: circuitData.grandesPremios?.[0]?.fechaHoraLibres2 || "No disponible",
          fechaHoraLibres3: circuitData.grandesPremios?.[0]?.fechaHoraLibres3 || "No disponible",
          fechaHoraClasificacion: circuitData.grandesPremios?.[0]?.fechaHoraClasificacion || "No disponible",
          fechaHoraCarrera: circuitData.grandesPremios?.[0]?.fechaHoraCarrera || "No disponible",
          fechaHoraSprint: circuitData.grandesPremios?.[0]?.fechaHoraSprint || "Esta carrera no cuenta con Sprint",
        };

        this.initializeMap();
      } catch (error) {
        console.error("Hubo un error al obtener los datos del circuito:", error);
      }
    },
    initializeMap() {
      if (this.circuit.latitude && this.circuit.longitude) {
        const map = L.map("map").setView(
          [this.circuit.latitude, this.circuit.longitude],
          13
        );

        L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
          maxZoom: 19,
        }).addTo(map);

        L.marker([this.circuit.latitude, this.circuit.longitude])
          .addTo(map)
          .bindPopup(this.circuit.name)
          .openPopup();
      }
    },
  },
  async mounted() {
    await this.fetchCircuitData();
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
  flex: 2; /* Esto asegura que se ocupe la mitad */
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
}

/* Columna derecha para la información con altura fija */
.left-column {
  flex: 1; /* Esto asegura que se ocupe la mitad */
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px;  
}
.valoraciones {
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;

}
</style>
