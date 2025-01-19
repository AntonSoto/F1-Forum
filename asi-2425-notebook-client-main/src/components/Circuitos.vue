<template>
  <div class="circuitos-container">
    <h2 class="circuitos-title">Circuitos</h2>
    <!-- Carrusel de imágenes -->
    <div class="carousel-container">
      <button class="carousel-button prev" @click="prevImage()">‹</button>
      <div v-if=" this.circuitos != null" class="carousel">
        <a class="carousel" :href="'/circuitos/' + circuitos[currentIndex]?.id">
        <img class="carousel-image" :src="currentCircuitoImage"
          :alt="`Imagen de ${circuitos[currentIndex]?.id || 'circuito'}`"
        />
        </a>
      </div>
      <button class="carousel-button next" @click="nextImage">›</button>
    </div>
    <!-- Indicadores del carrusel -->
    <div class="indicator">
      <span
        v-for="(circuito, index) in circuitos"
        :key="circuito.id"
        class="dot"
        :class="{ active: index === currentIndex }"
        @click="goToImage(index)"
      ></span>
    </div>
    <!-- Lista de circuitos -->
    <div class="circuitos-list">
      <h3>Todos los Circuitos</h3>
      <div class="circuitos-list-container">
        <div class="circuitos-row">
          <!-- Iterar sobre circuitos -->
          <div class="circuito-item" v-for="(circuito) in circuitos" :key="circuito.id">
            <a :href="'/circuitos/' + circuito.id" class="circuito-link-item">
              <span class="circuito-name">{{ circuito.nombreCircuito }}</span>
              <img
                class="card-img-top circuito-thumbnail"
                :src="circuito.tieneImagen
                  ? `http://localhost:8080/api/circuitos/${circuito.id}/imagen`
                  : '/placeholder.png'"
                :alt="`Imagen de ${circuito.nombreCircuito}`"
              />
            </a>
            <!-- Botón para editar imagen -->
            <router-link
              v-if="admin()"
              :to="{ name: 'EditCircuitImage', params: { circuitoId: circuito.id } }"
              class="edit-button"
            >
              Editar Imagen
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>



<script>

import auth from "@/common/auth";
import CampeonatoRepository from "@/repositories/CampeonatoRepository";
import CircuitRepository from "@/repositories/CircuitRepository";

export default {
  data() {
    return {
      currentIndex: 0,
      circuitos: null,
      year: null,
    };
  },
  computed: {
    currentCircuitoImage() {
      const currentCircuito = this.circuitos[this.currentIndex];
      console.log("ESTOY EN CURRENTCIRCUITO",currentCircuito)
      return currentCircuito?.tieneImagen
        ? `http://localhost:8080/api/circuitos/${currentCircuito.id}/imagen`
        : "/placeholder.png";
    }
  },
  async mounted() {
    await this.fetchCircuitos(); // Llamamos a la función para obtener los circuitos cuando el componente se monta
  },
  methods: {
    admin() {
      return auth.isAdmin();
    },
    formatToLocalDateTime(date, time) {
      if (!date || !time) return ''; // Validación para evitar errores
      const dateTime = `${date}T${time}`; // Formato ISO 8601
      return dateTime;
    },

    async fetchCircuitos() {
      try {
        // Paso 1: Consultar los circuitos desde el backend
        let circuitosFromBackend;

        const responseSeasons = await fetch("http://ergast.com/api/f1/seasons.json?limit=1000");
        let seasons = await responseSeasons.json();
        let mockYear = seasons.MRData.SeasonTable.Seasons[seasons.MRData.SeasonTable.Seasons.length -1].season
        this.year = mockYear

        let data;

        try{
          await CampeonatoRepository.findOne(this.year);
        }catch{
          await CampeonatoRepository.save({ ano: this.year });
        }


         
          const response  = await CircuitRepository.findAllByAno(this.year);

        if(response.data.length >0){ 
          circuitosFromBackend = response.data
          if (circuitosFromBackend.length > 0) {
              const circuitosOrdenados = circuitosFromBackend.sort((a, b) => {
                  const numOrdenA = a.grandesPremios[0].numOrden;
                  const numOrdenB = b.grandesPremios[0].numOrden;
                  return numOrdenA - numOrdenB;
              });

              this.circuitos = circuitosOrdenados;
            }
          console.log("Me voy a matar",circuitosFromBackend)

        }else{
          const response = await fetch("http://ergast.com/api/f1/current.json");
          data = await response.json();
          // Extraer datos relevantes de los circuitos
          const circuitosFromAPI = data.MRData.RaceTable.Races.map((race) => ({
            id: race.Circuit.circuitId,
            nombreCircuito: race.Circuit.circuitName,
            latitud: race.Circuit.Location.lat,
            longitud: race.Circuit.Location.long,
            localidad: race.Circuit.Location.locality,
            pais: race.Circuit.Location.country,
            grandesPremios: [
              {
                numOrden: race.round,
                
                fechaHoraSprint: race.Sprint
                  ? this.formatToLocalDateTime(race.Sprint.date, race.Sprint.time)
                  : '',
               
                fechaHoraLibres1: race.FirstPractice
                  ? this.formatToLocalDateTime(race.FirstPractice.date, race.FirstPractice.time)
                  : '',

                fechaHoraLibres2: race.SecondPractice
                  ? this.formatToLocalDateTime(race.SecondPractice.date, race.SecondPractice.time)
                  : '',

                fechaHoraLibres3: race.ThirdPractice
                  ? this.formatToLocalDateTime(race.ThirdPractice.date, race.ThirdPractice.time)
                  : '',

                ano: this.year,

                fechaHoraClasificacion: race.Qualifying
                  ? this.formatToLocalDateTime(race.Qualifying.date, race.Qualifying.time)
                  : '',

                fechaHoraCarrera: this.formatToLocalDateTime(race.date, race.time),

              },
            ],
          }));
          console.log(circuitosFromAPI.data)
          // Guardar cada circuito en el backend con la nueva estructura
          const savePromises = circuitosFromAPI.map(async (circuito) => {
            try {
              
              await CircuitRepository.save({
                id: circuito.id,
                nombreCircuito: circuito.nombreCircuito,
                latitud: circuito.latitud,
                longitud: circuito.longitud,
                localidad: circuito.localidad,
                pais: circuito.pais,
                grandesPremios: circuito.grandesPremios,
              });
            } catch (error) {
              console.error(`Error al guardar el circuito ${circuito.id}:`);
            }
          });

          // Esperar a que todos los circuitos se guarden
          await Promise.all(savePromises);

          // Mostrar inmediatamente los datos obtenidos de la API
          this.circuitos = circuitosFromAPI;
          console.log("Circuitos desde API con estructura:", this.circuitos);

        }

      }catch{
        console.log("Error")
      }    
    },
    nextImage() {
      console.log("siguiente")
      if (this.circuitos.length > 0) {
        this.currentIndex = (this.currentIndex + 1) % this.circuitos.length;
        console.log(this.currentIndex)
      }
    },
    prevImage() {
      if (this.circuitos.length > 0) {
        this.currentIndex =
          (this.currentIndex - 1 + this.circuitos.length) % this.circuitos.length;
      }
    },
    goToImage(index) {
      if (index >= 0 && index < this.circuitos.length) {
        this.currentIndex = index;
      }
    },
  },


};
</script>

<style scoped>
.circuitos-container {
  padding: 30px;
  text-align: center;
}

.circuitos-title {
  font-size: 24px;
  color: #ff1801;
  margin-bottom: 20px;
}

.carousel-container {
  position: relative;
  width: 100%;
  max-width: 600px;
  margin: 0 auto;
  overflow: hidden;
  border-radius: 10px;
}

.carousel {
  width: 100%;
  height: 400px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.carousel-image {
  width: 375px; /* Ancho fijo */
  height: 250px; /* Alto fijo */
  object-fit: cover; /* Asegura que la imagen se recorte para ajustarse al contenedor */
  border-radius: 10px;
}

.carousel-button {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  border: none;
  font-size: 24px;
  padding: 10px;
  cursor: pointer;
  border-radius: 50%;
  z-index: 10;
}

.carousel-button.prev {
  left: 10px;
}

.carousel-button.next {
  right: 10px;
}

.indicator {
  text-align: center;
  margin-top: 10px;
}

.dot {
  display: inline-block;
  width: 12px;
  height: 12px;
  margin: 0 5px;
  background-color: #bbb;
  border-radius: 50%;
  cursor: pointer;
}

.dot.active {
  background-color: #ff1801;
}

.circuito-link {
  margin-top: 15px;
}

.circuito-name {
  font-size: 18px;
  color: #ff1801;
  text-decoration: none;
}

.circuito-name:hover {
  text-decoration: underline;
}

.circuitos-list {
  margin-top: 30px;
}

.circuitos-list h3 {
  font-size: 22px;
  color: #ff1801;
  margin-bottom: 15px;
}

.circuitos-list-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 20px;
  padding: 0 20px;
}

.circuitos-row {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  width: 100%;
}

.circuito-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  width: 30%;
}

.circuito-link-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-decoration: none;
  color: #333;
  transition: transform 0.3s ease;
}

.circuito-link-item:hover {
  transform: scale(1.05);
  color: #ff1801;
}

.circuito-thumbnail {
  width: 150px; /* Ancho fijo */
  height: 100px; /* Alto fijo */
  object-fit: cover; /* Asegura que la imagen no se deforme */
  border-radius: 10px;
  margin-bottom: 10px;
}

.edit-button {
  margin-top: 10px;
  padding: 8px 15px;
  background-color: #ff1801;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
  text-decoration: none;
}

.edit-button:hover {
  background-color: #cc1500;
}
</style>