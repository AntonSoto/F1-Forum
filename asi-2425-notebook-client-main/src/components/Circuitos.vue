<template>
    <div class="circuitos-container">
      <h2 class="circuitos-title">Circuitos</h2>
  
      <!-- Carrusel -->
      <div class="carousel-container">
        <div class="carousel">
          <!-- Imagen clickeable -->
          <a class="carousel" :href="'/circuitos/' + images[currentIndex].name">
            <img :src="images[currentIndex].src" :alt="images[currentIndex].name" class="carousel-image" />
          </a>
        </div>
  
        <button @click="prevImage" class="carousel-button prev">‹</button>
        <button @click="nextImage" class="carousel-button next">›</button>
        
        <div class="indicator">
          <span v-for="(image, index) in images" :key="index"
                :class="['dot', { active: index === currentIndex }]"></span>
        </div>
  
        <!-- Enlace debajo de la imagen -->
        <div class="circuito-link">
          <a :href="'/circuitos/' + images[currentIndex].name" class="circuito-name">
            Ver circuito: {{ images[currentIndex].name }}
          </a>
        </div>
      </div>
  
      <!-- Lista de todos los circuitos con foto -->
      <div class="circuitos-list">
        <h3>Todos los Circuitos</h3>
        <div class="circuitos-list-container">
          <div class="circuitos-row">
            <div class="circuito-item" v-for="(image, index) in images" :key="index">
              <a :href="'/circuitos/' + image.name" class="circuito-link-item">
                <!-- Imagen del circuito -->
                <img :src="image.src" :alt="image.name" class="circuito-thumbnail" />
                <!-- Nombre del circuito -->
                <span>{{ image.name }}</span>
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  // Importa las imágenes directamente
  import monza from '@/assets/images/monza.jpg';
  import bahrein from '@/assets/images/americas.jpg';
  import singapur from '@/assets/images/singapur.jpg';
  
  export default {
    data() {
      return {
        currentIndex: 0,
        // Incluye el nombre de la imagen y la ruta en el array de imágenes
        images: [
          { src: monza, name: 'monza' },
          { src: bahrein, name: 'americas' },
          { src: singapur, name: 'singapur' },
        ],
      };
    },
    methods: {
      nextImage() {
        this.currentIndex = (this.currentIndex + 1) % this.images.length;
      },
      prevImage() {
        this.currentIndex =
          (this.currentIndex - 1 + this.images.length) % this.images.length;
      },
    },
  };
  </script>
  
  <style scoped>
  .circuitos-container {
    padding: 30px;
    text-align: center;
  }
  
  .circuitos-title {
    font-size: 24px;
    color: #ff1801; /* Color rojo Ferrari */
    margin-bottom: 20px;
  }
  
  .carousel-container {
    position: relative;
    width: 100%;
    max-width: 600px;
    margin: 0 auto;
    overflow: hidden;
    border-radius: 10px;
  }
  
  .carousel {
    width: 100%;
    height: 400px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  
  .carousel-image {
    width: 100%;
    height: auto;
    object-fit: cover;
    border-radius: 10px;
  }
  
  .carousel-button {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    background-color: rgba(0, 0, 0, 0.5);
    color: white;
    border: none;
    font-size: 24px;
    padding: 10px;
    cursor: pointer;
    border-radius: 50%;
    z-index: 10;
  }
  
  .carousel-button.prev {
    left: 10px;
  }
  
  .carousel-button.next {
    right: 10px;
  }
  
  .indicator {
    text-align: center;
    margin-top: 10px;
  }
  
  .dot {
    display: inline-block;
    width: 12px;
    height: 12px;
    margin: 0 5px;
    background-color: #bbb;
    border-radius: 50%;
    cursor: pointer;
  }
  
  .dot.active {
    background-color: #ff1801; /* Rojo Ferrari */
  }
  
  /* Estilos para el enlace debajo de la imagen */
  .circuito-link {
    margin-top: 15px;
  }
  
  .circuito-name {
    font-size: 18px;
    color: #ff1801;
    text-decoration: none;
  }
  
  .circuito-name:hover {
    text-decoration: underline;
  }
  
  /* Estilos para la lista de circuitos */
  .circuitos-list {
    margin-top: 30px;
  }
  
  .circuitos-list h3 {
    font-size: 22px;
    color: #ff1801;
    margin-bottom: 15px;
  }
  
  .circuitos-list-container {
    display: flex;
    flex-wrap: wrap; /* Permite que los elementos se ajusten */
    justify-content: center;
    gap: 20px;
    padding: 0 20px;
  }
  
  .circuitos-row {
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
    width: 100%;
  }
  
  .circuito-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
    width: 30%; /* Tres elementos por fila */
  }
  
  .circuito-link-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    text-decoration: none;
    color: #333;
    transition: transform 0.3s ease;
  }
  
  .circuito-link-item:hover {
    transform: scale(1.05);
    color: #ff1801;
  }
  
  /* Imagen más grande y centrada */
  .circuito-thumbnail {
    width: 100%;
    height: auto;
    max-width: 150px; /* Tamaño de la imagen */
    border-radius: 10px;
    margin-bottom: 10px;
  }
  
  .circuito-link-item span {
    font-size: 18px;
    margin-top: 10px;
  }
  </style>
  