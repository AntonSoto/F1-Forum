<template>
  <div class="container">
    <form>
      <div class="mb-3">
        <label for="title" class="form-label">Título</label>
        <input type="text" class="form-control" id="title" v-model="note.title" />
      </div>
      <div class="mb-3">
        <label for="content" class="form-label">Content</label>
        <textarea
          name="content"
          id="content"
          class="form-control"
          rows="3"
          v-model="note.content"
        ></textarea>
      </div>
      <div v-if="!newNote">
        <label for="archived" class="form-label">Archivada</label>
        <input type="checkbox" id="archived" v-model="note.archived" />
      </div>
      <button class="btn btn-primary" @click.prevent="guardarNota">Guardar</button>
    </form>
  </div>
</template>

<script>
import NoteRepository from "@/repositories/NoteRepository";

export default {
  data() {
    return {
      note: {},
      newNote: true
    };
  },
  async mounted() {
    if (this.$route.params.noteId) {
      this.newNote = false;
      this.note = await NoteRepository.findOne(this.$route.params.noteId);
    }
  },
  methods: {
    async guardarNota() {
      try {
        const respuesta = await NoteRepository.save(this.note);
        this.$router.push({ name: "NoteDetail", params: { noteId: respuesta.id } });
      } catch (err) {
        console.error(err);
      }
    }
  }
};
</script>
