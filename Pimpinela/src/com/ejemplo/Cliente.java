package com.ejemplo;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Clase Cliente que se conecta a un servidor y sigue un flujo de mensajes
 * predefinido para la comunicación. Utiliza sockets para enviar y recibir mensajes.
 */
public class Cliente {

    /**
     * Método principal que conecta al cliente con el servidor en el puerto 12345,
     * y permite la interacción con el usuario a través de la consola.
     *
     * @param args Argumentos de línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        try (Socket servidor = new Socket("localhost", 12345)) {
            System.out.println("Conectado al servidor en localhost:12345.");

            // Inicializa las entradas y salidas para la comunicación
            BufferedReader entrada = new BufferedReader(new InputStreamReader(servidor.getInputStream()));
            PrintWriter salida = new PrintWriter(servidor.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            String mensajeServidor;

            while (true) {
                System.out.print("Escribe un mensaje para el servidor: ");
                String mensajeUsuario = scanner.nextLine(); // Lee el mensaje del usuario
                salida.println(mensajeUsuario); // Envía el mensaje al servidor

                mensajeServidor = entrada.readLine(); // Recibe la respuesta del servidor
                System.out.println("Servidor: " + mensajeServidor);

                // Finaliza la conexión si el flujo termina correctamente
                if ("Por eso vete, olvida mi nombre, mi cara, mi casa y pega la vuelta".equals(mensajeServidor)) {
                    System.out.println("Finalizando conexión con el servidor...");
                    break;
                }

                // Termina si el servidor responde con un error
                if ("Error".equals(mensajeServidor)) {
                    System.out.println("Error en el flujo de conversación. Reinicia el cliente.");
                    break;
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Error: No se pudo encontrar el servidor.");
        } catch (IOException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }
}
