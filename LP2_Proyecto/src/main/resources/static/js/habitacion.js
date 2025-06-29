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


document.getElementById('busqueda').addEventListener('input', function(e) {
    const termino = e.target.value.toLowerCase();
    const filas = document.querySelectorAll('#tablaHabitaciones tr');
    
    filas.forEach(fila => {
        const texto = fila.textContent.toLowerCase();
        if (texto.includes(termino) || termino === '') {
            fila.style.display = '';
        } else {
            fila.style.display = 'none';
        }
    });
});
function limpiarFormulario() {
    document.getElementById('numero').value = '';
    document.getElementById('tipoId').value = '';
    document.getElementById('disponible').value = '';

}
function confirmarEliminacion(boton) {
    const id = boton.getAttribute("data-id");
    const nombre = boton.getAttribute("data-nombre");

    Swal.fire({
        title: `¿Estás seguro?`,
        text: `¿Deseas eliminar la habitacion ${nombre}?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#aaa',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = `/habitaciones/eliminar/${id}`;
        }
    });
}
