<template>
  <div class="card" style="width: 18rem">
    <div class="card-body">
      <h6 class="card-subtitle mb-2 text-muted">
        <router-link class="card-link" :to="'/user/' + note.owner">
          {{ note.owner }}
        </router-link>
      </h6>
      <p class="card-text">
        {{ note.title }}
      </p>
      <p class="card-text">
        {{ note.content }}
      </p>
      <p class="card-text">
        {{ note.timestamp.toLocaleDateString() }} -
        {{ note.timestamp.toLocaleTimeString() }}
      </p>
      <h6 class="card-subtitle mb-2 text-muted">{{ categoriesAsString }}</h6>

      <router-link v-if="showDetailsButton" class="btn btn-primary" :to="'/notes/' + note.id">
        detalles
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    note: {
      type: Object,
      required: true
    },
    showDetailsButton: {
      type: Boolean,
      default: true
    }
  },
  computed: {
    categoriesAsString() {
      return this.note?.categories.map((t) => t.name).join(", ");
    }
  }
};
</script>

<style scoped>
h6::after {
  content: "\00a0 ";
}
</style>
