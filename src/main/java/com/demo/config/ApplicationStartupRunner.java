package com.demo.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;

@Component
public class ApplicationStartupRunner implements ApplicationRunner {

    private final Environment environment;

    public ApplicationStartupRunner(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void run(ApplicationArguments args) {
        // Obtener el puerto de la aplicación
        String port = environment.getProperty("server.port", "8080");

        // Obtener la IP local de la máquina
        String localIp = getLocalIpAddress();

        // Imprimir la información en los logs
        System.out.println("\n");
        System.out.println("==========================================");
        System.out.println("✅ APLICACIÓN INICIADA CORRECTAMENTE");
        System.out.println("==========================================");
        System.out.println("🎬 CINE CINEMA APP - G1");
        System.out.println("==========================================");
        System.out.println("📱 PARA ESCANEAR QR DESDE DISPOSITIVO MÓVIL:");
        System.out.println("   URL: http://" + localIp + ":" + port);
        System.out.println("==========================================");
        System.out.println("🔗 ACCESO LOCAL: http://localhost:" + port);
        System.out.println("==========================================\n");
    }

    /**
     * Obtiene la IP local de la máquina
     */
    private String getLocalIpAddress() {
        try {
            // Intentar obtener la IP del host
            InetAddress localHost = InetAddress.getLocalHost();

            // Obtener la dirección IP del host
            String hostIp = localHost.getHostAddress();

            // Si es localhost o loopback, intentar obtener una IP de red real
            if (hostIp.equals("127.0.0.1") || hostIp.contains("::1")) {
                return getNetworkIpAddress();
            }

            return hostIp;
        } catch (Exception e) {
            return getNetworkIpAddress();
        }
    }

    /**
     * Obtiene una IP de red real (no localhost)
     */
    private String getNetworkIpAddress() {
        try {
            for (NetworkInterface ni : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                // Saltar interfaces inactivas
                if (!ni.isUp()) continue;

                for (InetAddress ia : Collections.list(ni.getInetAddresses())) {
                    // Obtener solo IPv4, no localhost ni link-local
                    if (!ia.isLoopbackAddress() && !ia.isLinkLocalAddress() && ia.getHostAddress().contains(".")) {
                        return ia.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            // Fallback
        }

        // Si todo falla, devolver localhost
        return "localhost";
    }
}


