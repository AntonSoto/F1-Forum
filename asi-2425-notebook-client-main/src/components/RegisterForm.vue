<template>
    <div>
      <h1>Registrarse</h1>
      <div class="mb-3 mt-3">
        <label for="login">Login: </label>
        <input type="text" id="login" v-model="auxLogin"/>
      </div>
      <div class="mb-3 mt-3">
        <label for="login">Nombre: </label>
        <input type="text" id="nombre" v-model="auxNombre"/>
      </div>
      <div class="mb-3 mt-3">
        <label for="login">Apellidos: </label>
        <input type="text" id="apellidos" v-model="auxApellidos"/>
      </div>
      <div class="mb-3">
        <label for="pass">Fecha Nacimiento: </label>
        <input type="date" id="fechaN" v-model="auxFechaN"/>
      </div>
      <div class="mb-3">
        <label for="pass">Password: </label>
        <input type="password" id="pass" v-model="auxPass"/>
      </div>
      <div class="mb-3">
        <label for="pass">Confirm Password: </label>
        <input type="password" id="pass" v-model="auxConfirmPass"/>
      </div>
      <div>
        <button @click="registrarme()">registrarse</button>
      </div>
      <div>
      <router-link :to="'/'">Log In</router-link>
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
        if(this.auxPass == this.auxConfirmPass){
            try {
                await auth.register({
                    login: this.auxLogin,
                    password: this.auxPass,
                    nombrePila: this.auxNombre,
                    apellidos: this.auxApellidos,
                    fechaNacimiento: this.auxFechaN
                });
                try{
                    await auth.login({
                    login: this.auxLogin,
                    password: this.auxPass,
                });
                }
                catch (e) {
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
  