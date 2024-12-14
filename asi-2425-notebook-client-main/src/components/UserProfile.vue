<template>

    <h1> Mi perfil</h1>
    <p>{{ user }}</p>
    <button class="nav-link" @click="desautenticarme()"> Logout </button>

</template>

<script>
import Swal from 'sweetalert2'
import AccountRepository from "@/repositories/AccountRepository";
import auth from "../common/auth";

export default {
  data() {
    return {
      user: {},
      newNote: true
    };
  },
  async mounted() {

      this.user = await AccountRepository.getAccount();
      console.log

  },
  methods: {
    desautenticarme() {
        Swal.fire({
            title: "Estás seguro?",
            text: "Vas a cerrar sesión en este dispositivo",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Estoy seguro"
        }).then(async (result) => {
            if (result.isConfirmed) {
                auth.logout();
                this.$router.push("/");
            }
        });

    }
  }
};

</script>

<style>


</style>