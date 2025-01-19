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
            <img :src="constructor.imagenUrl || 'placeholder.jpg'" alt="Constructor Image" width="50" height="50" />
          </td>
          <td>{{ index + 1 }}</td>
          <td>{{ constructor.nombre }}</td>
          <td>{{ constructor.nacionalidad }}</td>
          <td>{{ constructor.puntos }}</td>
          <td>{{ constructor.victorias }}</td>
          <td>
            <router-link v-if="admin()" class="btn btn-primary" :to="'/edit/constructor/' + constructor.id">
        Eliminar foto
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
      constructorStandings: [], // Lista de los standings de las escuderías
      invalidYear: false,
      isLoading: false,
      currentYear: new Date().getFullYear(),
    };
  },
  mounted() {
    this.fetchDriverStandings(); // Cargar los standings de la escudería al cargar el componente
  },
  methods: {
    admin() {
      return auth.isAdmin();
    },
    async fetchDriverStandings() {
      if (!this.selectedYear) {
        this.invalidYear = false;
        return this.loadDataConstructor('current'); // Si no se ha seleccionado un año, cargar la temporada actual
      }

      const year = Number(this.selectedYear);
      if (year < 1958 || year > this.currentYear) {
        this.invalidYear = true;
        this.constructorStandings = [];
        return;
      }

      this.invalidYear = false;

      try {
        await CampeonatoRepository.findOne(year);
      } catch (error) {
        console.log("No se ha podido encontrar el año especificado");
        await CampeonatoRepository.save({ ano: year });
      }

      this.loadDataConstructor(year);



    },
    async loadDataConstructor(year) {
      if (year == "current") year = 2024;
      let constructoresFromBackend = [];
      try {
        constructoresFromBackend = await ConstructorRepository.findByAno(year);
        console.log("Constructores desde el backend:", constructoresFromBackend.length);
        console.log(constructoresFromBackend);
      } catch (error) {
        console.error("Error al obtener los constructores del backend:", error);
      }
      if (constructoresFromBackend.length > 0) {
        // Si hay circuitos en el backend, usamos esos datos
        this.constructorStandings = constructoresFromBackend;

      } else {
        this.isLoading = true; // Activar el indicador de carga

        try {
          const url = `http://ergast.com/api/f1/${year}/constructorStandings.json`; // URL de la API
          const response = await fetch(url); // Realizar la petición
          const data = await response.json(); // Convertir la respuesta en formato JSON
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

          console.log(transformedData); // Aquí puedes ver el resultado transformado

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