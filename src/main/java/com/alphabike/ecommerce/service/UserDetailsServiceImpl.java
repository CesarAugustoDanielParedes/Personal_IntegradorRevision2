package com.alphabike.ecommerce.service;

import com.alphabike.ecommerce.model.Cliente;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy; // <-- ¡IMPORTACIÓN CLAVE!
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final ClienteService clienteService;



public UserDetailsServiceImpl(@Lazy ClienteService clienteService) {
this.clienteService = clienteService;
}



@Override
 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
// En nuestro caso, el "username" es el email
 Cliente cliente = clienteService.findByEmail(username);

 if (cliente == null) {
throw new UsernameNotFoundException("Usuario no encontrado con email: " + username);
}

// --- Definición de Roles/Autoridades ---
List<GrantedAuthority> authorities = new ArrayList<>();
 
 // La lógica temporal para el rol (ID = 1 es ADMIN) sigue siendo válida para la prueba.
  if (cliente.getId() == 1) { 
 authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
 } else {
 authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
 }
 // Retorna el objeto UserDetails que Spring Security necesita
 return new User(
 cliente.getEmail(), 
 cliente.getPassword(), 
 authorities
 );
}
}