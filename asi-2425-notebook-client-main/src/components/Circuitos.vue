<template>
  <div class="circuitos-container">
    <h2 class="circuitos-title">Circuitos</h2>

    <!-- Carrusel -->
    <div class="carousel-container">
      <div class="carousel">
        <!-- Imagen clickeable -->
        <a class="carousel" :href="'/circuitos/' + images[currentIndex].name">
          <img :src="images[currentIndex].src" :alt="images[currentIndex].name" class="carousel-image" />
        </a>
      </div>

      <button @click="prevImage" class="carousel-button prev">‹</button>
      <button @click="nextImage" class="carousel-button next">›</button>

      <div class="indicator">
        <span v-for="(image, index) in images" :key="index" :class="['dot', { active: index === currentIndex }]"></span>
      </div>

      <!-- Enlace debajo de la imagen -->
      <div class="circuito-link">
        <a :href="'/circuitos/' + images[currentIndex].name" class="circuito-name">
          Ver circuito: {{ images[currentIndex].name }}
        </a>
      </div>
    </div>

    <!-- Lista de todos los circuitos con foto -->
    <div class="circuitos-list">
      <h3>Todos los Circuitos</h3>
      <div class="circuitos-list-container">
        <div class="circuitos-row">
          <div class="circuito-item" v-for="(image, index) in images" :key="index">
            <a :href="'/circuitos/' + image.name" class="circuito-link-item">
              <!-- Imagen del circuito -->
              <img :src="image.src" :alt="image.name" class="circuito-thumbnail" />
              <!-- Nombre del circuito -->
              <span>{{ image.name }}</span>
            </a>

            <!-- Botón para editar imagen solo si admin() es true -->
            <router-link v-if="admin()" :to="{ name: 'EditCircuitImage', params: { circuitId: image.name } }"
              class="edit-button">
              Editar Imagen
            </router-link>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import notexist from "@/assets/images/notexist.jpg";
import auth from "@/common/auth";
import CircuitRepository from "@/repositories/CircuitRepository";

export default {
  data() {
    return {
      currentIndex: 0,
      images: [
        { src: notexist, name: "notexist" },
      ],
      circuitos: [], // Aquí almacenaremos los circuitos obtenidos desde la API
    };
  },
  async mounted() {
    await this.fetchCircuitos(); // Llamamos a la función para obtener los circuitos cuando el componente se monta
  },
  methods: {
    admin() {
      return auth.isAdmin();
    },

    async fetchCircuitos() {
  try {
    // Paso 1: Consultar los circuitos desde el backend
    let circuitosFromBackend = [];
    try {
      circuitosFromBackend = await CircuitRepository.findAll();
      console.log("Circuitos desde el backend:", circuitosFromBackend.length);
    } catch (error) {
      console.error("Error al obtener los circuitos del backend:", error);
    }

    if (circuitosFromBackend.length > 0) {
      // Si hay circuitos en el backend, usamos esos datos
      this.circuitos = circuitosFromBackend.map((circuito) => ({
        name: circuito.id,
       
      }));
      console.log("Circuitos obtenidos del backend:", this.circuitos);
    } else {
      // Paso 2: Consultar los circuitos desde la API externa
      const response = await fetch("http://ergast.com/api/f1/current.json");
      const data = await response.json();

      // Extraer datos relevantes de los circuitos
      const circuitosFromAPI = data.MRData.RaceTable.Races.map((race) => ({
        id: race.Circuit.circuitId,
        nombreCircuito: race.Circuit.circuitName,
        latitud: race.Circuit.Location.lat,
        longitud: race.Circuit.Location.long,
        localidad: race.Circuit.Location.locality,
        pais: race.Circuit.Location.country,
        
      }));

      console.log("Circuitos obtenidos de la API:", circuitosFromAPI);

      // Guardar cada circuito en el backend
      const savePromises = circuitosFromAPI.map(async (circuito) => {
        try {
          await CircuitRepository.save(circuito);
        } catch (error) {
          console.error(`Error al guardar el circuito ${circuito.id}:`, error);
        }
      });

      // Esperar a que todos los circuitos se guarden
      await Promise.all(savePromises);

      // Mostrar inmediatamente los datos obtenidos de la API
      this.circuitos = circuitosFromAPI.map((circuito) => ({
        name: circuito.id,
      }));
      console.log("Circuito desde api")
    }

    // Actualizar las imágenes basadas en los circuitos
    this.images = this.circuitos.map((circuito) => ({
      src: this.getImageForCircuit(circuito.name), // Usamos una función para asignar imágenes
      name: circuito.name,
    }));

    console.log("Imágenes actualizadas:", this.images);
  } catch (error) {
    console.error("Error al obtener y procesar los circuitos:", error);
  }
},

    // Método para seleccionar una imagen para cada circuito (puedes mejorar esto con más imágenes)
    getImageForCircuit(circuitId) {
      try {
        // Cargar la imagen del circuito dinámicamente desde la carpeta de imágenes
        const imagePath = require(`@/assets/images/${circuitId}.jpg`);
        return imagePath;
      } catch (error) {
        // Si no se encuentra la imagen, retornar la imagen por defecto
        //console.log(`Imagen no encontrada para el circuito: ${circuitId}`);
        return notexist; // Imagen por defecto
      }
    },

    nextImage() {
      this.currentIndex = (this.currentIndex + 1) % this.images.length;
    },

    prevImage() {
      this.currentIndex = (this.currentIndex - 1 + this.images.length) % this.images.length;
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
  width: 100%;
  max-width: 375px;
  object-fit: cover;
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
  width: 100%;
  height: auto;
  max-width: 150px;
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