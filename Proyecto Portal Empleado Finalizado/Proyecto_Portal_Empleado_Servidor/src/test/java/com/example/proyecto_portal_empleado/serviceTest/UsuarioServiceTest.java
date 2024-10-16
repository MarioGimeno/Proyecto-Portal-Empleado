package com.example.proyecto_portal_empleado.serviceTest;

import com.example.proyecto_portal_empleado.model.Usuario;
import com.example.proyecto_portal_empleado.repository.UsuarioRepository;
import com.example.proyecto_portal_empleado.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario1;
    private Usuario usuario2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        /*Llenamos los datos con ejemplos reales
        usuario1 = new Usuario();
        usuario1.setIdUsuario(1);
        usuario1.setNombre("Juan");
        usuario1.setApellidos("Pérez");
        usuario1.setCorreo("juan.perez@example.com");
        usuario1.setContrasena("password123");

        usuario2 = new Usuario();
        usuario2.setIdUsuario(2);
        usuario2.setNombre("María");
        usuario2.setApellidos("Gómez");
        usuario2.setCorreo("maria.gomez@example.com");
        usuario2.setContrasena("password456");
    }

    @Test
    void getAllUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario1, usuario2));

        assertEquals(2, usuarioService.getAllUsuarios().size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void getUsuarioById() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario1));

        Optional<Usuario> result = usuarioService.getUsuarioById(1);

        assertTrue(result.isPresent());
        assertEquals(usuario1, result.get());
        verify(usuarioRepository, times(1)).findById(1);
    }

    @Test
    void createUsuario() {
        when(usuarioRepository.save(usuario1)).thenReturn(usuario1);

        Usuario result = usuarioService.createUsuario(usuario1);

        assertEquals(usuario1, result);
        verify(usuarioRepository, times(1)).save(usuario1);
    }

    @Test
    void updateUsuario() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario1));
        when(usuarioRepository.save(usuario1)).thenReturn(usuario1);

        Usuario result = usuarioService.updateUsuario(1, usuario1);

        assertEquals(usuario1, result);
        verify(usuarioRepository, times(1)).save(usuario1);
    }

    @Test
    void deleteUsuario() {
        when(usuarioRepository.existsById(1)).thenReturn(true);

        boolean result = usuarioService.deleteUsuario(1);

        assertTrue(result);
        verify(usuarioRepository, times(1)).deleteById(1);
    }*/
    }
}