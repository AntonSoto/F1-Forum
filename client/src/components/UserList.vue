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
          <div>
            <button @click="makeAdmin(user.id)">Convertir en administrador</button>
            <button @click="deleteUser(user.id)" class="delete-btn">Eliminar</button>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import Swal from 'sweetalert2';
import UserRepository from "../repositories/UserRepository";

export default {
  data() {
    return {
      users: [],
      admins: []
    };
  },
  methods: {
    async makeAdmin(id) {
      Swal.fire({
        title: "¿Estás seguro?",
        text: "Vas a convertir un usuario en administrador.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Estoy seguro"
      }).then(async (result) => {
        if (result.isConfirmed) {
          try {
            await UserRepository.makeAdmin(id);
            Swal.fire({
              title: "Hecho",
              text: "El usuario ha sido convertido en administrador.",
              icon: "success",
              timer: 2000
            });
            await this.refreshUserList();
          } catch (err) {
            console.error(err);
            Swal.fire({
              title: "Error",
              text: "No se pudo convertir al usuario en administrador.",
              icon: "error"
            });
          }
        }
      });
    },
    async deleteUser(id) {
      Swal.fire({
        title: "¿Estás seguro?",
        text: "Esta acción eliminará al usuario y todas sus valoraciones.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "Eliminar"
      }).then(async (result) => {
        if (result.isConfirmed) {
          try {
            await UserRepository.deleteUser(id);
            Swal.fire({
              title: "Eliminado",
              text: "El usuario ha sido eliminado.",
              icon: "success",
              timer: 2000
            });
            await this.refreshUserList();
          } catch (err) {
            console.error(err);
            Swal.fire({
              title: "Error",
              text: "No se pudo eliminar al usuario.",
              icon: "error"
            });
          }
        }
      });
    },
    async refreshUserList() {
      const users = await UserRepository.findAll();
      this.admins = users.filter(user => user.authority === "ADMIN");
      this.users = users.filter(user => user.authority === "USER");
    }
  },
  async mounted() {
    await this.refreshUserList();
  }
};
</script>

<style>
.containerListaUsuarios {
  display: flex;
  justify-content: space-between;
  padding: 20px;
  overflow-y: auto;
}

.listaUsurarios {
  flex: 1;
  margin: 0 10px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  padding: 15px;
  max-height: 100%;
  overflow-y: auto;
}

.tituloUsers {
  text-align: center;
  margin-bottom: 15px;
  color: #ff1801;
  font-size: 20px;
}

.usuariosList {
  list-style-type: none;
  padding: 0;
}

.usuariosList li {
  background-color: #f1f1f1;
  padding: 10px;
  margin-bottom: 8px;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: space-between;
  align-items: center;
}

button {
  background-color: #ff1801;
  color: #ffffff;
  padding: 5px 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #e60000;
}

button:focus {
  outline: none;
  box-shadow: 0 0 5px rgba(255, 24, 1, 0.8);
}

.delete-btn {
  background-color: #d33;
  margin-left: 10px;
}

.delete-btn:hover {
  background-color: #b30000;
}
</style>
