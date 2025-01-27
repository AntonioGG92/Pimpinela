package com.ejemplo;

import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(12345)) {
            System.out.println("Servidor esperando conexión...");
            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado.");

            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);

            String mensajeCliente;
            boolean terminar = false;
            int paso = 0;

            while (!terminar && (mensajeCliente = entrada.readLine()) != null) {
                switch (paso) {
                    case 0:
                        if (mensajeCliente.equals("¿Quién es?")) {
                            salida.println("Soy yo");
                            paso++;
                        } else {
                            salida.println("Error");
                        }
                        break;

                    case 1:
                        if (mensajeCliente.equals("¿Qué vienes a buscar?")) {
                            salida.println("A ti");
                            paso++;
                        } else {
                            salida.println("Error");
                        }
                        break;

                    case 2:
                        if (mensajeCliente.equals("Ya es tarde")) {
                            salida.println("¿Por qué?");
                            paso++;
                        } else {
                            salida.println("Error");
                        }
                        break;

                    case 3:
                        if (mensajeCliente.equals("Porque ahora soy yo la que quiere estar sin ti")) {
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

            System.out.println("Servidor cerrando conexión...");
            cliente.close();
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
