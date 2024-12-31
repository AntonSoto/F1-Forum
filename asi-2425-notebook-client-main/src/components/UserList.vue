<template>
  <h1>Lista de Usuarios</h1>
  <div class="containerListaUsuarios">
    <div class="listaUsurarios">
      <h2 class="tituloUsers">Administradores</h2>
      <ul class="usuariosList">
        <li v-for="user in admins" :key="user.id">{{ user.login }}</li>
      </ul>
    </div>

    <div class="listaUsurarios">
      <h2 class="tituloUsers">Usuarios</h2>
      <ul class="usuariosList">
        <li v-for="user in users" :key="user.id">
          {{ user.login }}
          <button @click="makeAdmin(user.id)">Convertir en administrador</button>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import Swal from 'sweetalert2'
import UserRepository from "../repositories/UserRepository";

export default {
  data() {
    return {
      users: [],
      admins: []
    };
  },
  methods: {
    makeAdmin(id) {
      Swal.fire({
        title: "Estás seguro?",
        text: "Vas a convertir un usuario en administrador",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Estoy seguro"
      }).then(async (result) => {
        if (result.isConfirmed) {
          Swal.fire({
            title: "Hecho!",
            text: "El usuario se ha convertido en admin.",
            icon: "success",
            timer: 2000
          });
          try {
            await UserRepository.makeAdmin(id);
            let users = await UserRepository.findAll();
            this.admins = users.filter(user => user.authority === "ADMIN");
            this.users = users.filter(user => user.authority === "USER");
          } catch (err) {
            console.error(err);
          }
        }
      });
    }
  },
  async mounted() {
    let users = await UserRepository.findAll();
    this.admins = users.filter(user => user.authority === "ADMIN");
    this.users = users.filter(user => user.authority === "USER");
  }
};
</script>

<style>
.containerListaUsuarios {
  display: flex;
  justify-content: space-between;
  padding: 20px;
  overflow-y: auto; /* Permite el desplazamiento si el contenido es demasiado grande */
}

.listaUsurarios {
  flex: 1; /* Hace que cada lista ocupe el mismo espacio */
  margin: 0 10px; /* Espacio entre las listas */
  background-color: #fff; /* Fondo blanco para cada sección */
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* Sombra ligera */
  padding: 15px;
  max-height: 100%; /* Maximo tamaño para que no se desborde */
  overflow-y: auto; /* Permite scroll si el contenido es largo */
}

.tituloUsers {
  text-align: center;
  margin-bottom: 15px;
  color: #ff1801; /* Color rojo Ferrari */
  font-size: 20px;
}

.usuariosList {
  list-style-type: none;
  padding: 0;
}

.usuariosList li {
  background-color: #f1f1f1; /* Fondo suave para cada usuario */
  padding: 10px;
  margin-bottom: 8px;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* Sombra suave */
  display: flex;
  justify-content: space-between;
  align-items: center;
}

button {
  background-color: #ff1801; /* Rojo Ferrari */
  color: #ffffff;
  padding: 5px 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #e60000; /* Rojo más oscuro al pasar el mouse */
}

button:focus {
  outline: none;
  box-shadow: 0 0 5px rgba(255, 24, 1, 0.8); /* Sombra cuando se hace clic */
}
</style>