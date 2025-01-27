package com.ejemplo;

import java.io.*;
import java.net.*;

/**
 * Clase Servidor que espera conexiones de un cliente y maneja un flujo de mensajes
 * basado en un esquema definido.
 */
public class Servidor {

    /**
     * Método principal que inicia el servidor y espera conexiones en el puerto 12345.
     *
     * @param args Argumentos de línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(12345)) {
            System.out.println("Servidor iniciado. Esperando conexiones en el puerto 12345...");

            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());

            manejarCliente(cliente);

            System.out.println("Servidor cerrando conexión...");
            cliente.close();
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método que maneja la comunicación con el cliente.
     *
     * @param cliente Socket del cliente conectado.
     * @throws IOException Si ocurre un error de comunicación.
     */
    private static void manejarCliente(Socket cliente) throws IOException {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);

        String mensajeCliente;
        boolean terminar = false;
        int paso = 0;

        while (!terminar && (mensajeCliente = entrada.readLine()) != null) {
            switch (paso) {
                case 0:
                    if ("¿Quién es?".equals(mensajeCliente)) {
                        salida.println("Soy yo");
                        paso++;
                    } else {
                        salida.println("Error");
                    }
                    break;
                case 1:
                    if ("¿Qué vienes a buscar?".equals(mensajeCliente)) {
                        salida.println("A ti");
                        paso++;
                    } else {
                        salida.println("Error");
                    }
                    break;
                case 2:
                    if ("Ya es tarde".equals(mensajeCliente)) {
                        salida.println("¿Por qué?");
                        paso++;
                    } else {
                        salida.println("Error");
                    }
                    break;
                case 3:
                    if ("Porque ahora soy yo la que quiere estar sin ti".equals(mensajeCliente)) {
                        salida.println("Por eso vete, olvida mi nombre, mi cara, mi casa y pega la vuelta");
                        terminar = true;
                    } else {
                        salida.println("Error");
                    }
                    break;
                default:
                    salida.println("Error");
                    break;
            }
        }
    }
}
