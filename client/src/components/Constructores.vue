<template>
  <div class="driver-standings">
    <div class="year-input">
      <label for="year">Introduce un año (mínimo 1959):</label>
      <input type="number" v-model="selectedYear" :min="1958" :max="currentYear" placeholder="Introduce un año" />
      <button @click="fetchDriverStandings">Buscar</button>
      <p v-if="invalidYear" class="error-message">El año debe ser mayor o igual a 1958 y menor que el año actual.</p>
    </div>

    <!-- Indicador de carga -->
    <div v-if="isLoading" class="loading-spinner">
      <p>Cargando datos...</p>
    </div>

    <!-- Tabla con la clasificación -->
    <table v-if="constructorStandings.length > 0 && !isLoading">
      <thead>
        <tr>
          <th>Imagen</th>
          <th>Posición</th>
          <th>Escudería</th>
          <th>Nacionalidad</th>
          <th>Puntos</th>
          <th>Victorias</th>
          <th></th> <!-- Nueva columna -->
        </tr>
      </thead>
      <tbody>
        <tr v-for="(constructor, index) in constructorStandings" :key="constructor.id">
          <td>
          <td>
            <img class="card-img-top"
              :src="constructor.tieneImagen ? `http://localhost:8080/api/constructores/${constructor.id}/imagen` : '/placeholder.png'"
              alt="Imagen del constructor" />
          </td>
          </td>
          <td>{{ index + 1 }}</td>
          <td>{{ constructor.nombre }}</td>
          <td>{{ constructor.nacionalidad }}</td>
          <td>{{ constructor.puntos }}</td>
          <td>{{ constructor.victorias }}</td>
          <td>
            <router-link v-if="admin()" class="btn btn-primary" :to="'/edit/constructor/' + constructor.id">
              Gestionar foto
            </router-link>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- No hay datos disponibles -->
    <div v-else class="no-data">
      <p>No hay datos disponibles para el año seleccionado.</p>
    </div>
  </div>
</template>


<script>
import CampeonatoRepository from '@/repositories/CampeonatoRepository';
import ConstructorRepository from '@/repositories/ConstructorRepository';
import auth from '@/common/auth';

export default {
  data() {
    return {
      selectedYear: null, // Año predeterminado (null para la temporada actual)
      lastyear: null,
      constructorStandings: [], // Lista de los standings de las escuderías
      invalidYear: false,
      isLoading: false,
      currentYear: new Date().getFullYear(),
    };
  },
  computed: {
    getImageSrc() {
      return this.constructorStandings?.map(constructor => {
        if (constructor.tieneImagen != true) {
          return `${BACKEND_URL}/Constructor/${constructor.id}/imagen`;
        }
        return "/placeholder.png";
      }) || [];
    }
  },
  async mounted() {
    const responseSeasons = await fetch("http://ergast.com/api/f1/seasons.json?limit=1000");
    let seasons = await responseSeasons.json();
    let mockYear = seasons.MRData.SeasonTable.Seasons[seasons.MRData.SeasonTable.Seasons.length -1].season
    this.lastyear = mockYear

    try{
      await CampeonatoRepository.findOne(this.lastyear);
    }catch{
      await CampeonatoRepository.save({ ano: this.lastyear });
    }

    this.fetchDriverStandings();
  },
  methods: {
    admin() {
      return auth.isAdmin();
    },
    async fetchDriverStandings() {
      if (!this.selectedYear) {
        this.invalidYear = false;
        return this.loadDataConstructor(this.lastyear);
      }else{
        const year = Number(this.selectedYear);
        if (year < 1958 || year > this.currentYear) {
          this.invalidYear = true;
          this.constructorStandings = [];
          return;
        }

        this.invalidYear = false;
        try{
          await CampeonatoRepository.findOne(this.selectedYear);
        }catch{
          await CampeonatoRepository.save({ ano: this.selectedYear });
        }
        this.loadDataConstructor(this.selectedYear);
      }

    },
    async loadDataConstructor(year) {

      let url
      if(this.selectedYear == year){
        url = `http://ergast.com/api/f1/${this.selectedYear}/constructorStandings.json`; 
      }else{
        url = `http://ergast.com/api/f1/current/constructorStandings.json`; 
      }

      const response = await fetch(url);
      const data = await response.json(); 
      let constructoresFromBackend = [];
      try {
        constructoresFromBackend = await ConstructorRepository.findByAno(year);
      } catch (error) {
        console.error("Error al obtener los constructores del backend:", error);
      }
      if (constructoresFromBackend.length > 0) {
        // Si hay circuitos en el backend, usamos esos datos
        this.constructorStandings = constructoresFromBackend;

      } else {
        this.isLoading = true; // Activar el indicador de carga

        try {

          this.constructorStandings = data.MRData.StandingsTable.StandingsLists[0].ConstructorStandings || []; // Obtener los standings de las escuderías
          const constructor = data.MRData.StandingsTable.StandingsLists[0].ConstructorStandings || [];

          const transformedData = constructor.map(constructor => ({
            id: constructor.Constructor.constructorId,
            nombre: constructor.Constructor.name,
            nacionalidad: constructor.Constructor.nationality,
            victorias: constructor.wins,
            puntos: constructor.points,
            ano: data.MRData.StandingsTable.season,

          }));

          this.constructorStandings = transformedData;
          const savePromises = [];
          for (const constructor of transformedData) {
            const savePromise = (async () => {
              try {
                await ConstructorRepository.save({
                  id: constructor.id,
                  nombre: constructor.nombre,
                  nacionalidad: constructor.nacionalidad,
                  victorias: constructor.victorias,
                  puntos: constructor.puntos,
                  ano: constructor.ano,
                });
              } catch (error) {
                console.error(`Error al guardar el Constructor ${constructor.id}:`, error);
              }
            })();
            savePromises.push(savePromise);
          }

          // Esperar a que todos los circuitos se guarden
          await Promise.all(savePromises);

        } catch (error) {
          console.error("Error al obtener la clasificación:", error);
        } finally {
          this.isLoading = false; // Desactivar el indicador de carga
        }
      }
    }
  },
};




</script>

<style scoped>
/* Estilos generales */
.driver-standings {
  padding: 20px;
  font-family: 'Roboto', sans-serif;
  /* Fuente común */
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  max-width: 900px;
  /* Limita el ancho de la tarjeta */
  margin: 40px auto 0;
  /* Añade separación en la parte superior */
}

.year-input {
  margin-bottom: 20px;
  display: flex;
  align-items: center;
}

input[type="number"] {
  padding: 8px;
  font-size: 16px;
  width: 220px;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-right: 10px;
  transition: border-color 0.3s ease;
}

input[type="number"]:focus {
  border-color: #4CAF50;
}

button {
  padding: 10px 20px;
  font-size: 16px;
  background-color: #4CAF50;
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 4px;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #45a049;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.error-message {
  color: red;
  font-size: 14px;
  margin-top: 5px;
}

.loading-spinner {
  text-align: center;
  font-size: 18px;
  color: #888;
}

table {
  width: 100%;
  max-width: 800px;
  /* Limita el ancho de la tabla */
  margin: 20px auto;
  /* Centra la tabla */
  border-collapse: collapse;
}

th,
td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: #f4f4f4;
  font-weight: bold;
}

tbody tr:hover {
  background-color: #f1f1f1;
}

.no-data {
  text-align: center;
  font-size: 18px;
  color: #888;
}
</style>