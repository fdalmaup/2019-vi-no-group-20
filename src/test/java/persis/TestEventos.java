package persis;
import domain.evento.FrecuenciaEvento;
import domain.guardarropas.Guardarropas;
import domain.guardarropas.GuardarropasLimitado;
import domain.guardarropas.GuardarropasPremium;
import domain.prenda.*;
import domain.usuario.TipoDeUsuario;
import domain.usuario.Usuario;
import org.eclipse.core.runtime.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class TestEventos extends AbstractPersistenceTest implements WithGlobalEntityManager {
    private Usuario facundo;

    private GuardarropasPremium guardarropasCasual;
    private GuardarropasLimitado guardarropasLimitado;
    private GuardarropasPremium guardarropasCopado;
    private Prenda zapatos;
    private Prenda remera;
    private Prenda camisa;
    private Prenda pantalon;
    private Prenda anteojos;

    LocalDateTime fechaCumpleWilly;
    @Before
    public void init(){
        Color rojo = new Color(255, 0, 0);
        Color verde = new Color(0, 255, 0);
        Color azul = new Color(0, 0, 255);
        Color blanco = new Color(255, 255, 255);

        zapatos = armarUnaPrenda("zapatos",TipoDePrenda.ZAPATO, Material.CUERO, rojo, azul, Trama.GASTADO);
        remera = armarUnaPrenda("remera",TipoDePrenda.REMERA, Material.ALGODON, azul, rojo, Trama.CUADROS);
        pantalon = armarUnaPrenda("pantalon",TipoDePrenda.PANTALON, Material.JEAN, verde, rojo, Trama.RAYADA);
        anteojos= armarUnaPrenda("anteojos",TipoDePrenda.ANTEOJOS, Material.PLASTICO, verde, rojo, Trama.LISA);
        camisa = armarUnaPrenda("camisa",TipoDePrenda.CAMISA, Material.ALGODON, blanco, rojo, Trama.LISA);


        fechaCumpleWilly = LocalDateTime.of(2020,06,20,20,30);

    }

    public Prenda armarUnaPrenda(String nombre,TipoDePrenda tipoDePrenda, Material material, Color colorPrimario, Color colorSecundario, Trama trama){
        BorradorPrenda borradorPrenda = new BorradorPrenda();
        borradorPrenda.definirNombre(nombre);
        borradorPrenda.definirTipo(tipoDePrenda);
        borradorPrenda.definirMaterial(material);
        borradorPrenda.definirColorPrimario(colorPrimario);
        borradorPrenda.definirColorSecundario(colorSecundario);
        borradorPrenda.definirTrama(trama);
        return borradorPrenda.crearPrenda();
    }

    /*
    @Test
    public void sePersisteUnEvento(){
        facundo = new Usuario("Facundo Salerno",new ArrayList<Guardarropas>(Arrays.asList(new GuardarropasPremium("guardarropas casual", Arrays.asList(remera, camisa), Arrays.asList(pantalon), Arrays.asList(zapatos), Arrays.asList(anteojos)))), TipoDeUsuario.PREMIUM);
        guardarropasCasual = new GuardarropasPremium("guardarropas casual", new ArrayList<Prenda>(Arrays.asList(camisa, remera)), new ArrayList<Prenda>(Arrays.asList(pantalon)), new ArrayList<Prenda>(Arrays.asList(zapatos)), new ArrayList<Prenda>(Arrays.asList(anteojos)));

        facundo.agregarGuardarropas(guardarropasCasual);

        entityManager().persist(facundo);

        entityManager().find(Usuario.class,facundo.getId());  //Assert not null
    }

    @Test
    public void seRecuperaUnEventoAPartirDeSuID() throws Exception {

        facundo = new Usuario("Facundo Salerno",new ArrayList<Guardarropas>(Arrays.asList(new GuardarropasPremium("guardarropas casual", Arrays.asList(remera, camisa), Arrays.asList(pantalon), Arrays.asList(zapatos), Arrays.asList(anteojos)))), TipoDeUsuario.PREMIUM);
        guardarropasCopado = new GuardarropasPremium("guardarropas copado", new ArrayList<Prenda>(Arrays.asList(camisa, remera)), new ArrayList<Prenda>(Arrays.asList(pantalon)), new ArrayList<Prenda>(Arrays.asList(zapatos)), new ArrayList<Prenda>(Arrays.asList(anteojos)));

        facundo.agregarGuardarropas(guardarropasCopado);

        entityManager().persist(facundo);

        Usuario hermanoGemeloDeFacundoQueSeCopiaAFacundo = entityManager()
                .createQuery("from Usuario where id LIKE :IdUsuario", Usuario.class)
                .setParameter("IdUsuario", facundo.getId())
                .getResultList()
                .get(0);


        Assert.isTrue(hermanoGemeloDeFacundoQueSeCopiaAFacundo.getGuardarropas(1).getNombre().equals("guardarropas copado"));


    }

    @Test
    public void seRecuperanEventosAPartirDeFecha() throws Exception {

        facundo = new Usuario("Facundo Salerno",new ArrayList<Guardarropas>(Arrays.asList(new GuardarropasPremium("guardarropas casual", Arrays.asList(remera, camisa), Arrays.asList(pantalon), Arrays.asList(zapatos), Arrays.asList(anteojos)))), TipoDeUsuario.PREMIUM);
        facundo.crearEvento("Cumpleaños de juan", fechaCumpleWilly, FrecuenciaEvento.ANUAL,"Casa de Juan");

        entityManager().persist(facundo);

        Usuario hermanoGemeloDeFacundoQueSeCopiaAFacundo = entityManager()
                .createQuery("from Usuario where id LIKE :IdUsuario", Usuario.class)
                .setParameter("IdUsuario", facundo.getId())
                .getResultList()
                .get(0);

        Assert.isTrue(hermanoGemeloDeFacundoQueSeCopiaAFacundo.getEventos().get(0).getNombre().equals("Cumpleaños de juan"));
    }
*/
}