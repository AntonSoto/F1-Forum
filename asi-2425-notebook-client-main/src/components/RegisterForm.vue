<template>
  <div class="container-background">
    <div class="f1-container">
      <h1 class="f1-title">Registrarse</h1>
      <div class="form-group mb-3 mt-3">
        <label for="login" class="f1-label">Login:</label>
        <input type="text" id="login" class="f1-input" v-model="auxLogin" />
      </div>
      <div class="form-group mb-3 mt-3">
        <label for="nombre" class="f1-label">Nombre:</label>
        <input type="text" id="nombre" class="f1-input" v-model="auxNombre" />
      </div>
      <div class="form-group mb-3 mt-3">
        <label for="apellidos" class="f1-label">Apellidos:</label>
        <input type="text" id="apellidos" class="f1-input" v-model="auxApellidos" />
      </div>
      <div class="form-group mb-3">
        <label for="fechaN" class="f1-label">Fecha de Nacimiento:</label>
        <input type="date" id="fechaN" class="f1-input" v-model="auxFechaN" />
      </div>
      <div class="form-group mb-3">
        <label for="pass" class="f1-label">Contraseña:</label>
        <input type="password" id="pass" class="f1-input" v-model="auxPass" />
      </div>
      <div class="form-group mb-3">
        <label for="confirmPass" class="f1-label">Confirmar constraseña:</label>
        <input type="password" id="confirmPass" class="f1-input" v-model="auxConfirmPass" />
      </div>
      <div>
        <button class="f1-button" @click="registrarme()">Registrarse</button>
      </div>
      <div>
        <router-link :to="'/'" class="f1-link">Log In</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import auth from "../common/auth.js";

export default {
  data() {
    return {
      auxLogin: null,
      auxNombre: null,
      auxPass: null,
      auxApellidos: null,
      auxFechaN: null,
      auxConfirmPass: null
    };
  },
  methods: {
    async registrarme() {
      if (this.auxPass == this.auxConfirmPass) {
        try {
          await auth.register({
            login: this.auxLogin,
            password: this.auxPass,
            nombrePila: this.auxNombre,
            apellidos: this.auxApellidos,
            fechaNacimiento: this.auxFechaN
          });
          try {
            await auth.login({
              login: this.auxLogin,
              password: this.auxPass
            });
          } catch (e) {
            console.error(e);
            if (e.response?.data?.message) {
              alert(e.response.data.message);
            } else {
              alert(e.message);
            }
          }
          this.$router.push("/notes");
        } catch (e) {
          console.error(e);
          if (e.response?.data?.message) {
            alert(e.response.data.message);
          } else {
            alert(e.message);
          }
        }
      }
    }
  }
};
</script>

<style>
.container-background {
  background: linear-gradient(to bottom, #f7dada, #db7676);
  /* Degradado gris claro a gris oscuro */
  /* Asegura que cubra toda la altura de la ventana */
  display: flex;
  /* Centra el contenido en la página */
}

.f1-container {
  background: #ffffff;
  font-family: 'Roboto', sans-serif;
  max-width: 400px;
  margin: 40vh auto 0 auto;
  /* Centrar verticalmente */
  transform: translateY(-50%);
  /* Ajustar la posición para centrar exactamente */
  padding: 20px;
  margin-top: 450px;
  border: 2px solid #ff1801;
  /* Inspirado en los colores de Ferrari */
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
  outline: none;
  box-shadow: 0 0 5px rgba(255, 24, 1, 0.8);
}

.f1-button {
  background-color: #ff1801;
  color: #ffffff;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.f1-button:hover {
  background-color: #e60000;
}

.f1-link {
  color: #ff1801;
  text-decoration: none;
  display: inline-block;
  margin-top: 15px;
}

.f1-link:hover {
  text-decoration: underline;
  color: #e60000;
}
</style>