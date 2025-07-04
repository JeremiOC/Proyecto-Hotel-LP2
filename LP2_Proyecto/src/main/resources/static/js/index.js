function toggleSidebar() {
           const sidebar = document.getElementById('sidebar');
           sidebar.classList.toggle('collapsed');
       }

function toggleSubmenu(button) {
       const submenu = button.nextElementSibling;
       const isActive = button.classList.contains('active');
       
       document.querySelectorAll('.menu-toggle').forEach(btn => {
           btn.classList.remove('active');
           btn.nextElementSibling.classList.remove('active');
       });
       
       if (!isActive) {
           button.classList.add('active');
           submenu.classList.add('active');
       }
   }

   

document.querySelectorAll('.stat-card').forEach(card => {
       card.addEventListener('mouseenter', function() {
           this.style.transform = 'translateY(-10px) scale(1.02)';
       });
       
       card.addEventListener('mouseleave', function() {
           this.style.transform = 'translateY(0) scale(1)';
       });
   });