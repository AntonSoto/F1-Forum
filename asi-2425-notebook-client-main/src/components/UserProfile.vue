<template>

    <h1> Mi perfil</h1>
    <p>{{ user }}</p>


</template>

<script>
import AccountRepository from "@/repositories/AccountRepository";

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
};

</script>

<style>


</style>