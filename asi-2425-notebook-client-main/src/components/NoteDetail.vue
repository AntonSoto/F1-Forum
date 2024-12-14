<template>
  <div>
    <h1>Detalle de una nota</h1>
    <div v-if="note">
      <div>
        <span>Título: </span>
        <span>{{ note.title }}</span>
      </div>
      <div>
        <span>Contenido: </span>
        <span>{{ note.content }}</span>
      </div>
      <div v-if="isAdmin">
        <span>Usuario: </span>
        <span>{{ note.owner }}</span>
      </div>
      <div>
        <span>Categorías: </span>
        <span>{{ note.categories.map((c) => c.name).join(", ") }}</span>
      </div>
      <div>
        <span>Fecha de creación: </span>
        <span>{{ note.timestamp.toLocaleString() }}</span>
      </div>
      <div>
        <span>Archivado: </span>
        <input type="checkbox" v-model="note.archived" disabled v-if="isAdmin" />
        <input
          type="checkbox"
          v-model="note.archived"
          @change="alternarArchivado"
          v-if="!isAdmin"
        />
      </div>
    </div>
    <div v-else>Cargando la nota...</div>
  </div>
</template>

<script>
import NoteRepository from "../repositories/NoteRepository.js";
import auth from "@/common/auth.js";

export default {
  data() {
    return {
      note: null,
      isAdmin: auth.isAdmin()
    };
  },
  async mounted() {
    this.note = await NoteRepository.findOne(this.$route.params.noteId);
  },
  methods: {
    async alternarArchivado() {
      try {
        await NoteRepository.save(this.note);
      } catch (err) {
        console.error(err);
      }
    }
  }
};
</script>
