<template>
    <div class="container-background">
      <div class="f1-container">
        <h1 class="f1-title">Editar Perfil</h1>
        
        <!-- Campos del perfil -->
        <div class="form-group mb-3 mt-3">
          <label for="login" class="f1-label">Login:</label>
          <input type="text" id="login" class="f1-input" v-model="user.login" disabled />
        </div>
        
        <div class="form-group mb-3 mt-3">
          <label for="nombre" class="f1-label">Nombre:</label>
          <input type="text" id="nombre" class="f1-input" v-model="user.nombrePila" disabled />
        </div>
  
        <div class="form-group mb-3 mt-3">
          <label for="apellidos" class="f1-label">Apellidos:</label>
          <input type="text" id="apellidos" class="f1-input" v-model="user.apellidos" disabled />
        </div>
  
        <div class="form-group mb-3">
          <label for="fechaN" class="f1-label">Fecha de Nacimiento:</label>
          <input type="date" id="fechaN" class="f1-input" v-model="formattedFechaNacimiento" disabled />
        </div>
  
        <!-- Cambiar contraseña -->
        <div class="form-group mb-3">
          <label for="newPass" class="f1-label">Nueva Contraseña:</label>
          <input type="password" id="newPass" class="f1-input" v-model="newPassword" />
        </div>
  
        <div class="form-group mb-3">
          <label for="confirmPass" class="f1-label">Confirmar Contraseña:</label>
          <input type="password" id="confirmPass" class="f1-input" v-model="confirmPassword" />
        </div>
  
        <!-- Botón de guardar -->
        <div>
          <button class="f1-button" @click="actualizarPerfil">Guardar Cambios</button>
          <button class="f1-button" @click="$router.push(`/profile/${user.id}`)">Volver al Perfil</button>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import AccountRepository from "@/repositories/AccountRepository";
  import Swal from "sweetalert2";
  
  export default {
    data() {
      return {
        user: {
          login: "",
          nombrePila: "",
          apellidos: "",
          fechaNacimiento: null,
        },
        newPassword: "",
        confirmPassword: "",
      };
    },
    computed: {
      // Propiedad computada para formatear la fecha de nacimiento
      formattedFechaNacimiento: {
        get() {
          if (this.user.fechaNacimiento) {
            const date = new Date(this.user.fechaNacimiento);
            return date.toISOString().split('T')[0]; // Formato 'yyyy-MM-dd'
          }
          return '';
        },
        set(newValue) {
          const date = new Date(newValue);
          this.user.fechaNacimiento = date;
        }
      }
    },
    async mounted() {
      try {
        const response = await AccountRepository.getAccount(); // Cargar los datos del usuario actual
        this.user = response;
      } catch (error) {
        console.error("Error al cargar los datos del usuario:", error);
      }
    },
    methods: {
      async actualizarPerfil() {
        // Validar que las contraseñas coincidan
        if (this.newPassword !== this.confirmPassword) {
          Swal.fire({
            title: "Error",
            text: "Las contraseñas no coinciden.",
            icon: "error",
          });
          return;
        }
  
        // Crear un objeto con los datos del usuario actual y la nueva contraseña
        const updatedUser = {
          ...this.user,
          password: this.newPassword, // Aquí pasamos la nueva contraseña
        };
  
        // Intentar actualizar el perfil del usuario
        try {
          await AccountRepository.updatePassword(updatedUser); // Llamamos al método para actualizar el perfil
          Swal.fire({
            title: "Éxito",
            text: "Los cambios se han guardado correctamente.",
            icon: "success",
          });
          this.$router.push(`/profile/${this.user.id}`); // Redirigir al perfil actualizado
        } catch (error) {
          console.error("Error al actualizar los datos:", error);
          Swal.fire({
            title: "Error",
            text: "Hubo un problema al actualizar los datos.",
            icon: "error",
          });
        }
      }
    }
  };
  </script>
  
  <style>
  .container-background {
    background: linear-gradient(to bottom, #f7dada, #db7676);
    display: flex;
  }
  
  .f1-container {
    background: #ffffff;
    font-family: 'Roboto', sans-serif;
    max-width: 400px;
    margin: 40vh auto 0 auto;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
  }
  
  .f1-title {
    text-align: center;
    color: #ff1801;
    margin-bottom: 20px;
    font-size: 24px;
  }
  
  .f1-label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
  }
  
  .f1-input {
    width: 100%;
    padding: 10px;
    margin-bottom: 15px;
    border: 1px solid #ccc;
    border-radius: 5px;
    background-color: #f1f1f1;
  }
  
  .f1-input:focus {
    border-color: #ff1801;
  }
  
  .f1-button {
    background-color: #ff1801;
    color: #ffffff;
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    font-size: 16px;
    cursor: pointer;
  }
  
  .f1-button:hover {
    background-color: #e60000;
  }
  </style>
  