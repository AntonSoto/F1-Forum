<template>
  <nav class="navbar navbar-expand-lg bg-light">
    <div class="container-fluid">
      <router-link class="navbar-brand" to="/"> F1 FORUM </router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <router-link class="nav-link" to="/pilotos" active-class="active">
              Pilotos
            </router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/constructores" active-class="active">
              Constructores
            </router-link>
          </li>

          <!-- Enlace de Usuarios solo para administradores -->
          <li class="nav-item" v-if="auth.isAdmin() && store.state.user.logged">
            <router-link class="nav-link" to="/users" active-class="active">
              Usuarios
            </router-link>
          </li>
        </ul>

        <span v-if="store.state.user.logged"> Autenticado como&nbsp; </span>
        <router-link :to="'/profile/' + user.id" v-if="store.state.user.logged">{{ store.state.user.login }}</router-link>

        <ul class="navbar-nav">
          <!-- Mostrar el enlace de Login si el usuario no está autenticado -->
          <li class="nav-item" v-if="!store.state.user.logged">
            <router-link class="nav-link" to="/login" active-class="active">
              Login
            </router-link>
          </li>
          <li class="nav-item" v-if="store.state.user.logged">
            <a class="nav-link" @click="desautenticarme()"> Logout </a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <router-view />
</template>

<script>
import { getStore } from "./common/store";
import auth from "./common/auth";
import AccountRepository from "./repositories/AccountRepository";
import Swal from 'sweetalert2';

export default {
  data() {
    return {
      store: getStore(),
      user: {},
      auth
    };
  },
  methods: {
    desautenticarme() {
    Swal.fire({
      title: "¿Estás seguro?",
      text: "Se cerrará tu sesión actual.",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Sí, cerrar sesión",
      cancelButtonText: "Cancelar",
    }).then((result) => {
      if (result.isConfirmed) {
        auth.logout(); // Realiza el logout
        this.$router.push("/"); // Redirige al inicio
        Swal.fire("¡Sesión cerrada!", "Has cerrado tu sesión correctamente.", "success");
      }
    });
  }
  },
  watch: {
    async $route(newValue) {
      if (this.store.state.user.logged) {
        try {
          this.user = await AccountRepository.getAccount();
          // Se eliminó la manipulación con dropdownElement ya que no está presente en el template
        } catch (error) {
          console.error("Error fetching account:", error);
        }
      }
    }
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
  cursor: pointer;
}

/* fixing popper warning in bootstrap 5.2: */
</style>
