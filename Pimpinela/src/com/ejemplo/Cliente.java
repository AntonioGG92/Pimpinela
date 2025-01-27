package com.ejemplo;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Clase Cliente que se conecta a un servidor y sigue un flujo de mensajes
 * predefinido para la comunicación.
 */
public class Cliente {

    /**
     * Método principal que conecta al cliente con el servidor en el puerto 12345.
     *
     * @param args Argumentos de línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        try (Socket servidor = new Socket("localhost", 12345)) {
            System.out.println("Conectado al servidor en localhost:12345.");

            BufferedReader entrada = new BufferedReader(new InputStreamReader(servidor.getInputStream()));
            PrintWriter salida = new PrintWriter(servidor.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            String mensajeServidor;

            while (true) {
                System.out.print("Escribe un mensaje para el servidor: ");
                String mensajeUsuario = scanner.nextLine();
                salida.println(mensajeUsuario);

                mensajeServidor = entrada.readLine();
                System.out.println("Servidor: " + mensajeServidor);

                if ("Por eso vete, olvida mi nombre, mi cara, mi casa y pega la vuelta".equals(mensajeServidor)) {
                    System.out.println("Finalizando conexión con el servidor...");
                    break;
                }

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
