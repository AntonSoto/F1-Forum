<template>
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
};
</script>

<style>

</style>