<template>
  <nav class="navbar navbar-expand-lg bg-light">
    <div class="container-fluid">
      <router-link class="navbar-brand" to="/circuitos"> F1 FORUM </router-link>
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
          <li class="nav-item" v-if="auth.isAdmin()">
            <router-link class="nav-link" to="/users" active-class="active">
              Usuarios
            </router-link>
          </li>
        
        </ul>

        <span v-if="store.state.user.logged"> autenticado como&nbsp; </span>
        <router-link :to="'/profile/' + user.id" v-if="store.state.user.logged">{{ store.state.user.login }} </router-link>
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
import AccountRepository from "./repositories/AccountRepository";

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
      auth.logout();
      this.$router.push("/");
    }
  },
  watch: {
    async $route(newValue) {
      if (this.store.state.user.logged) {
        try {
          this.user = await AccountRepository.getAccount();
          console.log(this.user.id)
          if (["NoteList", "NoteListSetup"].includes(newValue.name)) {
            this.$refs.dropdownElement.classList.add("active");
          } else {
            this.$refs.dropdownElement.classList.remove("active");
          }
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
