<template>
  <div class="f1-container">
    <h1 class="profile-title">Mi perfil</h1>
    <div class="profile-detail">
      <p><strong>ID:</strong> {{ user.id }}</p>
      <p><strong>Login:</strong> {{ user.login }}</p>
      <p><strong>Rol:</strong> {{ user.authority }}</p>
      <p><strong>Fecha de nacimiento:</strong> {{ user.fechaNacimiento || "No especificada" }}</p>
      <p><strong>Nombre:</strong> {{ user.nombrePila || "No especificado" }}</p>
      <p><strong>Apellidos:</strong> {{ user.apellidos || "No especificados" }}</p>
    </div>
    <button class="f1-button" @click="desautenticarme()">Logout</button>
  </div>
</template>

<script>
import Swal from 'sweetalert2';
import AccountRepository from "@/repositories/AccountRepository";
import auth from "../common/auth";

export default {
  data() {
    return {
      user: {
        id: null,
        login: "",
        authority: "",
        fechaNacimiento: null,
        nombrePila: null,
        apellidos: null,
      },
    };
  },
  async mounted() {
    try {
      const fetchedUser = await AccountRepository.getAccount();
      this.user = {
        id: fetchedUser.id,
        login: fetchedUser.login,
        authority: fetchedUser.authority,
        fechaNacimiento: fetchedUser.fechaNacimiento,
        nombrePila: fetchedUser.nombrePila,
        apellidos: fetchedUser.apellidos,
      };
    } catch (error) {
      console.error("Error al obtener los datos del usuario:", error);
    }
  },
  methods: {
    desautenticarme() {
      Swal.fire({
        title: "¿Estás seguro?",
        text: "Vas a cerrar sesión en este dispositivo",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Estoy seguro",
      }).then(async (result) => {
        if (result.isConfirmed) {
          auth.logout();
          this.$router.push("/");
        }
      });
    },
  },
};
</script>

<style>

.profile-title {
  text-align: center;
  color: #ff1801;
  margin-bottom: 20px;
  font-size: 28px;
  font-weight: bold;
}

.profile-detail {
  margin: 20px 0;
  font-size: 16px;
  line-height: 1.8;
}
</style>