package cron;

import domain.usuario.TipoDeUsuario;
import domain.usuario.Usuario;
import exceptions.UsuarioInexistente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioUsuarios {
    static private RepositorioUsuarios instancia = null;
    private List<Usuario> listaDeUsuarios = new ArrayList<>();

    private RepositorioUsuarios(){

    }

    public static RepositorioUsuarios getInstance(){
        if(instancia == null){
            return instancia = new RepositorioUsuarios();
        }
        return instancia;
    }


    public void agregarUsuario(Usuario usuario){
        if(!listaDeUsuarios.contains(usuario))
            listaDeUsuarios.add(usuario);
    }

    public void eliminarUsuario(Usuario usuario){
        if(listaDeUsuarios.contains(usuario))
            listaDeUsuarios.remove(usuario);
    }

    public Usuario buscarUsuario(String nombre){
        Usuario usuario = listaDeUsuarios.stream().filter(u -> u.getNombre().equals(nombre)).findFirst().orElse(null);
        if(usuario == null)
            throw new UsuarioInexistente();
        return usuario;
    }

    public List<Usuario> getListaDeUsuarios(){
        return listaDeUsuarios;
    }
}
