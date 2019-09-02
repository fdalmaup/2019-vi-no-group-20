package domain.prenda;

import clima.Clima;
import domain.atuendo.Estado;
import domain.capaPrenda.NivelDeCapa;

import java.io.IOException;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Prenda {

    private TipoDePrenda tipoPrenda;
    private Material material;
    private Color colorPrimario;
    private Color colorSecundario;
    private Trama trama;
    private Imagen imagen;
    private Estado estado;

    /** Warning: construir con BorradorPrenda */
    public Prenda(TipoDePrenda tipo, Color colorPrimario, Color colorSecundario, Material material, Trama trama, Imagen imagen) {
        this.tipoPrenda = tipo;
        this.material = material;
        this.colorPrimario = colorPrimario;
        this.colorSecundario = colorSecundario;
        this.trama = trama;
        this.imagen=imagen;
    }





    /** Metodos */

    public void cambiarEstado(Estado estado){
        this.estado = estado;
    }

    public boolean esDeCategoria(Categoria categoria){
        return this.getCategoria()==categoria;
    }

    public boolean abrigaBien(Clima climaActual){
        return tipoPrenda.esAptoParaTemperatura(climaActual);
    }





    /** Getters y setters */

    public Categoria getCategoria() {
        return this.tipoPrenda.categoria();
    }
    public Estado getEstado(){
        return estado;
    }
    public TipoDePrenda getTipoPrenda(){
        return this.tipoPrenda;
    }
    public Material getMaterial(){
        return this.material;
    }
    public Color getColorPrimario() { return colorPrimario;}
    public Color getColorSecundario() { return colorSecundario;}
    public Trama getTrama() { return trama;}
    public NivelDeCapa getNivelDeCapa(){return tipoPrenda.getNivelDeCapa();}





    /** Equals y hashCode */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prenda prenda = (Prenda) o;
        return Objects.equals(getTipoPrenda(), prenda.getTipoPrenda()) &&
                getMaterial() == prenda.getMaterial() &&
                Objects.equals(getColorPrimario(), prenda.getColorPrimario()) &&
                Objects.equals(getColorSecundario(), prenda.getColorSecundario()) &&
                getTrama() == prenda.getTrama();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTipoPrenda(), getMaterial(), getColorPrimario(), getColorSecundario(), getTrama());
    }
}
