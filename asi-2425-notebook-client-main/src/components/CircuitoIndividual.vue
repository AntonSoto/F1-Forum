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
        try{
          // Intentamos obtener el circuito desde el backend
          const circuitData = await CircuitRepository.findOne(circuitoId);
          this.circuit = circuitData;
          this.initializeMap();
        }catch{
          console.log("hola")
            // Si no encontramos el circuito en el backend, obtenemos la información desde la API externa
            const response = await fetch(
                  `https://ergast.com/api/f1/circuits/${circuitoId}.json`
              );
              const data = await response.json();
              console.log(data)
  
              const circuitData = data.MRData.CircuitTable.Circuits[0];
              this.circuit = {
                name: circuitData.circuitName,
                location: circuitData.Location.locality,
                country: circuitData.Location.country,
                latitude: circuitData.Location.lat,
                longitude: circuitData.Location.long,
                schedule: "No disponible", // Placeholder
                rating: null, // Placeholder
              };
              this.initializeMap();

              // Después de mostrar la información, guardamos el circuito en el backend
              //await CircuitRepository.save(circuitFromApi);
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