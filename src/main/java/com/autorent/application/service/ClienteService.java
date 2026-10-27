package com.autorent.application.service;

import com.autorent.application.port.input.ConsultarClientesUseCase;
import com.autorent.application.port.input.EditarClienteUseCase;
import com.autorent.application.port.input.RegistrarClienteUseCase;
import com.autorent.application.port.output.AlquilerRepositoryPort;
import com.autorent.application.port.output.ClienteRepositoryPort;
import com.autorent.domain.exception.*;
import com.autorent.domain.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class ClienteService implements RegistrarClienteUseCase, ConsultarClientesUseCase, EditarClienteUseCase {

    private static final Pattern EMAIL_VALIDO = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private final ClienteRepositoryPort clienteRepositoryPort;
    private final AlquilerRepositoryPort alquilerRepositoryPort;

    public ClienteService(ClienteRepositoryPort clienteRepositoryPort, AlquilerRepositoryPort alquilerRepositoryPort) {
        this.clienteRepositoryPort = clienteRepositoryPort;
        this.alquilerRepositoryPort = alquilerRepositoryPort;
    }

    @Override
    public Cliente registrarCliente(Cliente cliente) {
        validarDatosRequeridos(cliente);
        limpiarDatos(cliente);
        validarEmail(cliente.getEmail());
        if (clienteRepositoryPort.existsBydocumento(cliente.getDocumento())) {
            throw new ClienteDuplicadoException(cliente.getDocumento());
        }
        if (clienteRepositoryPort.existsByemail(cliente.getEmail())) {
            throw new EmailDuplicadoException(cliente.getEmail());
        }
        return clienteRepositoryPort.registrar(cliente);
    }
    private void validarDatosRequeridos(Cliente cliente) {
        Validaciones.requerido(cliente.getNombre(), "Nombre");
        Validaciones.requerido(cliente.getDocumento(), "Documento");
        Validaciones.requerido(cliente.getEmail(), "Email");
        Validaciones.requerido(cliente.getTelefono(), "Teléfono");
    }

    private void limpiarDatos(Cliente cliente) {
        cliente.setNombre(cliente.getNombre().trim());
        cliente.setDocumento(cliente.getDocumento().trim());
        cliente.setEmail(cliente.getEmail().trim().toLowerCase());
        cliente.setTelefono(cliente.getTelefono().trim());
    }

    private void validarEmail(String email) {
        if (!EMAIL_VALIDO.matcher(email).matches()) {
            throw new DatoInvalidoException("El email no tiene un formato válido: " + email);
        }
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepositoryPort.listarClientes();
    }
    @Override
    public Cliente obtenerClientePorId(Long id) {
        return clienteRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new ClienteNoEncontradoException("Cliente no encontrado: " + id));
    }

    @Override
    public Cliente editarCliente(Long id, Cliente datos) {
        Cliente cliente = clienteRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new ClienteNoEncontradoException("Cliente no encontrado: " + id));

        if (alquilerRepositoryPort.existeAlquilerActivoPorCliente(id)) {
            throw new EdicionNoPermitidaException(
                    "El cliente tiene un alquiler activo. Se puede editar cuando lo finalice");
        }

        validarDatosRequeridos(datos);
        limpiarDatos(datos);
        validarEmail(datos.getEmail());

        if (!datos.getDocumento().equals(cliente.getDocumento())
                && clienteRepositoryPort.existsBydocumento(datos.getDocumento())) {
            throw new ClienteDuplicadoException(datos.getDocumento());
        }
        if (!datos.getEmail().equals(cliente.getEmail())
                && clienteRepositoryPort.existsByemail(datos.getEmail())) {
            throw new EmailDuplicadoException(datos.getEmail());
        }

        cliente.actualizarDatos(datos);
        return clienteRepositoryPort.registrar(cliente);
    }
}
