function seleccionarHabitacion(card) {
    document.querySelectorAll('.card-habitacion').forEach(el => el.classList.remove('seleccionada'));
    card.classList.add('seleccionada');
    document.getElementById('habitacionId').value = card.dataset.id;
}
function abrirModalHabitaciones() {
    document.getElementById('modalHabitaciones').style.display = 'block';
}

function cerrarModalHabitaciones() {
    document.getElementById('modalHabitaciones').style.display = 'none';
}

function seleccionarHabitacionModal(card) {
    const id = card.getAttribute('data-id');
    const numero = card.getAttribute('data-numero');
    const descripcion = card.getAttribute('data-descripcion');

    document.getElementById('habitacionId').value = id;
    document.getElementById('habitacionInput').value = `N° ${numero} - ${descripcion}`;
    cerrarModalHabitaciones();
}
document.addEventListener('DOMContentLoaded', () => {
    const input = document.getElementById('buscadorCliente');
    const resultado = document.getElementById('resultadoClientes');
    const hiddenInput = document.getElementById('clienteId');
    const clientes = JSON.parse(document.getElementById('clientesData').textContent); // <- lo agregaremos abajo
	// Para mostrar el autocompletado
	document.getElementById('resultadoClientes').classList.add('show');

	// Para ocultarlo
	document.getElementById('resultadoClientes').classList.remove('show');
    input.addEventListener('input', () => {
        const texto = input.value.toLowerCase();
        resultado.innerHTML = '';

        if (texto.length < 2) return;

        const filtrados = clientes.filter(cli =>
            (cli.nombres + ' ' + cli.apellidoPaterno + ' ' + cli.apellidoMaterno + ' ' + cli.nroDocumento)
                .toLowerCase().includes(texto)
        );

        filtrados.forEach(cli => {
            const div = document.createElement('div');
            div.textContent = `${cli.nombres} ${cli.apellidoPaterno} ${cli.apellidoMaterno} (${cli.nroDocumento})`;
            div.onclick = () => {
                input.value = div.textContent;
                hiddenInput.value = cli.id;
                resultado.innerHTML = '';
            };
            resultado.appendChild(div);
        });
    });
	
    document.addEventListener('click', function (e) {
        if (!resultado.contains(e.target) && e.target !== input) {
            resultado.innerHTML = '';
        }
    });
});
