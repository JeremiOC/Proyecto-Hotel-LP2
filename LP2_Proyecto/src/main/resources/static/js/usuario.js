function switchTab(tabName) {
    // Ocultar todos los contenidos
    const contents = document.querySelectorAll('.tab-content');
    contents.forEach(content => content.classList.remove('active'));

    // Desactivar todas las pestañas
    const tabs = document.querySelectorAll('.tab');
    tabs.forEach(tab => tab.classList.remove('active'));

    // Mostrar el contenido seleccionado
    document.getElementById(tabName).classList.add('active');

    // Activar la pestaña seleccionada
    event.target.classList.add('active');
}

function buscarUsuarios() {
    const termino = document.getElementById('busqueda').value;
    console.log('Buscando usuarios con término:', termino);
}

document.getElementById('imagen').addEventListener('change', function(e) {
    const label = document.querySelector('.file-upload-label');
    if (e.target.files.length > 0) {
        label.textContent = '📷 ' + e.target.files[0].name;
    } else {
        label.textContent = '📷 Seleccionar imagen...';
    }
});

document.getElementById('correo').addEventListener('blur', function(e) {
    const email = e.target.value;
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (email && !emailRegex.test(email)) {
        e.target.style.borderColor = '#dc3545';
    } else {
        e.target.style.borderColor = '#e0e6ed';
    }
});

document.getElementById('busqueda').addEventListener('input', function(e) {
    const termino = e.target.value.toLowerCase();
    const filas = document.querySelectorAll('#tablaUsuarios tr');
    
    filas.forEach(fila => {
        const texto = fila.textContent.toLowerCase();
        if (texto.includes(termino) || termino === '') {
            fila.style.display = '';
        } else {
            fila.style.display = 'none';
        }
    });
});