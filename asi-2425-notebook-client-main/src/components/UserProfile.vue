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
    <button class="f1-button" @click="$router.push(`/profile/${user.id}/edit`)">Editar Contraseña</button>
    <!-- Nuevo botón para eliminar cuenta -->
    <button class="f1-button delete-btn" @click="deleteAccount">Eliminar Cuenta</button>
  </div>
</template>


<script>
import Swal from 'sweetalert2';
import AccountRepository from "@/repositories/AccountRepository";
import auth from "../common/auth";
import UserRepository from '@/repositories/UserRepository';

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
    // Función para cerrar sesión
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

    // Función para eliminar la cuenta
    async deleteAccount() {
      Swal.fire({
        title: "¿Estás seguro?",
        text: "Esta acción eliminará permanentemente tu cuenta y todos tus datos.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "Eliminar Cuenta",
      }).then(async (result) => {
        if (result.isConfirmed) {
          try {
            // Llamar al repositorio para eliminar la cuenta
            await UserRepository.deleteUser(this.user.id);
            Swal.fire({
              title: "Cuenta eliminada",
              text: "Tu cuenta ha sido eliminada correctamente.",
              icon: "success",
              timer: 2000,
            });
            // Redirigir a la página de login después de la eliminación
            auth.logout();  // Asegura que se cierre la sesión
            this.$router.push("/login");
          } catch (error) {
            console.error("Error al eliminar la cuenta:", error);
            Swal.fire({
              title: "Error",
              text: "Hubo un problema al eliminar tu cuenta.",
              icon: "error",
            });
          }
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