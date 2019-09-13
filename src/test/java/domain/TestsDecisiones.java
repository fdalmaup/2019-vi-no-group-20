package domain;

import domain.atuendo.Atuendo;
import domain.atuendo.Estado;
import domain.capaPrenda.Capa;
import domain.capaPrenda.CapaSimple;
import domain.guardarropas.Guardarropas;
import domain.guardarropas.GuardarropasPremium;
import domain.prenda.*;
import domain.usuario.TipoDeUsuario;
import domain.usuario.Usuario;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;

public class TestsDecisiones {
    private Usuario batman;
    private Guardarropas guardarropasVeranoBatman;
    private Atuendo trajeBatman;
    private Prenda prendaBotaNegra;
    private Prenda prendaArmaduraNegra;
    private Prenda prendaPantalonNegro;
    private Prenda prendaAntifazDeMurcielago;
    private Capa botaNegra;
    private Capa armaduraNegra;
    private Capa pantalonNegro;
    private Capa antifazDeMurcielago;

    @Before
    public void init(){
        Color negro = new Color(0, 0, 0);

        prendaBotaNegra = armarUnaPrenda(TipoDePrenda.ZAPATO, Material.CUERO, negro, Trama.GASTADO);
        prendaArmaduraNegra = armarUnaPrenda(TipoDePrenda.REMERA, Material.ALGODON, negro, Trama.CUADROS);
        prendaPantalonNegro = armarUnaPrenda(TipoDePrenda.PANTALON, Material.JEAN, negro, Trama.RAYADA);
        prendaAntifazDeMurcielago = armarUnaPrenda(TipoDePrenda.ANTEOJOS, Material.PLASTICO, negro, Trama.LISA);

        botaNegra = new CapaSimple(prendaBotaNegra);
        armaduraNegra = new CapaSimple(prendaArmaduraNegra);
        pantalonNegro = new CapaSimple(prendaPantalonNegro);
        antifazDeMurcielago = new CapaSimple(prendaAntifazDeMurcielago);

        trajeBatman = new Atuendo(armaduraNegra, pantalonNegro, botaNegra, antifazDeMurcielago);

        guardarropasVeranoBatman = new GuardarropasPremium(Arrays.asList(prendaArmaduraNegra), Arrays.asList(prendaPantalonNegro), Arrays.asList(prendaBotaNegra), Arrays.asList(prendaAntifazDeMurcielago));

        batman = new Usuario("Facundo Salerno",Arrays.asList(guardarropasVeranoBatman), TipoDeUsuario.PREMIUM);
    }

    public Prenda armarUnaPrenda(TipoDePrenda tipoDePrenda, Material material, Color colorPrimario, Trama trama){
        BorradorPrenda borradorPrenda = new BorradorPrenda();
        borradorPrenda.definirTipo(tipoDePrenda);
        borradorPrenda.definirMaterial(material);
        borradorPrenda.definirColorPrimario(colorPrimario);
        borradorPrenda.definirTrama(trama);
        return borradorPrenda.crearPrenda();
    }

    @Test
    public void verificarQueElAtuendoEsAceptado(){
        batman.aceptarSugerencia(trajeBatman);
        Assert.assertEquals(trajeBatman.getEstado(), Estado.ACEPTADO);
    }

    @Test
    public void verificarQueLaDecisionSePuedeDeshaer(){
        batman.aceptarSugerencia(trajeBatman);
        batman.deshacerUltimaDecision();
        Assert.assertEquals(trajeBatman.getEstado(), Estado.NUEVO);
    }

}
