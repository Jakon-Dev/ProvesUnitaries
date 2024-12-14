package services;

import java.net.ConnectException;

/**
 * Interfaz para manejar señales Bluetooth no emparejadas.
 */
public interface UnbondedBTSignal {

    /**
     * Emite una señal Bluetooth para identificar estaciones.
     * @throws ConnectException Si ocurre un error al emitir la señal.
     */
    void BTbroadcast() throws ConnectException;
}
