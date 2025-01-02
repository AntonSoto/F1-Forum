<template>
  <div class="container-background">
    <div class="f1-container">
      <div class="form-group mb-3 mt-3">
        <label for="login" class="f1-label">Login:</label>
        <input type="text" id="login" class="f1-input" v-model="auxLogin" @keyup.enter="autenticarme()" />
      </div>
      <div class="form-group mb-3">
        <label for="pass" class="f1-label">Password:</label>
        <input type="password" id="pass" class="f1-input" v-model="auxPass" @keyup.enter="autenticarme()" />
      </div>
      <div>
        <button class="f1-button" @click="autenticarme()">Autenticarse</button>
      </div>
      <div>
        <router-link :to="'/register'" class="f1-link">Registrarse</router-link>
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
      auxPass: null
    };
  },
  methods: {
    async autenticarme() {
      try {
        await auth.login({
          login: this.auxLogin,
          password: this.auxPass
        });
        this.$router.push("/");
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
};
</script>

<style>
.container-background {
  background: linear-gradient(to bottom, #e0e0e0, #bdbdbd);
  /* Degradado gris claro a gris oscuro */
  min-height: 100vh;
  /* Asegura que cubra toda la altura de la ventana */
  display: flex;
  justify-content: center;
  align-items: center;
  /* Centra el contenido en la página */
}
.f1-container {
  background: #ffffff;
  font-family: 'Roboto', sans-serif;
  max-width: px;
  margin: 40vh auto 0 auto;
  margin-top: 200px;
  /* Centrar verticalmente */
  transform: translateY(-50%);
  /* Ajustar la posición para centrar exactamente */
  padding: 20px;
  border: 2px solid #ff1801;
  /* Inspirado en los colores de Ferrari */
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
}
</style>