<template>
    <div class="driver-standings">
      <div class="year-input">
        <label for="year">Introduce un año (mínimo 1950):</label>
        <input 
          type="number" 
          v-model="selectedYear" 
          :min="1950" 
          :max="currentYear" 
          placeholder="Introduce un año" 
        />
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
            <th>Posición</th>
            <th>Nombre</th>
            <th>Nacionalidad</th>
            <th>Escudería</th>
            <th>Puntos</th>
            <th>Victorias</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(driver, index) in driverStandings" :key="driver.Driver.driverId">
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
        if (year < 1950 || year > this.currentYear) {
          this.invalidYear = true;
          this.driverStandings = [];
          return;
        }
  
        this.invalidYear = false;
        this.loadData(year);
      },
      async loadData(year) {
        this.isLoading = true;
        try {
          const url = `http://ergast.com/api/f1/${year}/driverStandings.json`;
          const response = await fetch(url);
          const data = await response.json();
          this.driverStandings = data.MRData.StandingsTable.StandingsLists?.[0]?.DriverStandings || [];
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
  font-family: 'Roboto', sans-serif; /* Fuente común */
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  max-width: 900px; /* Limita el ancho de la tarjeta */
  margin: 40px auto 0; /* Añade separación en la parte superior */
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
  max-width: 800px; /* Limita el ancho de la tabla */
  margin: 20px auto; /* Centra la tabla */
  border-collapse: collapse;
}

th, td {
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


