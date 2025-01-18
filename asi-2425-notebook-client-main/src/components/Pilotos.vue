<template>
  <div class="driver-standings">
    <div class="year-input">
      <label for="year">Introduce un año (mínimo 1950):</label>
      <input type="number" v-model="selectedYear" :min="1950" :max="currentYear" placeholder="Introduce un año" />
      <button @click="fetchDriverStandings">Buscar</button>
      <p v-if="invalidYear" class="error-message">El año debe ser mayor o igual a 1950 y menor que el año actual.</p>
    </div>

    <!-- Indicador de carga -->
    <div v-if="isLoading" class="loading-spinner">
      <p>Cargando datos...</p>
    </div>

    <!-- Tabla con la clasificación -->
    <table v-if="driverStandings.length > 0 && !isLoading">
      <thead>
        <tr>
          <th>Imagen</th>
          <th>Posición</th>
          <th>Nombre</th>
          <th>Nacionalidad</th>
          <th>Escudería</th>
          <th>Puntos</th>
          <th>Victorias</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(driver, index, image) in driverStandings" :key="driver.Driver.driverId">
          <td></td>
          <td>{{ index + 1 }}</td>
          <td>{{ driver.Driver.givenName }} {{ driver.Driver.familyName }}</td>
          <td>{{ driver.Driver.nationality }}</td>
          <td>{{ driver.Constructors[0].name }}</td>
          <td>{{ driver.points }}</td>
          <td>{{ driver.wins }}</td>
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
import PilotoRepository from '@/repositories/PilotoRepository';

export default {
  data() {
    return {
      selectedYear: null, // Año predeterminado (null para la temporada actual)
      driverStandings: [],
      invalidYear: false,
      isLoading: false,
      currentYear: new Date().getFullYear(),

    };
  },
  mounted() {
    this.fetchDriverStandings();
  },
  methods: {
    async fetchDriverStandings() {
      if (!this.selectedYear) {
        this.invalidYear = false;
        return this.loadData('current');
      }

      const year = Number(this.selectedYear);
      console.log(year)
      if (year < 1950 || year > this.currentYear) {
        this.invalidYear = true;
        this.driverStandings = [];
        return;
      }

      this.invalidYear = false;

      try {
        console.log("Post antes del FINDONE", year)
        await CampeonatoRepository.findOne(year);
      } catch (error) {
        console.log("No se ha podido encontrar el año especificado");
        await CampeonatoRepository.save({ ano: year });
      }

      this.loadData(year);
    },
    async loadData(year) {
      this.isLoading = true;

      try {
        const url = `http://ergast.com/api/f1/${year}/driverStandings.json`;
        const response = await fetch(url);
        const data = await response.json();
        this.driverStandings = data.MRData.StandingsTable.StandingsLists?.[0]?.DriverStandings || [];
        const driver = data.MRData.StandingsTable.StandingsLists?.[0]?.DriverStandings || [];

        const transformedData = driver.map(driver => ({
          id: driver.Driver.driverId,
          nombreCompleto: `${driver.Driver.givenName} ${driver.Driver.familyName}`,
          nacionalidad: driver.Driver.nationality,
          victorias: driver.wins,
          puntos: driver.points,
          constructorId: driver.Constructors[0].constructorId,
          constructorNombre: driver.Constructors[0].name,
          constructorNacionalidad: driver.Constructors[0].nationality,
          ano: data.MRData.StandingsTable.season,

        }));
        const savePromises = [];
          for (const piloto of transformedData) {
            const savePromise = (async () => {
              try {
                await PilotoRepository.save({
                  id: piloto.id,
                  nombreCompleto: piloto.nombreCompleto,
                  nacionalidad: piloto.nacionalidad,
                  victorias: piloto.victorias,
                  puntos: piloto.puntos,
                  ano: piloto.ano,
                  constructorId: piloto.constructorId,
                  constructorNombre: piloto.constructorNombre,
                  constructorNacionalidad: piloto.constructorNacionalidad,
                });
              } catch (error) {
                console.error(`Error al guardar el Piloto ${piloto.id}:`, error);
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
        this.isLoading = false;
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
