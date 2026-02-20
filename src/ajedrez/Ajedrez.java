/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ajedrez;

import java.util.Scanner;
import java.util.Arrays;
import javax.swing.SwingUtilities;

public class Ajedrez {

    public static final int MAX_ROW = 8;
    public static final int MAX_COLUMN = 8;

    private static final Scanner sc = new Scanner(System.in);

    // Inicializamos el tablero  
    
    public static void boardInitialization(String[][] board) {

        board[0][0] = "[T]";
        board[0][1] = "[C]";
        board[0][2] = "[A]";
        board[0][3] = "[K]";
        board[0][4] = "[Q]";
        board[0][5] = "[A]";
        board[0][6] = "[C]";
        board[0][7] = "[T]";

        for (int i = 0; i < board.length; i++) {
            board[1][i] = "[P]";
            for (int j = 2; j < 6; j++) {
                board[j][i] = " . ";
            }
            board[6][i] = "(P)";
        }

        board[7][0] = "(T)";
        board[7][1] = "(C)";
        board[7][2] = "(A)";
        board[7][3] = "(Q)";
        board[7][4] = "(K)";
        board[7][5] = "(A)";
        board[7][6] = "(C)";
        board[7][7] = "(T)";
    }

   
    //metodo que marca en la matriz auxiliar las opciones disponibles para la pieza seleccionada, devuelve si hay al menos 1 posicion
    public static boolean optionsPeon(String[][] board, int row, int column, int[][] validOptions) {

        boolean existOption = false; // empezamos con la condicion de que no exiten opciones validas 

        if (row == 6 && board[row - 2][column].equals(" . ")) { // si la fila siguiente esta vacia queda marcada como valida 
            validOptions[row - 2][column] = 1;
            existOption = true; //si ponemos un uno en la matriz ya existen opciones validas 
        }

        if (board[row - 1][column].equals(" . ")) { // si la fila siguiente esta vacia queda marcada como valida 
            validOptions[row - 1][column] = 1;
            existOption = true; //si ponemos un uno en la matriz ya existen opciones validas 
        }

        if (column != 0) {  //control de errores - si la columna es 0, esta en el limite no habra nada a la izquierda 
            if (board[row - 1][column - 1].equals("[P]") || board[row - 1][column - 1].equals("[T]") || board[row - 1][column - 1].equals("[C]") || board[row - 1][column - 1].equals("[A]") || board[row - 1][column - 1].equals("[Q]") || board[row - 1][column - 1].equals("[K]")) {
                validOptions[row - 1][column - 1] = 1; // si la ficha en diagonal es enemiga marcamos como opcion valida 
                existOption = true;
            }
        }
        if (column != 7) { //control de errores - si la columna es 7, esta en el limite no habra nada a la derecha 
            if (board[row - 1][column + 1].equals("[P]") || board[row - 1][column + 1].equals("[T]") || board[row - 1][column + 1].equals("[C]") || board[row - 1][column + 1].equals("[A]") || board[row - 1][column + 1].equals("[Q]") || board[row - 1][column + 1].equals("[K]")) {
                validOptions[row - 1][column + 1] = 1; // si la ficha en diagonal es enemiga marcamos como opcion valida 
                existOption = true;
            }
        }
        return existOption;
    }

    //metodo que marca en la matriz auxiliar las opciones disponibles para la pieza seleccionada, devuelve si hay al menos 1 posicion
    public static boolean optionsBlackPeon(String[][] board, int row, int column, int[][] validOptions) {

        boolean existOption = false; // empezamos con la condicion de que no exiten opciones validas 

        if (row == 1 && board[row + 2][column].equals(" . ")) { // si la fila siguiente esta vacia queda marcada como valida 
            validOptions[row + 2][column] = 1;
            existOption = true; //si ponemos un uno en la matriz ya existen opciones validas 
        }

        if (board[row + 1][column].equals(" . ")) { // si la fila siguiente esta vacia queda marcada como valida 
            validOptions[row + 1][column] = 1;
            existOption = true; //si ponemos un uno en la matriz ya existen opciones validas 
        }
        if (column != 0) {  //control de errores - si la columna es 0, esta en el limite no habra nada a la izquierda 
            if (board[row + 1][column - 1].equals("(P)") || board[row + 1][column - 1].equals("(T)") || board[row + 1][column - 1].equals("(C)") || board[row + 1][column - 1].equals("(A)") || board[row + 1][column - 1].equals("(Q)") || board[row + 1][column - 1].equals("(K)")) {
                validOptions[row + 1][column - 1] = 1; // si la ficha en diagonal es enemiga marcamos como opcion valida 
                existOption = true;
            }
        }
        if (column != 7) { //control de errores - si la columna es 7, esta en el limite no habra nada a la derecha 
            if (board[row + 1][column + 1].equals("(P)") || board[row + 1][column + 1].equals("(T)") || board[row + 1][column + 1].equals("(C)") || board[row + 1][column + 1].equals("(A)") || board[row + 1][column + 1].equals("(Q)") || board[row + 1][column + 1].equals("(K)")) {
                validOptions[row + 1][column + 1] = 1; // si la ficha en diagonal es enemiga marcamos como opcion valida 
                existOption = true;
            }
        }
        return existOption;
    }
    //metodo que marca en la matriz auxiliar las opciones disponibles para la pieza seleccionada, devuelve si hay al menos 1 posicion

    public static boolean optionsRook(String[][] board, int row, int column, int[][] validOptions) {

        boolean existOptions = false; //empezamos con la condicion de que no existen opciones validas
        //Comprovamos hacia arriba , hasta que encuentre una ficha aliada o despues de encontrar una pieza enemiga 
        for (int i = (row - 1); i >= 0; i--) {
            if (board[i][column].equals("[P]") || board[i][column].equals("[T]") || board[i][column].equals("[C]") || board[i][column].equals("[A]") || board[i][column].equals("[Q]") || board[i][column].equals("[K]")) {
                validOptions[i][column] = 1;
                existOptions = true; //si marca un 1, indicamos que existen opciones validas 
                break;
            }
            if (board[i][column] == "(P)" || board[i][column] == "(T)" || board[i][column] == "(C)" || board[i][column] == "(A)" || board[i][column] == "(Q)" || board[i][column] == "(K)") {
                break;
            }
            validOptions[i][column] = 1;
            existOptions = true; //si marca un 1, indicamos que existen opciones validas 
        }

        //Comprovamos hacia abajo, hasta que encuentre una ficha aliada o despues de encontrar una pieza enemiga 
        for (int i = (row + 1); i < board.length; i++) {
            if (board[i][column].equals("[P]") || board[i][column].equals("[T]") || board[i][column].equals("[C]") || board[i][column].equals("[A]") || board[i][column].equals("[Q]") || board[i][column].equals("[K]")) {
                validOptions[i][column] = 1;
                existOptions = true; //si marca un 1, indicamos que existen opciones validas 
                break;
            }
            if (board[i][column] == "(P)" || board[i][column] == "(T)" || board[i][column] == "(C)" || board[i][column] == "(A)" || board[i][column] == "(Q)" || board[i][column] == "(K)") {
                break;
            }
            validOptions[i][column] = 1;
            existOptions = true; //si marca un 1, indicamos que existen opciones validas 
        }

        //Comprovamos por la izquierda, hasta que encuentre una ficha aliada o despues de encontrar una pieza enemiga 
        for (int i = (column - 1); i >= 0; i--) {
            if (board[row][i].equals("[P]") || board[row][i].equals("[T]") || board[row][i].equals("[C]") || board[row][i].equals("[A]") || board[row][i].equals("[Q]") || board[row][i].equals("[K]")) {
                validOptions[row][i] = 1;
                existOptions = true;
                break;
            }
            if (board[row][i].equals("(P)") || board[row][i].equals("(T)") || board[row][i].equals("(C)") || board[row][i].equals("(A)") || board[row][i].equals("(Q)") || board[row][i].equals("(K)")) {
                break;
            }
            validOptions[row][i] = 1;
            existOptions = true;
        }

        //Comprovamos hacia la derecha, hasta que encuentre una ficha aliada o despues de encontrar una pieza enemiga 
        for (int i = (column + 1); i < validOptions.length; i++) {
            if (board[row][i].equals("[P]") || board[row][i].equals("[T]") || board[row][i].equals("[C]") || board[row][i].equals("[A]") || board[row][i].equals("[Q]") || board[row][i].equals("[K]")) {
                validOptions[row][i] = 1;
                existOptions = true;
                break;
            }
            if (board[row][i].equals("(P)") || board[row][i].equals("(T)") || board[row][i].equals("(C)") || board[row][i].equals("(A)") || board[row][i].equals("(Q)") || board[row][i].equals("(K)")) {
                break;
            }
            validOptions[row][i] = 1;
            existOptions = true;
        }

        return existOptions;
    }

    public static boolean optionsBlackRook(String[][] board, int row, int column, int[][] validOptions) {

        boolean existOptions = false; //empezamos con la condicion de que no existen opciones validas
        //Comprovamos hacia arriba , hasta que encuentre una ficha aliada o despues de encontrar una pieza enemiga 
        for (int i = (row - 1); i >= 0; i--) {
            if (board[i][column].equals("(P)") || board[i][column].equals("(T)") || board[i][column].equals("(C)") || board[i][column].equals("(A)") || board[i][column].equals("(Q)") || board[i][column].equals("(K)")) {
                validOptions[i][column] = 1;
                existOptions = true; //si marca un 1, indicamos que existen opciones validas 
                break;
            }
            if (board[i][column].equals("[P]") || board[i][column].equals("[T]") || board[i][column].equals("[C]") || board[i][column].equals("[A]") || board[i][column].equals("[Q]") || board[i][column].equals("[K]")) {
                break;
            }
            validOptions[i][column] = 1;
            existOptions = true; //si marca un 1, indicamos que existen opciones validas 
        }

        //Comprovamos hacia abajo, hasta que encuentre una ficha aliada o despues de encontrar una pieza enemiga 
        for (int i = (row + 1); i < board.length; i++) {
            if (board[i][column].equals("(P)") || board[i][column].equals("(T)") || board[i][column].equals("(C)") || board[i][column].equals("(A)") || board[i][column].equals("(Q)") || board[i][column].equals("(K)")) {
                validOptions[i][column] = 1;
                existOptions = true; //si marca un 1, indicamos que existen opciones validas 
                break;
            }
            if (board[i][column].equals("[P]") || board[i][column].equals("[T]") || board[i][column].equals("[C]") || board[i][column].equals("[A]") || board[i][column].equals("[Q]") || board[i][column].equals("[K]")) {
                break;
            }
            validOptions[i][column] = 1;
            existOptions = true; //si marca un 1, indicamos que existen opciones validas 
        }

        //Comprovamos por la izquierda, hasta que encuentre una ficha aliada o despues de encontrar una pieza enemiga 
        for (int i = (column - 1); i >= 0; i--) {
            if (board[row][i].equals("(P)") || board[row][i].equals("(T)") || board[row][i].equals("(C)") || board[row][i].equals("(A)") || board[row][i].equals("(Q)") || board[row][i].equals("(K)")) {
                validOptions[row][i] = 1;
                existOptions = true;
                break;
            }
            if (board[row][i].equals("[P]") || board[row][i].equals("[T]") || board[row][i].equals("[C]") || board[row][i].equals("[A]") || board[row][i].equals("[Q]") || board[row][i].equals("[K]")) {
                break;
            }
            validOptions[row][i] = 1;
            existOptions = true;
        }

        //Comprovamos hacia la derecha, hasta que encuentre una ficha aliada o despues de encontrar una pieza enemiga 
        for (int i = (column + 1); i < validOptions.length; i++) {
            if (board[row][i].equals("(P)") || board[row][i].equals("(T)") || board[row][i].equals("(C)") || board[row][i].equals("(A)") || board[row][i].equals("(Q)") || board[row][i].equals("(K)")) {
                validOptions[row][i] = 1;
                existOptions = true;
                break;
            }
            if (board[row][i].equals("[P]") || board[row][i].equals("[T]") || board[row][i].equals("[C]") || board[row][i].equals("[A]") || board[row][i].equals("[Q]") || board[row][i].equals("[K]")) {
                break;
            }
            validOptions[row][i] = 1;
            existOptions = true;
        }

        return existOptions;
    }

    public static boolean optionsAlfil(String[][] board, int row, int column, int[][] validOptions) {
        boolean existOptions = false; //empezamos con la condicion de que no existen opciones validas

        int j = 1;
        for (int i = (row - 1); i >= 0; i--) {
            if (column + j >= MAX_COLUMN) {
                break;
            }
            if (board[i][column + j].equals("[P]") || board[i][column + j].equals("[T]") || board[i][column + j].equals("[C]") || board[i][column + j].equals("[A]") || board[i][column + j].equals("[Q]") || board[i][column + j].equals("[K]")) {
                validOptions[i][column + j] = 1;
                existOptions = true; //si marca un 1, indicamos que existen opciones validas 
                break;
            }
            if (board[i][column + j].equals("(P)") || board[i][column + j].equals("(T)") || board[i][column + j].equals("(C)") || board[i][column + j].equals("(A)") || board[i][column + j].equals("(Q)") || board[i][column + j].equals("(K)")) {
                break;
            }
            validOptions[i][column + j] = 1;
            existOptions = true; //si marca un 1, indicamos que existen opciones validas 
            j++;
        }

        j = 1;
        for (int i = (row - 1); i >= 0; i--) {
            if (column - j < 0) {
                break;
            }
            if (board[i][column - j].equals("[P]") || board[i][column - j].equals("[T]") || board[i][column - j].equals("[C]") || board[i][column - j].equals("[A]") || board[i][column - j].equals("[Q]") || board[i][column - j].equals("[K]")) {
                validOptions[i][column - j] = 1;
                existOptions = true; //si marca un 1, indicamos que existen opciones validas 
                break;
            }
            if (board[i][column - j].equals("(P)") || board[i][column - j].equals("(T)") || board[i][column - j].equals("(C)") || board[i][column - j].equals("(A)") || board[i][column - j].equals("(Q)") || board[i][column - j].equals("(K)")) {
                break;
            }
            validOptions[i][column - j] = 1;
            existOptions = true; //si marca un 1, indicamos que existen opciones validas 
            j++;
        }

        j = 1;
        for (int i = (row + 1); i < MAX_COLUMN; i++) {
            if (column - j < 0) {
                break;
            }
            if (board[i][column - j].equals("[P]") || board[i][column - j].equals("[T]") || board[i][column - j].equals("[C]") || board[i][column - j].equals("[A]") || board[i][column - j].equals("[Q]") || board[i][column - j].equals("[K]")) {
                validOptions[i][column - j] = 1;
                existOptions = true; //si marca un 1, indicamos que existen opciones validas 
                break;
            }
            if (board[i][column - j].equals("(P)") || board[i][column - j].equals("(T)") || board[i][column - j].equals("(C)") || board[i][column - j].equals("(A)") || board[i][column - j].equals("(Q)") || board[i][column - j].equals("(K)")) {
                break;
            }
            validOptions[i][column - j] = 1;
            existOptions = true; //si marca un 1, indicamos que existen opciones validas 
            j++;
        }

        j = 1;
        for (int i = (row + 1); i < MAX_COLUMN; i++) {
            if (column + j >= MAX_COLUMN) {
                break;
            }
            if (board[i][column + j].equals("[P]") || board[i][column + j].equals("[T]") || board[i][column + j].equals("[C]") || board[i][column + j].equals("[A]") || board[i][column + j].equals("[Q]") || board[i][column + j].equals("[K]")) {
                validOptions[i][column + j] = 1;
                existOptions = true; //si marca un 1, indicamos que existen opciones validas 
                break;
            }
            if (board[i][column + j].equals("(P)") || board[i][column + j].equals("(T)") || board[i][column + j].equals("(C)") || board[i][column + j].equals("(A)") || board[i][column + j].equals("(Q)") || board[i][column + j].equals("(K)")) {
                break;
            }
            validOptions[i][column + j] = 1;
            existOptions = true; //si marca un 1, indicamos que existen opciones validas 
            j++;
        }

        return existOptions;
    }

    public static boolean optionsBlackAlfil(String[][] board, int row, int column, int[][] validOptions) {
        boolean existOptions = false; //empezamos con la condicion de que no existen opciones validas

        int j = 1;
        for (int i = (row - 1); i >= 0; i--) {
            if (column + j >= MAX_COLUMN) {
                break;
            }
            if (board[i][column + j].equals("[P]") || board[i][column + j].equals("[T]") || board[i][column + j].equals("[C]") || board[i][column + j].equals("[A]") || board[i][column + j].equals("[Q]") || board[i][column + j].equals("[K]")) {
                break;
            }
            if (board[i][column + j].equals("(P)") || board[i][column + j].equals("(T)") || board[i][column + j].equals("(C)") || board[i][column + j].equals("(A)") || board[i][column + j].equals("(Q)") || board[i][column + j].equals("(K)")) {
                validOptions[i][column + j] = 1;
                existOptions = true; //si marca un 1, indicamos que existen opciones validas
                break;
            }
            validOptions[i][column + j] = 1;
            existOptions = true; //si marca un 1, indicamos que existen opciones validas 
            j++;
        }

        j = 1;
        for (int i = (row - 1); i >= 0; i--) {
            if (column - j < 0) {
                break;
            }
            if (board[i][column - j].equals("[P]") || board[i][column - j].equals("[T]") || board[i][column - j].equals("[C]") || board[i][column - j].equals("[A]") || board[i][column - j].equals("[Q]") || board[i][column - j].equals("[K]")) {
                break;
            }
            if (board[i][column - j].equals("(P)") || board[i][column - j].equals("(T)") || board[i][column - j].equals("(C)") || board[i][column - j].equals("(A)") || board[i][column - j].equals("(Q)") || board[i][column - j].equals("(K)")) {
                validOptions[i][column - j] = 1;
                existOptions = true; //si marca un 1, indicamos que existen opciones validas 
                break;
            }
            validOptions[i][column - j] = 1;
            existOptions = true; //si marca un 1, indicamos que existen opciones validas 
            j++;
        }

        j = 1;
        for (int i = (row + 1); i < MAX_COLUMN; i++) {
            if (column - j < 0) {
                break;
            }
            if (board[i][column - j].equals("[P]") || board[i][column - j].equals("[T]") || board[i][column - j].equals("[C]") || board[i][column - j].equals("[A]") || board[i][column - j].equals("[Q]") || board[i][column - j].equals("[K]")) {
                break;
            }
            if (board[i][column - j].equals("(P)") || board[i][column - j].equals("(T)") || board[i][column - j].equals("(C)") || board[i][column - j].equals("(A)") || board[i][column - j].equals("(Q)") || board[i][column - j].equals("(K)")) {
                validOptions[i][column - j] = 1;
                existOptions = true; //si marca un 1, indicamos que existen opciones validas 
                break;
            }
            validOptions[i][column - j] = 1;
            existOptions = true; //si marca un 1, indicamos que existen opciones validas 
            j++;
        }

        j = 1;
        for (int i = (row + 1); i < MAX_COLUMN; i++) {
            if (column + j >= MAX_COLUMN) {
                break;
            }
            if (board[i][column + j].equals("[P]") || board[i][column + j].equals("[T]") || board[i][column + j].equals("[C]") || board[i][column + j].equals("[A]") || board[i][column + j].equals("[Q]") || board[i][column + j].equals("[K]")) {
                break;
            }
            if (board[i][column + j].equals("(P)") || board[i][column + j].equals("(T)") || board[i][column + j].equals("(C)") || board[i][column + j].equals("(A)") || board[i][column + j].equals("(Q)") || board[i][column + j].equals("(K)")) {
                validOptions[i][column + j] = 1;
                existOptions = true; //si marca un 1, indicamos que existen opciones validas 
                break;
            }
            validOptions[i][column + j] = 1;
            existOptions = true; //si marca un 1, indicamos que existen opciones validas 
            j++;
        }

        return existOptions;
    }

    public static boolean optionsQueen(String[][] board, int row, int column, int[][] validOptions) {
        boolean existOptions = false; //empezamos con la condicion de que no existen opciones validas

        //MOVIMIENTOS DE LA TORRE
        //Comprovamos hacia arriba , hasta que encuentre una ficha aliada o despues de encontrar una pieza enemiga 
        for (int i = (row - 1); i >= 0; i--) {
            if (board[i][column].equals("[P]") || board[i][column].equals("[T]") || board[i][column].equals("[C]") || board[i][column].equals("[A]") || board[i][column].equals("[Q]") || board[i][column].equals("[K]")) {
                validOptions[i][column] = 1;
                existOptions = true; //si marca un 1, indicamos que existen opciones validas 
                break;
            }
            if (board[i][column] == "(P)" || board[i][column] == "(T)" || board[i][column] == "(C)" || board[i][column] == "(A)" || board[i][column] == "(Q)" || board[i][column] == "(K)") {
                break;
            }
            validOptions[i][column] = 1;
            existOptions = true; //si marca un 1, indicamos que existen opciones validas 
        }

        //Comprovamos hacia abajo, hasta que encuentre una ficha aliada o despues de encontrar una pieza enemiga 
        for (int i = (row + 1); i < board.length; i++) {
            if (board[i][column].equals("[P]") || board[i][column].equals("[T]") || board[i][column].equals("[C]") || board[i][column].equals("[A]") || board[i][column].equals("[Q]") || board[i][column].equals("[K]")) {
                validOptions[i][column] = 1;
                existOptions = true; //si marca un 1, indicamos que existen opciones validas 
                break;
            }
            if (board[i][column] == "(P)" || board[i][column] == "(T)" || board[i][column] == "(C)" || board[i][column] == "(A)" || board[i][column] == "(Q)" || board[i][column] == "(K)") {
                break;
            }
            validOptions[i][column] = 1;
            existOptions = true; //si marca un 1, indicamos que existen opciones validas 
        }

        //Comprovamos por la izquierda, hasta que encuentre una ficha aliada o despues de encontrar una pieza enemiga 
        for (int i = (column - 1); i >= 0; i--) {
            if (board[row][i].equals("[P]") || board[row][i].equals("[T]") || board[row][i].equals("[C]") || board[row][i].equals("[A]") || board[row][i].equals("[Q]") || board[row][i].equals("[K]")) {
                validOptions[row][i] = 1;
                existOptions = true;
                break;
            }
            if (board[row][i].equals("(P)") || board[row][i].equals("(T)") || board[row][i].equals("(C)") || board[row][i].equals("(A)") || board[row][i].equals("(Q)") || board[row][i].equals("(K)")) {
                break;
            }
            validOptions[row][i] = 1;
            existOptions = true;
        }

        //Comprovamos hacia la derecha, hasta que encuentre una ficha aliada o despues de encontrar una pieza enemiga 
        for (int i = (column + 1); i < validOptions.length; i++) {
            if (board[row][i].equals("[P]") || board[row][i].equals("[T]") || board[row][i].equals("[C]") || board[row][i].equals("[A]") || board[row][i].equals("[Q]") || board[row][i].equals("[K]")) {
                validOptions[row][i] = 1;
                existOptions = true;
                break;
            }
            if (board[row][i].equals("(P)") || board[row][i].equals("(T)") || board[row][i].equals("(C)") || board[row][i].equals("(A)") || board[row][i].equals("(Q)") || board[row][i].equals("(K)")) {
                break;
            }
            validOptions[row][i] = 1;
            existOptions = true;
        }

        //MOVIMIENTOS DEL ALFIL 
        int j = 1;
        for (int i = (row - 1); i >= 0; i--) {
            if (column + j >= MAX_COLUMN) {
                break;
            }
            if (board[i][column + j].equals("[P]") || board[i][column + j].equals("[T]") || board[i][column + j].equals("[C]") || board[i][column + j].equals("[A]") || board[i][column + j].equals("[Q]") || board[i][column + j].equals("[K]")) {
                validOptions[i][column + j] = 1;
                existOptions = true; //si marca un 1, indicamos que existen opciones validas 
                break;
            }
            if (board[i][column + j].equals("(P)") || board[i][column + j].equals("(T)") || board[i][column + j].equals("(C)") || board[i][column + j].equals("(A)") || board[i][column + j].equals("(Q)") || board[i][column + j].equals("(K)")) {
                break;
            }
            validOptions[i][column + j] = 1;
            existOptions = true; //si marca un 1, indicamos que existen opciones validas 
            j++;
        }

        j = 1;
        for (int i = (row - 1); i >= 0; i--) {
            if (column - j < 0) {
                break;
            }
            if (board[i][column - j].equals("[P]") || board[i][column - j].equals("[T]") || board[i][column - j].equals("[C]") || board[i][column - j].equals("[A]") || board[i][column - j].equals("[Q]") || board[i][column - j].equals("[K]")) {
                validOptions[i][column - j] = 1;
                existOptions = true; //si marca un 1, indicamos que existen opciones validas 
                break;
            }
            if (board[i][column - j].equals("(P)") || board[i][column - j].equals("(T)") || board[i][column - j].equals("(C)") || board[i][column - j].equals("(A)") || board[i][column - j].equals("(Q)") || board[i][column - j].equals("(K)")) {
                break;
            }
            validOptions[i][column - j] = 1;
            existOptions = true; //si marca un 1, indicamos que existen opciones validas 
            j++;
        }

        j = 1;
        for (int i = (row + 1); i < MAX_COLUMN; i++) {
            if (column - j < 0) {
                break;
            }
            if (board[i][column - j].equals("[P]") || board[i][column - j].equals("[T]") || board[i][column - j].equals("[C]") || board[i][column - j].equals("[A]") || board[i][column - j].equals("[Q]") || board[i][column - j].equals("[K]")) {
                validOptions[i][column - j] = 1;
                existOptions = true; //si marca un 1, indicamos que existen opciones validas 
                break;
            }
            if (board[i][column - j].equals("(P)") || board[i][column - j].equals("(T)") || board[i][column - j].equals("(C)") || board[i][column - j].equals("(A)") || board[i][column - j].equals("(Q)") || board[i][column - j].equals("(K)")) {
                break;
            }
            validOptions[i][column - j] = 1;
            existOptions = true; //si marca un 1, indicamos que existen opciones validas 
            j++;
        }

        j = 1;
        for (int i = (row + 1); i < MAX_COLUMN; i++) {
            if (column + j >= MAX_COLUMN) {
                break;
            }
            if (board[i][column + j].equals("[P]") || board[i][column + j].equals("[T]") || board[i][column + j].equals("[C]") || board[i][column + j].equals("[A]") || board[i][column + j].equals("[Q]") || board[i][column + j].equals("[K]")) {
                validOptions[i][column + j] = 1;
                existOptions = true; //si marca un 1, indicamos que existen opciones validas 
                break;
            }
            if (board[i][column + j].equals("(P)") || board[i][column + j].equals("(T)") || board[i][column + j].equals("(C)") || board[i][column + j].equals("(A)") || board[i][column + j].equals("(Q)") || board[i][column + j].equals("(K)")) {
                break;
            }
            validOptions[i][column + j] = 1;
            existOptions = true; //si marca un 1, indicamos que existen opciones validas 
            j++;
        }

        return existOptions;
    }

    public static boolean optionsBlackQueen(String[][] board, int row, int column, int[][] validOptions) {
        boolean existOptions = false; //empezamos con la condicion de que no existen opciones validas

        //MOVIMIENTOS DE LA TORRE
        //Comprovamos hacia arriba , hasta que encuentre una ficha aliada o despues de encontrar una pieza enemiga 
        for (int i = (row - 1); i >= 0; i--) {
            if (board[i][column].equals("(P)") || board[i][column].equals("(T)") || board[i][column].equals("(C)") || board[i][column].equals("(A)") || board[i][column].equals("(Q)") || board[i][column].equals("(K)")) {
                validOptions[i][column] = 1;
                existOptions = true; //si marca un 1, indicamos que existen opciones validas 
                break;
            }
            if (board[i][column].equals("[P]") || board[i][column].equals("[T]") || board[i][column].equals("[C]") || board[i][column].equals("[A]") || board[i][column].equals("[Q]") || board[i][column].equals("[K]")) {
                break;
            }
            validOptions[i][column] = 1;
            existOptions = true; //si marca un 1, indicamos que existen opciones validas 
        }

        //Comprovamos hacia abajo, hasta que encuentre una ficha aliada o despues de encontrar una pieza enemiga 
        for (int i = (row + 1); i < board.length; i++) {
            if (board[i][column].equals("(P)") || board[i][column].equals("(T)") || board[i][column].equals("(C)") || board[i][column].equals("(A)") || board[i][column].equals("(Q)") || board[i][column].equals("(K)")) {
                validOptions[i][column] = 1;
                existOptions = true; //si marca un 1, indicamos que existen opciones validas 
                break;
            }
            if (board[i][column].equals("[P]") || board[i][column].equals("[T]") || board[i][column].equals("[C]") || board[i][column].equals("[A]") || board[i][column].equals("[Q]") || board[i][column].equals("[K]")) {
                break;
            }
            validOptions[i][column] = 1;
            existOptions = true; //si marca un 1, indicamos que existen opciones validas 
        }

        //Comprovamos por la izquierda, hasta que encuentre una ficha aliada o despues de encontrar una pieza enemiga 
        for (int i = (column - 1); i >= 0; i--) {
            if (board[row][i].equals("(P)") || board[row][i].equals("(T)") || board[row][i].equals("(C)") || board[row][i].equals("(A)") || board[row][i].equals("(Q)") || board[row][i].equals("(K)")) {
                validOptions[row][i] = 1;
                existOptions = true;
                break;
            }
            if (board[row][i].equals("[P]") || board[row][i].equals("[T]") || board[row][i].equals("[C]") || board[row][i].equals("[A]") || board[row][i].equals("[Q]") || board[row][i].equals("[K]")) {
                break;
            }
            validOptions[row][i] = 1;
            existOptions = true;
        }

        //Comprovamos hacia la derecha, hasta que encuentre una ficha aliada o despues de encontrar una pieza enemiga 
        for (int i = (column + 1); i < validOptions.length; i++) {
            if (board[row][i].equals("(P)") || board[row][i].equals("(T)") || board[row][i].equals("(C)") || board[row][i].equals("(A)") || board[row][i].equals("(Q)") || board[row][i].equals("(K)")) {
                validOptions[row][i] = 1;
                existOptions = true;
                break;
            }
            if (board[row][i].equals("[P]") || board[row][i].equals("[T]") || board[row][i].equals("[C]") || board[row][i].equals("[A]") || board[row][i].equals("[Q]") || board[row][i].equals("[K]")) {
                break;
            }
            validOptions[row][i] = 1;
            existOptions = true;
        }

        //MOVIMIENTOS DEL ALFIL 
        int j = 1;
        for (int i = (row - 1); i >= 0; i--) {
            if (column + j >= MAX_COLUMN) {
                break;
            }
            if (board[i][column + j].equals("[P]") || board[i][column + j].equals("[T]") || board[i][column + j].equals("[C]") || board[i][column + j].equals("[A]") || board[i][column + j].equals("[Q]") || board[i][column + j].equals("[K]")) {
                break;
            }
            if (board[i][column + j].equals("(P)") || board[i][column + j].equals("(T)") || board[i][column + j].equals("(C)") || board[i][column + j].equals("(A)") || board[i][column + j].equals("(Q)") || board[i][column + j].equals("(K)")) {
                validOptions[i][column + j] = 1;
                existOptions = true; //si marca un 1, indicamos que existen opciones validas
                break;
            }
            validOptions[i][column + j] = 1;
            existOptions = true; //si marca un 1, indicamos que existen opciones validas 
            j++;
        }

        j = 1;
        for (int i = (row - 1); i >= 0; i--) {
            if (column - j < 0) {
                break;
            }
            if (board[i][column - j].equals("[P]") || board[i][column - j].equals("[T]") || board[i][column - j].equals("[C]") || board[i][column - j].equals("[A]") || board[i][column - j].equals("[Q]") || board[i][column - j].equals("[K]")) {
                break;
            }
            if (board[i][column - j].equals("(P)") || board[i][column - j].equals("(T)") || board[i][column - j].equals("(C)") || board[i][column - j].equals("(A)") || board[i][column - j].equals("(Q)") || board[i][column - j].equals("(K)")) {
                validOptions[i][column - j] = 1;
                existOptions = true; //si marca un 1, indicamos que existen opciones validas 
                break;
            }
            validOptions[i][column - j] = 1;
            existOptions = true; //si marca un 1, indicamos que existen opciones validas 
            j++;
        }

        j = 1;
        for (int i = (row + 1); i < MAX_COLUMN; i++) {
            if (column - j < 0) {
                break;
            }
            if (board[i][column - j].equals("[P]") || board[i][column - j].equals("[T]") || board[i][column - j].equals("[C]") || board[i][column - j].equals("[A]") || board[i][column - j].equals("[Q]") || board[i][column - j].equals("[K]")) {
                break;
            }
            if (board[i][column - j].equals("(P)") || board[i][column - j].equals("(T)") || board[i][column - j].equals("(C)") || board[i][column - j].equals("(A)") || board[i][column - j].equals("(Q)") || board[i][column - j].equals("(K)")) {
                validOptions[i][column - j] = 1;
                existOptions = true; //si marca un 1, indicamos que existen opciones validas 
                break;
            }
            validOptions[i][column - j] = 1;
            existOptions = true; //si marca un 1, indicamos que existen opciones validas 
            j++;
        }

        j = 1;
        for (int i = (row + 1); i < MAX_COLUMN; i++) {
            if (column + j >= MAX_COLUMN) {
                break;
            }
            if (board[i][column + j].equals("[P]") || board[i][column + j].equals("[T]") || board[i][column + j].equals("[C]") || board[i][column + j].equals("[A]") || board[i][column + j].equals("[Q]") || board[i][column + j].equals("[K]")) {
                break;
            }
            if (board[i][column + j].equals("(P)") || board[i][column + j].equals("(T)") || board[i][column + j].equals("(C)") || board[i][column + j].equals("(A)") || board[i][column + j].equals("(Q)") || board[i][column + j].equals("(K)")) {
                validOptions[i][column + j] = 1;
                existOptions = true; //si marca un 1, indicamos que existen opciones validas 
                break;
            }
            validOptions[i][column + j] = 1;
            existOptions = true; //si marca un 1, indicamos que existen opciones validas 
            j++;
        }
        return existOptions;
    }

    //metodo que marca en la matriz auxiliar las opciones disponibles para la pieza seleccionada, devuelve si hay al menos 1 posicion
    public static boolean optionsKing(String[][] board, int row, int column, int[][] validOptions) {
        boolean existOptions = false;  //empezamos con la condición de que no hay opciones posibles

        // Si la ficha de arriba es vacia o del oponente, solo si no esta en el limite (row == 0)
        if (row > 0) { //aseguramos que solo se compruebe si existe una celda arriba - control de errores 
            String cell = board[row - 1][column];
            if (!(cell.equals("(P)") || cell.equals("(T)") || cell.equals("(C)")
                    || cell.equals("(A)") || cell.equals("(Q)") || cell.equals("(K)"))) {
                validOptions[row - 1][column] = 1;
                existOptions = true; // si se ha añade un 1 a la matriz, marcamos que existen opciones 
            }
        }
        // abajo
        if (row < 7) { //aseguramos que solo se compruebe si existe una celda abajo - control de errores 
            String cell = board[row + 1][column];
            if (!(cell.equals("(P)") || cell.equals("(T)") || cell.equals("(C)")
                    || cell.equals("(A)") || cell.equals("(Q)") || cell.equals("(K)"))) {
                validOptions[row + 1][column] = 1;
                existOptions = true; // si se ha añade un 1 a la matriz, marcamos que existen opciones 
            }
        }
        // izquierda
        if (column > 0) { //aseguramos que solo se compruebe si existe una celda a la izquierda - control de errores 
            String cell = board[row][column - 1];
            if (!(cell.equals("(P)") || cell.equals("(T)") || cell.equals("(C)")
                    || cell.equals("(A)") || cell.equals("(Q)") || cell.equals("(K)"))) {
                validOptions[row][column - 1] = 1;
                existOptions = true; // si se ha añade un 1 a la matriz, marcamos que existen opciones 
            }
        }
        // derecha
        if (column < 7) { //aseguramos que solo se compruebe si existe una celda a la derecha - control de errores 
            String cell = board[row][column + 1];
            if (!(cell.equals("(P)") || cell.equals("(T)") || cell.equals("(C)")
                    || cell.equals("(A)") || cell.equals("(Q)") || cell.equals("(K)"))) {
                validOptions[row][column + 1] = 1;
                existOptions = true; // si se ha añade un 1 a la matriz, marcamos que existen opciones 
            }
        }
        return existOptions;
    }

    //metodo que marca en la matriz auxiliar las opciones disponibles para la pieza seleccionada, devuelve si hay al menos 1 posicion
    public static boolean optionsBlackKing(String[][] board, int row, int column, int[][] validOptions) {
        boolean existOptions = false;  //empezamos con la condición de que no hay opciones posibles

        // Si la ficha de arriba es vacia o del oponente, solo si no esta en el limite (row == 0)
        if (row > 0) { //aseguramos que solo se compruebe si existe una celda arriba - control de errores 
            String cell = board[row - 1][column];
            if (!(cell.equals("[P]") || cell.equals("[T]") || cell.equals("[C]")
                    || cell.equals("[A]") || cell.equals("[Q]") || cell.equals("[K]"))) {
                validOptions[row - 1][column] = 1;
                existOptions = true; // si se ha añade un 1 a la matriz, marcamos que existen opciones 
            }
        }
        // abajo
        if (row < 7) { //aseguramos que solo se compruebe si existe una celda abajo - control de errores 
            String cell = board[row + 1][column];
            if (!(cell.equals("[P]") || cell.equals("[T]") || cell.equals("[C]")
                    || cell.equals("[A]") || cell.equals("[Q]") || cell.equals("[K]"))) {
                validOptions[row + 1][column] = 1;
                existOptions = true; // si se ha añade un 1 a la matriz, marcamos que existen opciones 
            }
        }
        // izquierda
        if (column > 0) { //aseguramos que solo se compruebe si existe una celda a la izquierda - control de errores 
            String cell = board[row][column - 1];
            if (!(cell.equals("[P]") || cell.equals("[T]") || cell.equals("[C]")
                    || cell.equals("[A]") || cell.equals("[Q]") || cell.equals("[K]"))) {
                validOptions[row][column - 1] = 1;
                existOptions = true; // si se ha añade un 1 a la matriz, marcamos que existen opciones 
            }
        }
        // derecha
        if (column < 7) { //aseguramos que solo se compruebe si existe una celda a la derecha - control de errores 
            String cell = board[row][column + 1];
            if (!(cell.equals("[P]") || cell.equals("[T]") || cell.equals("[C]")
                    || cell.equals("[A]") || cell.equals("[Q]") || cell.equals("[K]"))) {
                validOptions[row][column + 1] = 1;
                existOptions = true; // si se ha añade un 1 a la matriz, marcamos que existen opciones 
            }
        }
        return existOptions;
    }

    public static boolean optionsKigth(String[][] board, int row, int column, int[][] validOptions) {
        boolean existOptions = false;  //empezamos con la condición de que no hay opciones posibles

        if (row < (MAX_ROW - 2) && column < (MAX_COLUMN - 1)) {
            if (board[row + 2][column + 1].equals(" . ") || board[row + 2][column + 1].equals("[P]") || board[row + 2][column + 1].equals("[T]") || board[row + 2][column + 1].equals("[A]") || board[row + 2][column + 1].equals("[K]") || board[row + 2][column + 1].equals("[Q]") || board[row + 2][column + 1].equals("[C]")) {
                validOptions[row + 2][column + 1] = 1;
                existOptions = true;
            }
        }

        if (row < (MAX_ROW - 2) && column > 0) {
            if (board[row + 2][column - 1].equals(" . ") || board[row + 2][column - 1].equals("[P]") || board[row + 2][column - 1].equals("[T]") || board[row + 2][column - 1].equals("[A]") || board[row + 2][column - 1].equals("[K]") || board[row + 2][column - 1].equals("[Q]") || board[row + 2][column - 1].equals("[C]")) {
                validOptions[row + 2][column - 1] = 1;
                existOptions = true;
            }
        }

        if (row > 1 && column < (MAX_COLUMN - 1)) {
            if (board[row - 2][column + 1].equals(" . ") || board[row - 2][column + 1].equals("[P]") || board[row - 2][column + 1].equals("[T]") || board[row - 2][column + 1].equals("[A]") || board[row - 2][column + 1].equals("[K]") || board[row - 2][column + 1].equals("[Q]") || board[row - 2][column + 1].equals("[C]")) {
                validOptions[row - 2][column + 1] = 1;
                existOptions = true;
            }
        }

        if (row > 1 && column > 0) {
            if (board[row - 2][column - 1].equals(" . ") || board[row - 2][column - 1].equals("[P]") || board[row - 2][column - 1].equals("[T]") || board[row - 2][column - 1].equals("[A]") || board[row - 2][column - 1].equals("[K]") || board[row - 2][column - 1].equals("[Q]") || board[row - 2][column - 1].equals("[C]")) {
                validOptions[row - 2][column - 1] = 1;
                existOptions = true;
            }
        }

        if (row < (MAX_ROW - 1) && column < (MAX_COLUMN - 2)) {
            if (board[row + 1][column + 2].equals(" . ") || board[row + 2][column + 1].equals("[P]") || board[row + 2][column + 1].equals("[T]") || board[row + 2][column + 1].equals("[A]") || board[row + 2][column + 1].equals("[K]") || board[row + 2][column + 1].equals("[Q]") || board[row + 2][column + 1].equals("[C]")) {
                validOptions[row + 1][column + 2] = 1;
                existOptions = true;
            }
        }

        if (row < (MAX_ROW - 1) && column > 1) {
            if (board[row + 1][column - 2].equals(" . ") || board[row + 2][column - 1].equals("[P]") || board[row + 2][column - 1].equals("[T]") || board[row + 2][column - 1].equals("[A]") || board[row + 2][column - 1].equals("[K]") || board[row + 2][column - 1].equals("[Q]") || board[row + 2][column - 1].equals("[C]")) {
                validOptions[row + 1][column - 2] = 1;
                existOptions = true;
            }
        }

        if (row > 0 && column < (MAX_COLUMN - 2)) {
            if (board[row - 1][column + 2].equals(" . ") || board[row - 2][column + 1].equals("[P]") || board[row - 2][column + 1].equals("[T]") || board[row - 2][column + 1].equals("[A]") || board[row - 2][column + 1].equals("[K]") || board[row - 2][column + 1].equals("[Q]") || board[row - 2][column + 1].equals("[C]")) {
                validOptions[row - 1][column + 2] = 1;
                existOptions = true;
            }
        }

        if (row > 0 && column > 1) {
            if (board[row - 1][column - 2].equals(" . ") || board[row - 2][column - 1].equals("[P]") || board[row - 2][column - 1].equals("[T]") || board[row - 2][column - 1].equals("[A]") || board[row - 2][column - 1].equals("[K]") || board[row - 2][column - 1].equals("[Q]") || board[row - 2][column - 1].equals("[C]")) {
                validOptions[row - 1][column - 2] = 1;
                existOptions = true;
            }
        }
        return existOptions;
    }

    public static boolean optionsBlackKigth(String[][] board, int row, int column, int[][] validOptions) {
        boolean existOptions = false;  //empezamos con la condición de que no hay opciones posibles

        if (row < (MAX_ROW - 2) && column < (MAX_COLUMN - 1)) {
            if (board[row + 2][column + 1].equals(" . ") || board[row + 2][column + 1].equals("(P)") || board[row + 2][column + 1].equals("(T)") || board[row + 2][column + 1].equals("(A)") || board[row + 2][column + 1].equals("(K)") || board[row + 2][column + 1].equals("(Q)") || board[row + 2][column + 1].equals("(C)")) {
                validOptions[row + 2][column + 1] = 1;
                existOptions = true;
            }
        }

        if (row < (MAX_ROW - 2) && column > 0) {
            if (board[row + 2][column - 1].equals(" . ") || board[row + 2][column - 1].equals("(P)") || board[row + 2][column - 1].equals("(T)") || board[row + 2][column - 1].equals("(A)") || board[row + 2][column - 1].equals("(K)") || board[row + 2][column - 1].equals("(Q)") || board[row + 2][column - 1].equals("(C)")) {
                validOptions[row + 2][column - 1] = 1;
                existOptions = true;
            }
        }

        if (row > 1 && column < (MAX_COLUMN - 1)) {
            if (board[row - 2][column + 1].equals(" . ") || board[row - 2][column + 1].equals("(P)") || board[row - 2][column + 1].equals("(T)") || board[row - 2][column + 1].equals("(A)") || board[row - 2][column + 1].equals("(K)") || board[row - 2][column + 1].equals("(Q)") || board[row - 2][column + 1].equals("(C)")) {
                validOptions[row - 2][column + 1] = 1;
                existOptions = true;
            }
        }

        if (row > 1 && column > 0) {
            if (board[row - 2][column - 1].equals(" . ") || board[row - 2][column - 1].equals("(P)") || board[row - 2][column - 1].equals("(T)") || board[row - 2][column - 1].equals("(A)") || board[row - 2][column - 1].equals("(K)") || board[row - 2][column - 1].equals("(Q)") || board[row - 2][column - 1].equals("(C)")) {
                validOptions[row - 2][column - 1] = 1;
                existOptions = true;
            }
        }

        if (row < (MAX_ROW - 1) && column < (MAX_COLUMN - 2)) {
            if (board[row + 1][column + 2].equals(" . ") || board[row + 2][column + 1].equals("(P)") || board[row + 2][column + 1].equals("(T)") || board[row + 2][column + 1].equals("(A)") || board[row + 2][column + 1].equals("(K)") || board[row + 2][column + 1].equals("(Q)") || board[row + 2][column + 1].equals("(C)")) {
                validOptions[row + 1][column + 2] = 1;
                existOptions = true;
            }
        }

        if (row < (MAX_ROW - 1) && column > 1) {
            if (board[row + 1][column - 2].equals(" . ") || board[row + 2][column - 1].equals("(P)") || board[row + 2][column - 1].equals("(T)") || board[row + 2][column - 1].equals("(A)") || board[row + 2][column - 1].equals("(K)") || board[row + 2][column - 1].equals("(Q)") || board[row + 2][column - 1].equals("(C)")) {
                validOptions[row + 1][column - 2] = 1;
                existOptions = true;
            }
        }

        if (row > 0 && column < (MAX_COLUMN - 2)) {
            if (board[row - 1][column + 2].equals(" . ") || board[row - 2][column + 1].equals("(P)") || board[row - 2][column + 1].equals("(T)") || board[row - 2][column + 1].equals("(A)") || board[row - 2][column + 1].equals("(K)") || board[row - 2][column + 1].equals("(Q)") || board[row - 2][column + 1].equals("(C)")) {
                validOptions[row - 1][column + 2] = 1;
                existOptions = true;
            }
        }

        if (row > 0 && column > 1) {
            if (board[row - 1][column - 2].equals(" . ") || board[row - 2][column - 1].equals("(P)") || board[row - 2][column - 1].equals("(T)") || board[row - 2][column - 1].equals("(A)") || board[row - 2][column - 1].equals("(K)") || board[row - 2][column - 1].equals("(Q)") || board[row - 2][column - 1].equals("(C)")) {
                validOptions[row - 1][column - 2] = 1;
                existOptions = true;
            }
        }
        return existOptions;
    }

    public static boolean moverPieza(String[][] board, int fromRow, int fromCol, int toRow, int toCol) {

        // Si está vacío el origen → no se puede mover
        if (board[fromRow][fromCol].equals(" . ")) {
            return false;
        }
        // Si intentas mover a la misma posición → no
        if (fromRow == toRow && fromCol == toCol) {
            return false;
        }
        // Movimiento básico (solo cambia las posiciones)
        String pieza = board[fromRow][fromCol];
        board[fromRow][fromCol] = " . ";
        board[toRow][toCol] = pieza;

        return true; // movimiento hecho
    }

    public static void main(String[] args) {
        String[][] board = new String[8][8];
        boardInitialization(board);
        javax.swing.SwingUtilities.invokeLater(() -> new ChessGUI(board));

    }
}
