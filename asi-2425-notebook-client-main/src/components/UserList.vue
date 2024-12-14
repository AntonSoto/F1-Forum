<template>
    <h1>Lista de Usuarios</h1>
    <div class="containerListaUsuarios">

      <div class="listaUsurarios">
        <h2 class="tituloUsers">Administradores</h2>
        <ul class="listaUsurarios">
          <li v-for="user in admins" :key="user.id">{{ user.login }}</li>
        </ul>
      </div>

      <div class="listaUsurarios">
        <h2 class="tituloUsers">Usuarios</h2>
        <ul class="listaUsurarios">
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
                    try {
                        let users = await UserRepository.findAll();

                        this.admins = users.filter(user => user.authority === "ADMIN");
                        this.users = users.filter(user => user.authority === "USER");
                        
                        
                    } catch (err) {
                        console.error(err);
                    }
                    
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