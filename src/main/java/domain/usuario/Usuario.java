package domain.usuario;

import clima.Clima;
import clima.Meteorologo;
import cron.Cron;
import cron.RepositorioUsuarios;
import domain.atuendo.Atuendo;
import domain.decision.Aceptar;
import domain.decision.Calificar;
import domain.decision.Decision;
import domain.decision.Rechazar;
import domain.evento.Evento;
import domain.evento.FrecuenciaEvento;
import domain.guardarropas.Guardarropas;
import domain.notificaciones.InteresadoAlertaMeteorologica;
import domain.notificaciones.InteresadoEvento;
import domain.notificaciones.MedioDeNotificacion;
import exceptions.*;

import java.lang.reflect.Member;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.mockito.cglib.core.Local;
import org.uqbar.commons.model.annotations.Observable;

import javax.persistence.*;

@Observable /** Necesario para poder usarse con arena */

@Entity
public class Usuario implements InteresadoEvento, InteresadoAlertaMeteorologica {

    @Id
    @GeneratedValue
    Long id;

    private List<Evento> eventos = new ArrayList<>();
    
    private String nombre;

    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name= "usuarioId")
    private List<Decision> decisiones = new ArrayList<>();

    @Transient
    private List<Atuendo> atuendosAceptados = new ArrayList<>();
    @Transient
    private List<Atuendo> atuendosRechazados = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private TipoDeUsuario tipoDeUsuario;


    @OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "usuarioId", nullable = false)
    private List<Guardarropas> guardarropas;

    @Transient
    private List<MedioDeNotificacion> mediosDeNotificacion = new ArrayList<>();

	





    /** Metodos */

    public Usuario(String nombre,List<Guardarropas> guardarropas, TipoDeUsuario tipoDeUsuario) {
    	this.nombre=nombre;
        this.tipoDeUsuario=tipoDeUsuario;
        guardarropas.stream().forEach( guardarropasAValidar -> validarTipoDeGuardarropas(guardarropasAValidar));
        this.guardarropas = guardarropas;
        RepositorioUsuarios.getInstance().agregarUsuario(this);

    }


    public void agregarGuardarropas (Guardarropas guardarropasAgregado){
        validarTipoDeGuardarropas(guardarropasAgregado);
        this.guardarropas.add(guardarropasAgregado);
    }

    public void validarTipoDeGuardarropas(Guardarropas guardarropasAValidar){

           if((guardarropasAValidar.tipoDeUsuarioQueAcepta() != tipoDeUsuario)){
               throw new ElGuardarropasNoEsAptoException();
           }
    }
    public List<Atuendo> obtenerSugerenciasDeTodosSusGuardarropas(Meteorologo meteorologo){
        return guardarropas.stream()
                .flatMap(guardarropas -> guardarropas.sugerirAtuendo(meteorologo).stream())
                .collect(Collectors.toList());
    }

    public List<Atuendo> obtenerSugerencias(int indexGuardarropas, Meteorologo meteorologo){
        if(indexGuardarropas<0 || guardarropas.size() >= indexGuardarropas)
            throw new NoExisteGuardarropasException();
        return guardarropas.get(indexGuardarropas).sugerirAtuendo(meteorologo);
    }

    public void crearEvento(String nombre, LocalDateTime fechaYHora, FrecuenciaEvento frecuencia, String lugar){
        eventos.add(new Evento(nombre, fechaYHora, frecuencia, lugar, this));
    }

    public List<Atuendo> recibirSugerenciasEvento(String nombreEvento, LocalDateTime fechaEvento){ //Tambien podriamos haber usado index en la lista
        return eventos.stream().filter(evento -> evento.seLlama(nombreEvento))
                .filter(evento -> evento.esEnLaFecha(fechaEvento))
                .collect(Collectors.toList())
                .get(0).obtenerSugerencias();
    }

    private Decision popDecision(){
       return decisiones.remove(decisiones.size()-1);
    }

    public void deshacerUltimaDecision(){
        if(this.decisiones.isEmpty())
            throw new NoHayDecisionesParaDeshacer();
        popDecision().deshacer();
    }

    public void aceptarSugerencia(Atuendo atuendo){
        this.decisiones.add(new Aceptar(atuendo));
        this.atuendosAceptados.add(atuendo);        //TODO: podria obtenerse a partir de filtrar las decisiones del usuario
    }

    public void calificarSugerencia(Atuendo atuendo, int calificacion){
        this.decisiones.add(new Calificar(atuendo,calificacion));
    }

    public void rechazarSugerencia(Atuendo atuendo){
        decisiones.add(new Rechazar(atuendo));
        atuendosRechazados.add(atuendo);
    }

    public void agregarMedioNotificacion(MedioDeNotificacion medio){
        if(!mediosDeNotificacion.contains(medio))
            mediosDeNotificacion.add(medio);
    }





    /** Arena */

    private LocalDateTime filtroEventoInicial = LocalDateTime.now();
    private LocalDateTime filtroEventoFinal = LocalDateTime.now();
    private List<Evento> eventosFiltrados = new ArrayList<>();

    public void filtrarEventosEntreRangoDeFechas(){
        eventosFiltrados = eventos.stream().filter(evento -> evento.estaEntre(filtroEventoInicial, filtroEventoFinal)).collect(Collectors.toList());
    }





    /** Getters y setters */

    public void setFiltroEventoInicial(LocalDateTime filtroEventoInicial) {
        this.filtroEventoInicial = filtroEventoInicial;
    }

    public void setFiltroEventoFinal(LocalDateTime filtroEventoFinal) {
        this.filtroEventoFinal = filtroEventoFinal;
    }

    public LocalDateTime getFiltroEventoInicial() {
        return filtroEventoInicial;
    }

    public LocalDateTime getFiltroEventoFinal() {
        return filtroEventoFinal;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public List<Evento> getEventosFiltrados() {
        return eventosFiltrados;
    }

    public void setEventosFiltrados(List<Evento> eventosFiltrados) {
        this.eventosFiltrados = eventosFiltrados;
    }

    public Guardarropas getGuardarropas(int posicion) {
    	return guardarropas.get(posicion);
	}



    /** Observer */

    @Override /* El usuario va a recibir el evento que este cerca y con sugerencias preparadas */
    public void recibirNotificacionEventoCerca(Evento evento) {
        mediosDeNotificacion.stream().forEach(medio -> medio.lanzarNotificacion());
    }

    @Override
    public void recibirNotificacionAlertaMeteorologica(Clima climaActual) {
        if(atuendosAceptados.stream().anyMatch(atuendo -> atuendo.revalidadAtuendo(climaActual)))
            mediosDeNotificacion.stream().forEach(medio -> medio.lanzarNotificacion());
    }





    /** Equals y hashCode */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(eventos, usuario.eventos) &&
                Objects.equals(decisiones, usuario.decisiones) &&
                Objects.equals(atuendosAceptados, usuario.atuendosAceptados) &&
                Objects.equals(atuendosRechazados, usuario.atuendosRechazados) &&
                Objects.equals(guardarropas, usuario.guardarropas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventos, decisiones, atuendosAceptados, atuendosRechazados, guardarropas);
    }
}
