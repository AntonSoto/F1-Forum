<template>
    <div class="circuitos-container">
      <h2 class="circuitos-title">Circuitos</h2>
      
      <!-- Carrusel -->
    <div class="carousel-container">
      <div class="carousel">
        <!-- Imagen clickeable -->
        <a class= "carousel" :href="'/circuitos/' + images[currentIndex].name">
          <img :src="images[currentIndex].src" :alt="images[currentIndex].name" class="carousel-image" />
        </a>
      </div>
  
        <button @click="prevImage" class="carousel-button prev">‹</button>
        <button @click="nextImage" class="carousel-button next">›</button>
  
        <div class="indicator">
          <span v-for="(image, index) in images" :key="index" :class="['dot', { active: index === currentIndex }]"></span>
        </div>
        
        <!-- Enlace debajo de la imagen -->
        <div class="circuito-link">
          <a :href="'/circuitos/' + images[currentIndex].name" class="circuito-name">
            Ver circuito: {{ images[currentIndex].name }}
          </a>
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
  </style>
  