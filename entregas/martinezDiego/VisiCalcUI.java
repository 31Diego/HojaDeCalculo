package v04;

import java.util.Scanner;
import librerias.Consola;

public class VisiCalcUI {
    private Viewport viewport;
    private Scanner scanner;

    public VisiCalcUI(HojaDeCalculo hoja) {
        this.viewport = new Viewport(hoja, 15, 10);
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        boolean estaOperativo = true;

        while (estaOperativo) {
            mostrarHoja();
            char comando = scanner.next().toUpperCase().charAt(0);
            estaOperativo = procesarComando(comando);
        }

        System.out.println("Saliendo del programa.");
        scanner.close();
    }

    private void mostrarHoja() {
        Consola.limpiarPantalla();
        mostrarOpciones();
        System.out.print("      ");
        for (int j = 0; j < viewport.getColumnasViewport(); j++) {
            char letraColumna = (char) ('A' + viewport.getColumnaInicio() + j);
            System.out.printf("%-8s", letraColumna);
        }
        System.out.println();

        for (int i = 0; i < viewport.getFilasViewport(); i++) {
            System.out.printf("%-5d|", viewport.getFilaInicio() + i + 1);

            for (int j = 0; j < viewport.getColumnasViewport(); j++) {
                String celda = viewport.getCelda(i, j).getContenido();
                celda = celda.length() > 5 ? celda.substring(0, 5) : String.format("%-5s", celda);

                if (i == viewport.getFilaCursorGlobal() - viewport.getFilaInicio()
                        && j == viewport.getColumnaCursorGlobal() - viewport.getColumnaInicio()) {
                    System.out.print("[" + celda + "]");
                } else {
                    System.out.print(" " + celda + " ");
                }
                System.out.print("|");
            }
            System.out.println();
        }
        Consola.posicionarse(2, 10);
    }

    private void mostrarOpciones() {
        int filaActual = viewport.getFilaCursorGlobal();
        int columnaActual = viewport.getColumnaCursorGlobal();
        char letraColumna = (char) ('A' + columnaActual);

        System.out.print("[" + letraColumna + (filaActual + 1) + "] ");
        System.out.println("OPCIONES: desplazarse: wasd | editar: e | ordenar: o | salir: q");
        System.out.println("COMANDO >");
    }

    private boolean procesarComando(char comando) {
        switch (comando) {
            case 'W':
                viewport.moverCursor(-1, 0);
                break;
            case 'A':
                viewport.moverCursor(0, -1);
                break;
            case 'S':
                viewport.moverCursor(1, 0);
                break;
            case 'D':
                viewport.moverCursor(0, 1);
                break;
            case 'E':
                editarCeldaActual();
                break;
            case 'O':
                ordenarColumna();
                break;
            case 'Q':
                return false;
            default:
                System.out.println("Comando inválido. Intente nuevamente.");
        }
        return true;
    }

    private void editarCeldaActual() {
        Celda celdaActual = viewport.getCeldaCursor();
        Consola.posicionarse(2, 1);
        System.out.print("Ingrese el texto:");
        String texto = scanner.next();
        celdaActual.setContenido(texto);
    }

    private void ordenarColumna() {
        int columnaActual = viewport.getColumnaCursorGlobal();
        int filas = viewport.getHoja().getNumeroDeFilas();

        String[] valores = new String[filas];
        for (int i = 0; i < filas; i++) {
            valores[i] = viewport.getHoja().getCelda(i, columnaActual).getContenido();
        }

        bubbleSort(valores);

        for (int i = 0; i < filas; i++) {
            viewport.getHoja().getCelda(i, columnaActual).setContenido(valores[i]);
        }
    }

    private void bubbleSort(String[] arr) {
        int n = arr.length;
        boolean intercambio;
        do {
            intercambio = false;
            for (int i = 0; i < n - 1; i++) {
                if (arr[i].compareTo(arr[i + 1]) > 0) {
                    String temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    intercambio = true;
                }
            }
        } while (intercambio);
    }
}

