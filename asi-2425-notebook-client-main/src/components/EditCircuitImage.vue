<template>
    <div class="edit-circuit-container">
      <h2>Editar Imagen del Circuito</h2>
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <label for="circuitName">Nombre del Circuito:</label>
          <input type="text" id="circuitName" :value="circuitId" readonly />
        </div>
        <div class="form-group">
          <label for="newImage">Nueva Imagen:</label>
          <input type="file" id="newImage" />
        </div>
        <button type="submit" class="submit-button">Guardar</button>
        <router-link to="/" class="cancel-button">Cancelar</router-link>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      circuitId: {
        type: String,
        required: true,
      },
    },
    methods: {
      submitForm() {
        console.log(`Guardando imagen para el circuito: ${this.circuitId}`);
        // Aquí puedes implementar la lógica para guardar la imagen
      },
    },
  };
  </script>
  
  <style scoped>
  .edit-circuit-container {
    margin: 20px auto;
    max-width: 400px;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 10px;
    background-color: #f9f9f9;
  }
  
  .edit-circuit-container h2 {
    color: #ff1801;
    margin-bottom: 20px;
  }
  
  .form-group {
    margin-bottom: 15px;
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
  
  .submit-button {
    padding: 10px 20px;
    background-color: #ff1801;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  
  .cancel-button {
    padding: 10px 20px;
    margin-left: 10px;
    background-color: #ccc;
    color: black;
    text-decoration: none;
    border-radius: 5px;
  }
  </style>
  