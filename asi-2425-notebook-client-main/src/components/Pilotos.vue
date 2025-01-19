<template>
  <div class="driver-standings">
    <div class="year-input">
      <label for="year">Introduce un año (mínimo 1958):</label>
      <input type="number" v-model="selectedYear" :min="1958" :max="currentYear" placeholder="Introduce un año" />
      <button @click="fetchDriverStandings">Buscar</button>
      <p v-if="invalidYear" class="error-message">El año debe ser mayor o igual a 1958 y menor que el año actual.</p>
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
          <th></th> <!-- Nueva columna -->
        </tr>
      </thead>
      <tbody>
        <tr v-for="(driver, index) in driverStandings" :key="driver.id">
          <td>
          <td>
            <img class="card-img-top"
              :src="driver.tieneImagen ? `http://localhost:8080/api/pilotos/${driver.id}/imagen` : '/placeholder.png'"
              alt="Imagen del piloto" />
          </td>
          </td>
          <td>{{ index + 1 }}</td>
          <td>{{ driver.nombreCompleto }}</td>
          <td>{{ driver.nacionalidad }}</td>
          <td>{{ driver.constructorNombre }}</td>
          <td>{{ driver.puntos }}</td>
          <td>{{ driver.victorias }}</td>
          <td>
            <router-link v-if="admin()" class="btn btn-primary" :to="'/edit/pilotos/' + driver.id">
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
import PilotoRepository from '@/repositories/PilotoRepository';
import ConstructorRepository from '@/repositories/ConstructorRepository';
import auth from '@/common/auth';

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
    admin() {
      return auth.isAdmin();
    },
    async fetchDriverStandings() {
      if (!this.selectedYear) {
        this.invalidYear = false;
        await this.loadDataConstructor('current');
        return this.loadData('current');
      }

      const year = Number(this.selectedYear);
      
      if (year < 1958 || year > this.currentYear) {
        this.invalidYear = true;
        this.driverStandings = [];
        return;
      }

      this.invalidYear = false;

      try {
        
        await CampeonatoRepository.findOne(year);
      } catch (error) {
        
        await CampeonatoRepository.save({ ano: year });
      }
      await this.loadDataConstructor(year);
      this.loadData(year);
    },
    async loadData(year) {
      
      if (year == "current") year = 2024;
      
      let driversFromBackend = [];
      try {
        driversFromBackend = await PilotoRepository.findByAnoPiloto(year);
        console.log("Pilotos desde el backend:", driversFromBackend.length);
        //console.log(driversFromBackend);
      } catch (error) {
        console.error("Error al obtener los pilotos del backend:", error);
      }
      if (driversFromBackend.length > 0) {
        // Si hay circuitos en el backend, usamos esos datos
        this.driverStandings = driversFromBackend;

      } else {
        this.isLoading = true; // Activar el indicador de carga


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
            ano: data.MRData.StandingsTable.season,

          }));
          this.driverStandings = transformedData;
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
                  //constructorNombre: piloto.constructorNombre,
                });
              } catch (error) {
                //console.error(`Error al guardar el Piloto ${piloto.id}:`, error);
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
    
    async loadDataConstructor(year) {
      
      if (year == "current") year = 2024;
      
      let constructoresFromBackend = [];
      try {
        constructoresFromBackend = await ConstructorRepository.findByAno(year);
        console.log("Constructores desde el backend:", constructoresFromBackend.length);
        //console.log(constructoresFromBackend);
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

          //console.log(transformedData); // Aquí puedes ver el resultado transformado

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
