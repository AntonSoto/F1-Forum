<template>
  <nav class="navbar navbar-expand-lg bg-light">
    <div class="container-fluid">
      <router-link class="navbar-brand" to="/notes"> Notebook 2024/2025 </router-link>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0" v-if="store.state.user.logged">
          <li class="nav-item">
            <router-link class="nav-link" to="/about" active-class="active">
              Acerca de
            </router-link>
          </li>
          <li class="nav-item dropdown">
            <a
              class="nav-link dropdown-toggle"
              href="#"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
              ref="dropdownElement"
            >
              Notas
            </a>
            <ul class="dropdown-menu">
              <li>
                <router-link class="dropdown-item" to="/notes" active-class="active"
                  >Lista de notas</router-link
                >
              </li>
              <li>
                <router-link class="dropdown-item" to="/asdf" active-class="active">
                  URL incorrecta
                </router-link>
              </li>
            </ul>
          </li>
        </ul>

        <span v-if="store.state.user.logged"> autenticado como {{ store.state.user.login }} </span>
        <ul class="navbar-nav">
          <!-- <li class="nav-item" v-if="!store.state.user.logged">
            <router-link class="nav-link" to="/login" active-class="active">
              Login
            </router-link>
          </li> -->
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

export default {
  data() {
    return {
      store: getStore()
    };
  },
  methods: {
    desautenticarme() {
      auth.logout();
      this.$router.push("/");
    }
  },
  watch: {
    $route(newValue) {
      if (this.store.state.user.logged) {
        if (["NoteList", "NoteListSetup"].includes(newValue.name)) {
          this.$refs.dropdownElement.classList.add("active");
        } else {
          this.$refs.dropdownElement.classList.remove("active");
        }
      }
    }
  }
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
.dropdown-menu {
  margin-top: 0px !important;
}
</style>
