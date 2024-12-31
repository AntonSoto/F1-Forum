<template>
  <div class="circuit-info">
      <h1>{{ circuit.nombreCircuito }}</h1>
      <p><strong>Ubicación:</strong> {{ circuit.localidad }}, {{ circuit.pais }}</p>
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
              id:"",
              nombreCircuito: "",
              localidad: "",
              pais: "",
              latitud: null,
              longitud: null,
          },
      };
  },
  methods: {
      async fetchCircuitData() {
        const circuitoId = this.$route.params.circuitoId; 
        try{

          const circuitData = await CircuitRepository.findOne(circuitoId);
          this.circuit = circuitData;
          this.initializeMap();

        }catch{

            const response = await fetch(
                  `https://ergast.com/api/f1/circuits/${circuitoId}.json`
            );
              const data = await response.json();
              console.log(data)
  
              const circuitData = data.MRData.CircuitTable.Circuits[0];
              this.circuit = {
                id: circuitoId,
                nombreCircuito: circuitData.circuitName,
                localidad: circuitData.Location.locality,
                pais: circuitData.Location.country,
                latitud: circuitData.Location.lat,
                longitud: circuitData.Location.long,
              };

              this.initializeMap();

              // Después de mostrar la información, guardamos el circuito en el backend
              await CircuitRepository.save(this.circuit);
        }

      },
      initializeMap() {
          if (this.circuit.latitud && this.circuit.longitud) {
              const map = L.map("map").setView(
                  [this.circuit.latitud, this.circuit.longitud],
                  13
              );

              L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
                  maxZoom: 19,
              }).addTo(map);

              L.marker([this.circuit.latitud, this.circuit.longitud])
                  .addTo(map)
                  .bindPopup(this.circuit.nombreCircuito)
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