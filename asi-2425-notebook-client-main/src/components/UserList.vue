<template>
    <h1>Lista de Usuarios</h1>
    <div class="containerListaUsuarios">

      <div class="listaUsurarios">
        <h2 class="tituloUsers">Administradores</h2>
        <ul>
          <li v-for="user in admins" :key="user.id">{{ user.login }}</li>
        </ul>
      </div>

      <div class="listaUsurarios">
        <h2 class="tituloUsers">Usuarios</h2>
        <ul>
          <li v-for="user in users" :key="user.id">{{ user.login }}</li>
        </ul>
      </div>
    </div>
  </template>

<script>
import UserRepository from "../repositories/UserRepository";

export default {
  data() {
    return {
      users: [],
      admins: []
    };
  },
  async mounted() {
    let users = await UserRepository.findAll();
    console.log("all", users)

    this.admins = users.filter(user => user.authority === "ADMIN");
    console.log("admins", this.admins)
    this.users = users.filter(user => user.authority === "USER");
    console.log("users",this.users)

  }
};

</script>

<style>
.containerListaUsuarios {
  display: flex;
  justify-content: space-between;
  padding: 20px;
}

.listaUsurarios {
  list-style-type: none;
  padding: 0;
}

.tituloUsers {
  text-align: center;
  margin-bottom: 10px;
}

</style>