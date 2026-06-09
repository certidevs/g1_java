package com.demo.service;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
@AllArgsConstructor
public class QrService {
    public String generarQr(Long ticketId, HttpServletRequest request) throws WriterException, IOException {

        // 1. La URL que va dentro del QR - usando la IP real de la máquina
        String host = request.getServerName();
        int port = request.getServerPort();
        String protocol = request.getScheme();
        String url = protocol + "://" + host + ":" + port + "/tickets/qr-scan/" + ticketId;

        // 2. Generamos la matriz de puntos del QR
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(url, BarcodeFormat.QR_CODE, 300, 300);

        // 3. Convertimos esa matriz a una imagen PNG en memoria
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

        // 4. Convertimos la imagen a texto Base64 para mostrarla en HTML
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

}
