package domain;

import domain.atuendo.Atuendo;
import domain.guardarropas.Guardarropas;
import domain.guardarropas.GuardarropasPremium;
import domain.prenda.*;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

import java.util.Arrays;

public class TestsPrendasSuperpuestas {
    private Atuendo atuendo;

    private Prenda zapatosFormales;
    private Prenda zapatosSalida;   //TODO: Para tests guardarropas

    private Prenda pantalonParaSalida;
    private Prenda pantalonFormal;   //TODO: Para tests guardarropas

    private Prenda anteojos;

    private Prenda camperaParaSalida;
    private Prenda camperaMichelin;
    private Prenda busoInformal;
    private Prenda busoFormal;
    private Prenda sweaterFormal;
    private Prenda remeraCanchera;
    private Prenda remeraDeDia;
    private Prenda camisaFormalBlanca;
    private Prenda camisaFormalAzul;
    private Prenda camisaSalida;

    private Guardarropas guardarropasInvierno;

    @Before
    public void init() {
        //Instanciaciones previas a los TEST
        Color rojo = new Color(255, 0, 0);
        Color verde = new Color(0, 255, 0);
        Color azul = new Color(0, 0, 255);
        Color blanco = new Color(255, 255, 255);
        Color negro = new Color(0, 0, 0);

        zapatosSalida = armarUnaPrenda(TipoDePrenda.ZAPATO, Material.GAMUZA, azul, null, Trama.GASTADO);
        //zapatosFormales = armarUnaPrenda(TipoDePrenda.ZAPATO, Material.CUERO, negro, null, Trama.LISA); //TODO: Para tests guardarropas

        pantalonParaSalida = armarUnaPrenda(TipoDePrenda.PANTALON, Material.JEAN, blanco, null, Trama.GASTADO);
        //pantalonFormal = armarUnaPrenda(TipoDePrenda.PANTALON, Material.POLIESTER, negro, null, Trama.LISA);  //TODO: Para tests guardarropas

        anteojos = armarUnaPrenda(TipoDePrenda.ANTEOJOS, Material.PLASTICO, verde, rojo, Trama.LISA);


        busoFormal = armarUnaPrenda(TipoDePrenda.BUSO, Material.ALGODON, azul, null, Trama.LISA);
        busoInformal = armarUnaPrenda(TipoDePrenda.BUSO, Material.ALGODON, azul, verde, Trama.RAYADA);
        sweaterFormal = armarUnaPrenda(TipoDePrenda.SWEATER,Material.LINO, azul, null, Trama.LISA);

        camperaParaSalida = armarUnaPrenda(TipoDePrenda.CAMPERA, Material.JEAN, verde, null, Trama.GASTADO);
        camperaMichelin = armarUnaPrenda(TipoDePrenda.CAMPERA, Material.PLUMA, azul, null, Trama.LISA);

        camisaSalida = armarUnaPrenda(TipoDePrenda.CAMISA, Material.JEAN, azul, blanco, Trama.ESCOCESA);
        camisaFormalAzul = armarUnaPrenda(TipoDePrenda.CAMISA, Material.SEDA, azul, null, Trama.LISA);
        camisaFormalBlanca = armarUnaPrenda(TipoDePrenda.CAMISA, Material.LINO, blanco, null, Trama.LISA);

        remeraCanchera = armarUnaPrenda(TipoDePrenda.REMERA, Material.ALGODON, azul, rojo, Trama.RAYADA);
        remeraDeDia = armarUnaPrenda(TipoDePrenda.REMERA, Material.ALGODON, azul, null, Trama.LISA);

        guardarropasInvierno = new GuardarropasPremium(Arrays.asList(sweaterFormal,remeraDeDia,remeraCanchera, camisaSalida, busoInformal, camperaParaSalida, camisaFormalAzul, camisaFormalBlanca, camperaMichelin, busoFormal), Arrays.asList(pantalonParaSalida), Arrays.asList(zapatosFormales), Arrays.asList(anteojos));
    }

    public Prenda armarUnaPrenda(TipoDePrenda tipoDePrenda, Material material, Color colorPrimario, Color colorSecundario, Trama trama) {
        BorradorPrenda borradorPrenda = new BorradorPrenda();
        borradorPrenda.definirTipo(tipoDePrenda);
        borradorPrenda.definirMaterial(material);
        borradorPrenda.definirColorPrimario(colorPrimario);
        if (colorSecundario != null) {

            borradorPrenda.definirColorSecundario(colorSecundario);
        }
        borradorPrenda.definirTrama(trama);
        return borradorPrenda.crearPrenda();
    }

    /*@Test(expected = AtuendoInvalidoException.class)
    public void verificarQueNoSeCreaAtuendoConPrendasSuperpuestasDeDistintaCategoria(){
        new Atuendo(Arrays.asList(camisa, pantalon), pantalon, zapatos, anteojos);
    }*/

    //TODO: Test para verificar que siempre se crean capas con prendas de igual categoria

    //TODO:Test para verificar que se dispara una excepcion si no hay capas creadas debido a que no se satisface la temperatura

    @Test
    public void generarAtuendoTiraExcepcionSiNohayCapasQueSatisfaganLaTemperatura() {

    }

    //TODO: Test para verificar que las capas generadas esten ordenadas

    //TODO: Test para verificar que todas las capas son de distinto nivel

}
