package services;

import data.VehicleID;
import exceptions.CorruptedImgException;

import java.awt.image.BufferedImage;

/**
 * Interfaz para decodificar códigos QR y obtener IDs de vehículos.
 */
public interface QRDecoder {

    /**
     * Obtiene el ID de un vehículo a partir de una imagen de código QR.
     * @param QRImg Imagen del código QR.
     * @return El ID del vehículo.
     * @throws exceptions.CorruptedImgException Si la imagen del QR está dañada o no es válida.
     */
    VehicleID getVehicleID(BufferedImage QRImg) throws exceptions.CorruptedImgException;
}
