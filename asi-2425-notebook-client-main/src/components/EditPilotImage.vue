<template>
  <div class="edit-circuit-container">
    <h2>Gestionando Imagen del Piloto: {{ pilotoId }}</h2>

    <div class="columns">
      <!-- Columna para Editar Imagen -->
      <div class="column">
        <h3>Editar Imagen</h3>
        <form @submit.prevent="editImage">
          <div class="form-group">
            <label for="editImage">Nueva Imagen:</label>
            <input type="file" id="editImage" ref="inputEditImage" />
          </div>
          <button type="submit" class="edit-button">Guardar Cambios</button>
        </form>
      </div>

      <!-- Columna para Eliminar Imagen -->
      <div class="column">
        <h3>Eliminar Imagen</h3>
        <p>Esta acción eliminará la imagen actual del piloto.</p>
        <button @click="deleteImage" class="delete-button">Eliminar Imagen</button>
      </div>

      <!-- Columna para Añadir Imagen desde 0 -->
      <div class="column">
        <h3>Añadir Nueva Imagen</h3>
        <form @submit.prevent="addImage">
          <div class="form-group">
            <label for="newImage">Nueva Imagen:</label>
            <input type="file" id="newImage" ref="inputNewImage" />
          </div>
          <button type="submit" class="add-button">Añadir Imagen</button>
        </form>
        <div class="mb-3" v-if="auxName">
          <label class="form-label">Imagen actual: {{ auxName }}</label>  
        </div>
      </div>
    </div>
    <router-link to="/pilotos" class="cancel-button">Cancelar</router-link>
  </div>
</template>

<script>
import PilotoRepository from '@/repositories/PilotoRepository';

export default {
  props: {
    pilotoId: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      pilotos: {},
      auxName: null,
    };
  },

  methods: {
    editImage() {
      console.log(`Editando imagen para el piloto: ${this.pilotoId}`);
      // Lógica para editar la imagen
    },

    async deleteImage() {
      try {
          // Llamar al repositorio para guardar la imagen
          await PilotoRepository.deleteImage(this.pilotoId);
          console.log("Imagen eliminada exitosamente.");
        } catch (err) {
        console.error("Error al eliminar la imagen:", err);
      }
    },

    async addImage() {
      try {
        const file = this.$refs.inputNewImage.files[0]; // Obtener el archivo del input correcto
        if (file) {
          // Llamar al repositorio para guardar la imagen
          await PilotoRepository.saveImage(this.pilotoId, file);
          console.log("Imagen añadida exitosamente.");
        } else {
          console.error("No se seleccionó ningún archivo.");
        }
      } catch (err) {
        console.error("Error al añadir la imagen:", err);
      }
    }
  },
};
</script>



<style scoped>
.edit-circuit-container {
  margin: 20px auto;
  max-width: 800px;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
  background-color: #f9f9f9;
}

.edit-circuit-container h2 {
  color: #ff1801;
  text-align: center;
  margin-bottom: 20px;
}

.columns {
  display: flex;
  justify-content: space-between;
  gap: 20px;
}

.column {
  flex: 1;
  border: 1px solid #ddd;
  border-radius: 10px;
  padding: 15px;
  background-color: #fff;
  text-align: center;
}

.column h3 {
  color: #333;
  margin-bottom: 10px;
}

.form-group {
  margin-bottom: 15px;
  text-align: left;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.form-group input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.edit-button {
  padding: 10px 20px;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.delete-button {
  padding: 10px 20px;
  background-color: #f44336;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.add-button {
  padding: 10px 20px;
  background-color: #2196f3;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.cancel-button {
  display: inline-block;
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #ccc;
  color: black;
  text-decoration: none;
  border-radius: 5px;
  text-align: center;
}
</style>
