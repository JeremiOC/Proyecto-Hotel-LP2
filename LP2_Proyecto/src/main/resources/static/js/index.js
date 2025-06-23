function toggleSidebar() {
           const sidebar = document.getElementById('sidebar');
           sidebar.classList.toggle('collapsed');
       }

       function toggleSubmenu(button) {
           const submenu = button.nextElementSibling;
           const isActive = button.classList.contains('active');
           
           // Cerrar todos los submenús
           document.querySelectorAll('.menu-toggle').forEach(btn => {
               btn.classList.remove('active');
               btn.nextElementSibling.classList.remove('active');
           });
           
           // Abrir el submenú clickeado si no estaba activo
           if (!isActive) {
               button.classList.add('active');
               submenu.classList.add('active');
           }
       }

       function loadContent(section) {
           const mainContent = document.querySelector('.main-content');
           
           // Aquí puedes agregar la lógica para cargar diferentes contenidos
           let content = '';
           
           switch(section) {
               case 'crear-usuario':
                   content = `
                       <div class="welcome-card">
                           <h2 class="welcome-title">Crear Usuario</h2>
                           <p class="welcome-subtitle">Formulario para crear nuevos usuarios del sistema</p>
                       </div>
                   `;
                   break;
               case 'listar-usuarios':
                   content = `
                       <div class="welcome-card">
                           <h2 class="welcome-title">Lista de Usuarios</h2>
                           <p class="welcome-subtitle">Visualiza todos los usuarios registrados en el sistema</p>
                       </div>
                   `;
                   break;
               case 'registrar-cliente':
                   content = `
                       <div class="welcome-card">
                           <h2 class="welcome-title">Registrar Cliente</h2>
                           <p class="welcome-subtitle">Formulario para registrar nuevos clientes</p>
                       </div>
                   `;
                   break;
               case 'buscar-cliente':
                   content = `
                       <div class="welcome-card">
                           <h2 class="welcome-title">Buscar Cliente</h2>
                           <p class="welcome-subtitle">Encuentra clientes por nombre, documento o teléfono</p>
                       </div>
                   `;
                   break;
               default:
                   content = `
                       <div class="welcome-card">
                           <h2 class="welcome-title">${section.replace('-', ' ').toUpperCase()}</h2>
                           <p class="welcome-subtitle">Funcionalidad en desarrollo</p>
                       </div>
                   `;
           }
           
           mainContent.innerHTML = content;
       }

       // Efecto de hover para las tarjetas de estadísticas
       document.querySelectorAll('.stat-card').forEach(card => {
           card.addEventListener('mouseenter', function() {
               this.style.transform = 'translateY(-10px) scale(1.02)';
           });
           
           card.addEventListener('mouseleave', function() {
               this.style.transform = 'translateY(0) scale(1)';
           });
       });