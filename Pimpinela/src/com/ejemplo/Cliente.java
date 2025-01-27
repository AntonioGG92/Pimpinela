package com.ejemplo;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try (Socket servidor = new Socket("localhost", 12345)) {
            System.out.println("Conectado al servidor.");

            BufferedReader entrada = new BufferedReader(new InputStreamReader(servidor.getInputStream()));
            PrintWriter salida = new PrintWriter(servidor.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            String mensajeServidor;
            String mensajeUsuario;

            while (true) {
                System.out.print("Escribe un mensaje para el servidor: ");
                mensajeUsuario = scanner.nextLine();
                salida.println(mensajeUsuario);

                mensajeServidor = entrada.readLine();
                System.out.println("Servidor responde: " + mensajeServidor);

                if (mensajeServidor.equals("Por eso vete, olvida mi nombre, mi cara, mi casa y pega la vuelta")) {
                    System.out.println("Desconexión solicitada. Cerrando cliente...");
                    break;
                }

                if (mensajeServidor.equals("Error")) {
                    System.out.println("Has cometido un error. Reinicia el cliente para intentarlo de nuevo.");
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Error al conectar con el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
