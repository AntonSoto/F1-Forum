<template>
  <div class="circuit-info">
      <h1>{{ circuit.name }}</h1>
      <p><strong>Ubicación:</strong> {{ circuit.location }}, {{ circuit.country }}</p>
      <p><strong>Horarios:</strong> {{ circuit.schedule }}</p>
      <p><strong>Valoraciones:</strong> {{ circuit.rating || "Sin valoraciones" }}</p>

      <div id="map" class="map-container"></div>
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
              schedule: "No disponible", // Placeholder
              rating: null, // Placeholder
          },
      };
  },
  methods: {
      async fetchCircuitData() {
        const circuitoId = this.$route.params.circuitoId; 
        console.log(circuitoId)
        try{
          // Intentamos obtener el circuito desde el backend
          const circuitData = await CircuitRepository.findOne(circuitoId);
          console.log("aqui")
          this.circuit = {
                name: circuitData.nombreCircuito,
                location: circuitData.localidad,
                country: circuitData.pais,
                latitude: circuitData.latitud,
                longitude: circuitData.longitud,
                schedule: "No disponible",
                rating: null, 
              };

              console.log("aqui")
          this.initializeMap();
          console.log("aqui2")
        }catch{
          
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
  height: 400px;
  margin-top: 20px;
  border: 1px solid #ccc;
}
</style>