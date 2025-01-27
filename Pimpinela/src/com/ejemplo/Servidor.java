package com.ejemplo;

import java.io.*;
import java.net.*;

/**
 * Clase Servidor que espera conexiones de un cliente y maneja un flujo de mensajes
 * basado en un esquema definido. Utiliza sockets para la comunicación.
 */
public class Servidor {

    /**
     * Método principal que inicia el servidor, escucha conexiones en el puerto 12345,
     * y gestiona el flujo de conversación con el cliente.
     *
     * @param args Argumentos de línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(12345)) {
            System.out.println("Servidor iniciado. Esperando conexiones en el puerto 12345...");

            // Espera a que un cliente se conecte
            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());

            // Maneja la comunicación con el cliente
            manejarCliente(cliente);

            System.out.println("Servidor cerrando conexión...");
            cliente.close();
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método encargado de manejar la comunicación con el cliente.
     * de mensajes predefinido, y responde con "Error" si el flujo es interrumpido.
     *
     * @param cliente Socket del cliente conectado.
     * @throws IOException Si ocurre un error de comunicación.
     */
    private static void manejarCliente(Socket cliente) throws IOException {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);

        String mensajeCliente;
        boolean terminar = false;
        int paso = 0; // Variable para controlar el flujo de la conversación

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
                        terminar = true; // Finaliza la conversación
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
