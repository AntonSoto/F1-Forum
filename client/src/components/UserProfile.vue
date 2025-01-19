<template>
  <div class="profile-container">
    <!-- Columna izquierda: Datos del perfil -->
    <div class>
      <h1 class="profile-title">Mi perfil</h1>
      <div class="profile-detail">
        <p><strong>ID:</strong> {{ user.id }}</p>
        <p><strong>Login:</strong> {{ user.login }}</p>
        <p><strong>Rol:</strong> {{ user.authority }}</p>
        <p><strong>Fecha de nacimiento:</strong> {{ formatFechaNacimiento(user.fechaNacimiento) || "No especificada" }}
        </p>
        <p><strong>Nombre:</strong> {{ user.nombrePila || "No especificado" }}</p>
        <p><strong>Apellidos:</strong> {{ user.apellidos || "No especificados" }}</p>
      </div>
      <button class="f1-button" @click="desautenticarme()">Logout</button>
      <button class="f1-button" @click="$router.push(`/profile/${user.id}/edit`)">Editar Contraseña</button>
      <button class="f1-button delete-btn" @click="deleteAccount">Eliminar Cuenta</button>
    </div>

    <!-- Columna derecha: Valoraciones -->
    <div>
      <div class="user-comments">
        <h2>Mis Valoraciones</h2>
        <div v-if="comentarios.length > 0">
          <ul>
            <li v-for="comentario in comentarios" :key="comentario.id">
              <p><strong>Id del Gran Premio:</strong> {{ comentario.granPremio }}</p>
              <p>{{ comentario.comentario }}</p>
              <button class="delete-comment-btn" @click="deleteComentario(comentario.id)">Eliminar</button>
            </li>
          </ul>
        </div>
        <p v-else>No has realizado comentarios aún.</p>
      </div>
    </div>
  </div>
</template>




<script>
import Swal from "sweetalert2";
import AccountRepository from "@/repositories/AccountRepository";
import auth from "../common/auth";
import UserRepository from "@/repositories/UserRepository";
import ValoracionRepository from "@/repositories/ValoracionRepository";

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
      comentarios: [], // Lista de comentarios del usuario
    };
  },
  async mounted() {
    try {
      // Obtiene los datos del usuario actual
      const fetchedUser = await AccountRepository.getAccount();
      this.user = {
        id: fetchedUser.id,
        login: fetchedUser.login,
        authority: fetchedUser.authority,
        fechaNacimiento: fetchedUser.fechaNacimiento,
        nombrePila: fetchedUser.nombrePila,
        apellidos: fetchedUser.apellidos,
      };
      // Obtiene los comentarios del usuario
      await this.fetchComentarios();
    } catch (error) {
      console.error("Error al obtener los datos del usuario o sus comentarios:", error);
    }
  },
  methods: {
    async deleteComentario(id) {
      try {
        const confirm = await Swal.fire({
          title: "¿Estás seguro?",
          text: "Esta acción eliminará el comentario permanentemente.",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#d33",
          cancelButtonColor: "#3085d6",
          confirmButtonText: "Eliminar",
        });

        if (confirm.isConfirmed) {
          await ValoracionRepository.delete(id);
          Swal.fire({
            title: "Comentario eliminado",
            text: "El comentario ha sido eliminado correctamente.",
            icon: "success",
            timer: 2000,
          });
          // Recarga los comentarios después de eliminar uno
          await this.fetchComentarios();
        }
      } catch (error) {
        console.error("Error al eliminar el comentario:", error);
        Swal.fire({
          title: "Error",
          text: "Hubo un problema al eliminar el comentario.",
          icon: "error",
        });
      }
    },
    async fetchComentarios() {
      try {
        this.comentarios = await ValoracionRepository.findAllByUser();
      } catch (error) {
        console.error("Error al obtener los comentarios del usuario:", error);
      }
    },
    formatFechaNacimiento(fecha) {
      if (!fecha) return null;
      const date = new Date(fecha);
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, "0");
      const day = String(date.getDate()).padStart(2, "0");
      return `${year}-${month}-${day}`;
    },
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
            await UserRepository.deleteUser(this.user.id);
            Swal.fire({
              title: "Cuenta eliminada",
              text: "Tu cuenta ha sido eliminada correctamente.",
              icon: "success",
              timer: 2000,
            });
            auth.logout();
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

.user-comments {
  margin-top: 20px;
  border-top: 1px solid #ddd;
  padding-top: 20px;
}

.user-comments h2 {
  color: #ff1801;
  margin-bottom: 10px;
}

.user-comments ul {
  list-style-type: none;
  padding: 0;
}

.user-comments li {
  margin-bottom: 15px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #f9f9f9;
}

.user-comments li p {
  margin: 0;
}

.user-comments li p strong {
  color: #333;
}
</style>