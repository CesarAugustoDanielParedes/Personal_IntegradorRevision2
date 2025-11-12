document.addEventListener('DOMContentLoaded', () => {
    // --- Lógica para Carrusel Principal (Hero) ---
    const heroCarousel = document.getElementById('heroCarousel');
    const heroPrevBtn = document.querySelector('.hero-carousel .carousel-control.prev');
    const heroNextBtn = document.querySelector('.hero-carousel .carousel-control.next');
    let heroCurrentIndex = 0;
    const heroItems = heroCarousel ? heroCarousel.querySelectorAll('.carousel-item') : [];
    const heroTotalItems = heroItems.length;
    let heroInterval;

    if (heroCarousel && heroTotalItems > 0) {
        function showHeroItem(index) {
            heroCarousel.style.transform = `translateX(${-index * 100}%)`;
            // Opcional: actualizar clase 'active' para indicadores o estilos
        }

        function nextHeroItem() {
            heroCurrentIndex = (heroCurrentIndex + 1) % heroTotalItems;
            showHeroItem(heroCurrentIndex);
        }

        function prevHeroItem() {
            heroCurrentIndex = (heroCurrentIndex - 1 + heroTotalItems) % heroTotalItems;
            showHeroItem(heroCurrentIndex);
        }

        heroNextBtn.addEventListener('click', () => {
            clearInterval(heroInterval);
            nextHeroItem();
            startHeroCarousel();
        });

        heroPrevBtn.addEventListener('click', () => {
            clearInterval(heroInterval);
            prevHeroItem();
            startHeroCarousel();
        });

        function startHeroCarousel() {
            heroInterval = setInterval(nextHeroItem, 5000); // Cambia cada 5 segundos
        }

        startHeroCarousel(); // Inicia el carrusel automáticamente
    }


    // --- Lógica para Carrusel de Productos Destacados ---
    const productCarouselContainer = document.getElementById('productosDestacados');
    const productPrevBtn = document.querySelector('.product-carousel-wrapper .prev-product');
    const productNextBtn = document.querySelector('.product-carousel-wrapper .next-product');

    if (productCarouselContainer) {
        // La funcionalidad de desplazamiento es diferente aquí, ya que usamos overflow-x: auto
        // y scroll-behavior: smooth para una experiencia más nativa.
        // Las flechas simplemente desplazarán el contenedor una cantidad fija.
        
        const scrollAmount = 300; // Cuántos píxeles desplazar por clic

        productNextBtn.addEventListener('click', () => {
            productCarouselContainer.scrollBy({
                left: scrollAmount,
                behavior: 'smooth'
            });
        });

        productPrevBtn.addEventListener('click', () => {
            productCarouselContainer.scrollBy({
                left: -scrollAmount,
                behavior: 'smooth'
            });
        });

        // Opcional: Ocultar/mostrar flechas si se llega al inicio/fin del scroll
        // Esto es más complejo y lo omitimos para mantenerlo simple.
    }
});