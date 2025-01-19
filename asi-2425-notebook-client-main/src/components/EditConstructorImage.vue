<template>
  <div class="edit-circuit-container">
    <h2>Gestionando Imagen del Constructor: {{ this.constructor.nombre }}</h2>

    
    <div class="columns">
      <!-- Columna para Editar Imagen -->
      <div class="column" v-if="this.constructor.tieneImagen">
        <h3>Editar Imagen</h3>
        <form @submit.prevent="addImage">
          <div class="form-group">
            <label for="newImage">Nueva Imagen:</label>
            <input type="file" id="newImage" ref="inputNewImage" />
          </div>
          <button type="submit" class="edit-button">Añadir Imagen</button>
        </form>
        <div class="mb-3" v-if="auxName">
          <label class="form-label">Imagen actual: {{ auxName }}</label>
        </div>
      </div>
      <div class="column" v-if="!this.constructor.tieneImagen">
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

      <!-- Columna para Eliminar Imagen -->
      <div class="column">
        <h3>Eliminar Imagen</h3>
        <p>Esta acción eliminará la imagen actual del constructor.</p>
        <button @click="deleteImage" class="delete-button">Eliminar Imagen</button>
      </div>

      <!-- Columna para Añadir Imagen desde 0 -->
      
    </div>
    <router-link to="/constructores" class="cancel-button">Cancelar</router-link>
  </div>
</template>

<script>
import ConstructorRepository from '@/repositories/ConstructorRepository';

export default {
  props: {
    constructorId: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      constructor: {},
      auxName: null,
    };
  },  
  async mounted() {

    this.constructor = await ConstructorRepository.findById(this.constructorId);
  },
  methods: {
    editImage() {
      console.log(`Editando imagen para el constructor: ${this.constructorId}`);
      // Lógica para editar la imagen
    },

    async deleteImage() {
      try {
          // Llamar al repositorio para guardar la imagen
          await ConstructorRepository.deleteImage(this.constructorId);
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
          await ConstructorRepository.saveImage(this.constructorId, file);
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
