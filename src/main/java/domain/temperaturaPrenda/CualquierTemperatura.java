package domain.temperaturaPrenda;

import clima.Clima;

public class    CualquierTemperatura extends TemperaturaPrenda{

    @Override /* Si no importa la temperatura siempre se va a adaptar al clima actual */
    public boolean seAdapta(Clima climaActual) {
        return true;
    }
}