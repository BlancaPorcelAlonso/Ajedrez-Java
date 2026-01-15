/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ajedrez;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class ChessGUI extends JFrame { // JFrame = una ventana de Swing (barra de título, borde, etc).

    static final int MAX_COLUMNS = 8; // Constante para el tamaño del tablero. 
    static final int MAX_ROWS = 8; // Constante para el tamaño del tablero.
    private final JButton[][] cells = new JButton[MAX_ROWS][MAX_COLUMNS]; // Matriz de botones que representarán las 64 casillas visuales del tablero.
    private final String[][] board; // Referencia a tu misma matriz de consola (String[8][8]). No copiamos, solo apuntamos a ella.
    private final JLabel status = new JLabel("Selecciona una casilla…"); // Muestra mensajes al usuario 
    private Point selected = null; // Guarda la casilla seleccionada en el primer clic. Si es null = aún no hay.
    private final int[][] valid = new int[8][8]; // 0 = no válida, 1 = válida
    private int currentPlayer = 1;                 // 1 = blancas, 2 = negras
    private final JLabel turn = new JLabel("Turno: Jugador 1 (blancas)");

    public ChessGUI(String[][] board) {  // El constructor recibe la matriz y la guarda.
        super("Ajedrez (Swing)"); // super("Ajedrez (Swing)") pone el título en la barra de la ventana.
        this.board = board;

        getContentPane().setLayout(new BorderLayout());

        JPanel grid = new JPanel(new GridLayout(MAX_ROWS, MAX_COLUMNS)); //Creamos un panel con un GridLayout 8×8: rejilla perfecta para 64 botones.
        Color light = new Color(255, 240, 245); // rosa claro (lavender blush)
        Color dark = new Color(255, 182, 193);  // rosa medio (light pink) ç
        // Definimos los dos colores de las casillas (claro/oscuro)

        for (int r = 0; r < MAX_ROWS; r++) {
            for (int c = 0; c < MAX_COLUMNS; c++) {
                JButton b = new JButton(); // añade nuevo boton
                b.setFocusPainted(false); //Sin anillo de foco (setFocusPainted(false)),
                // Colores del borde
                Color bordeNormal = Color.WHITE;                 // borde blanco siempre
                Color bordeHover = new Color(255, 105, 180);     // rosa brillante (hot pink)
                // Borde inicial (blanco)
                b.setBorder(BorderFactory.createLineBorder(bordeNormal, 2));
                // Efecto hover: al entrar/salir del ratón
                b.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                        b.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(bordeHover, 2), // reborde rosa exterior
                                BorderFactory.createLineBorder(bordeNormal, 2) // borde blanco interior
                        ));
                    }

                    public void mouseExited(java.awt.event.MouseEvent e) {
                        b.setBorder(BorderFactory.createLineBorder(bordeNormal, 2)); // vuelve al blanco
                    }
                });

                final int rr = r, cc = c; // guardamos coords para usarlas en el listener
                b.addActionListener(e -> {
                    if (selected == null) {
                        // 1️⃣ Primer clic → selecciona

                        String piezaClick = board[rr][cc];
                        if (isEmpty(piezaClick)) {
                            status.setText("Esa casilla está vacía.");
                            return;
                        }
                        if (currentPlayer == 1 && !isWhite(piezaClick)) {
                            status.setText("Ahora juegan las blancas.");
                            return;
                        }
                        if (currentPlayer == 2 && !isBlack(piezaClick)) {
                            status.setText("Ahora juegan las negras.");
                            return;
                        }
                        selected = new Point(rr, cc);

                        // Guardamos color original (puedes usar getBackground si lo prefieres)
                        Color selectedColor = new Color(255, 105, 180); // rosa más fuerte
                        b.setBackground(selectedColor);

                        // calcular opciones y pintarlas
                        boolean hayOpciones = computeOptionsFor(rr, cc);
                        paintHighlights();

                        if (!hayOpciones) {
                            status.setText("Sin movimientos válidos para esa pieza.");
                        }

                        status.setText("Seleccionada: fila " + (rr + 1) + ", col " + (cc + 1)
                                + " (" + board[rr][cc] + ")");
                    } else {
                        // 2️⃣ Segundo clic → destino
                        Point from = selected;
                        int fr = from.x, fc = from.y;
                        int tr = rr, tc = cc;

                        String piezaDestino = board[tr][tc];
                        String piezaOrigen = board[fr][fc];

                        // 🔹 Si haces clic sobre otra pieza de tu mismo color → cambiar selección
                        if (currentPlayer == 1 && isWhite(piezaDestino)) {
                            clearValid();
                            paintHighlights();
                            cells[fr][fc].setBackground(baseColor(fr, fc)); // restaurar color anterior
                            selected = new Point(tr, tc); // nueva selección
                            cells[tr][tc].setBackground(new Color(255, 105, 180)); // rosa fuerte
                            computeOptionsFor(tr, tc);
                            paintHighlights();
                            status.setText("Seleccionada nueva pieza blanca (" + (tr + 1) + "," + (tc + 1) + ")");
                            return;
                        }
                        if (currentPlayer == 2 && isBlack(piezaDestino)) {
                            clearValid();
                            paintHighlights();
                            cells[fr][fc].setBackground(baseColor(fr, fc));
                            selected = new Point(tr, tc);
                            cells[tr][tc].setBackground(new Color(255, 105, 180));
                            computeOptionsFor(tr, tc);
                            paintHighlights();
                            status.setText("Seleccionada nueva pieza negra (" + (tr + 1) + "," + (tc + 1) + ")");
                            return;
                        }

                        // 🔹 Si NO se cumple, seguimos como antes con el intento de movimiento:
                        if (valid[tr][tc] != 1) {
                            status.setText("Casilla no válida.");
                            return;
                        }

                        String pieza = board[fr][fc]; // lo que hay en el origen: "(P)", "[T]", etc.

                        if (pieza.equals("(P)") && tr == 0) {
                            Object[] choices = {"(Q)", "(T)", "(A)", "(C)"};
                            Object pick = JOptionPane.showInputDialog(
                                    this, "Promoción (blancas):", "Promoción",
                                    JOptionPane.PLAIN_MESSAGE, null, choices, "(Q)"
                            );
                            if (pick != null) {
                                pieza = pick.toString(); // sustituimos el peón por la elección
                            }
                        }

                        if (pieza.equals("[P]") && tr == 7) {
                            Object[] choices = {"[Q]", "[T]", "[A]", "[C]"};
                            Object pick = JOptionPane.showInputDialog(
                                    this, "Promoción (negras):", "Promoción",
                                    JOptionPane.PLAIN_MESSAGE, null, choices, "[Q]"
                            );
                            if (pick != null) {
                                pieza = pick.toString();
                            }
                        }

                        board[fr][fc] = " . ";   // origen queda vacío
                        board[tr][tc] = pieza;   // destino recibe la pieza (ya promocionada si tocaba)

                        JButton prev = cells[fr][fc]; // botón del origen
                        prev.setBackground(baseColor(fr, fc)); // vuelve al color rosa base

                        selected = null;   // ya no hay casilla seleccionada
                        clearValid();      // valid[][] = 0 en todas
                        paintHighlights(); // quita cualquier fondo de “válida”

                        renderBoard(); // vuelve a poner ♟♕… según board[][] actual
                        status.setText("Movimiento: (" + (fr + 1) + "," + (fc + 1) + ") → (" + (tr + 1) + "," + (tc + 1) + ")");

                        currentPlayer = (currentPlayer == 1) ? 2 : 1;
                        turn.setText(currentPlayer == 1 ? "Turno: Jugador 1 (blancas)" : "Turno: Jugador 2 (negras)");
                    }
                });

                b.setMargin(new Insets(0, 0, 0, 0)); //sin márgenes internos
                b.setPreferredSize(new Dimension(64, 64)); //tamaño sugerido
                b.setBackground(((r + c) % 2 == 0) ? light : dark); // va alternando claro y oscuro segun (r+c)%2==0)
                b.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));// fuente grande para los símbolos,
                cells[r][c] = b; //los guarda en cells[r][c] y los mete en la rejilla.
                grid.add(b);
            }
        }

        JPanel north = new JPanel(new BorderLayout());
        north.setBackground(new Color(255, 240, 245));
        /*JLabel title = new JLabel("♖👑 AJEDREZ 👑♖");
        title.setHorizontalAlignment(SwingConstants.CENTER); // <-- Centra el texto
        title.setFont(new Font("Serif", Font.BOLD, 26));
        title.setForeground(new Color(255, 105, 180));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        title.setBackground(new Color(250, 240, 245));
        title.setOpaque(true);*/
        // Cargar imagen (por ejemplo "titulo.png" dentro de la carpeta del proyecto)
        ImageIcon original = new ImageIcon(getClass().getResource("/ajedrez/resources/titulo.png"));
        Image img = original.getImage().getScaledInstance(300, 90, Image.SCALE_SMOOTH);
        ImageIcon iconoTitulo = new ImageIcon(img);
        // Crear JLabel con imagen
        JLabel title = new JLabel(iconoTitulo);

        // Centrar la imagen dentro del label
        title.setHorizontalAlignment(SwingConstants.CENTER);

        // Opcional: añadir borde como antes
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        // ⬅️ Si quieres que el fondo sea visible, debes setOpaque(true) también
        title.setOpaque(false);
        title.setBackground(new Color(250, 240, 245));
        
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS)); // columna

        status.setFont(status.getFont().deriveFont(Font.BOLD, 16f));
        turn.setFont(turn.getFont().deriveFont(Font.BOLD, 16f));

        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        status.setAlignmentX(Component.CENTER_ALIGNMENT);
        turn.setAlignmentX(Component.CENTER_ALIGNMENT);

        north.add(Box.createVerticalGlue());   // espacio arriba
        north.add(title);
        north.add(Box.createVerticalStrut(30)); // espacio entre título y estado
        north.add(status);
        north.add(Box.createVerticalStrut(15)); // espacio entre estado y turno
        north.add(turn);
        north.add(Box.createVerticalGlue()); // empuja todo hacia arriba, como en tu dibujo
        
        add(north, BorderLayout.WEST);

        add(grid, BorderLayout.CENTER); // tablero en el centro

        pack(); //Ajusta la ventana al tamaño mínimo que necesita el contenido (los 64 botones).
        setLocationRelativeTo(null); //– Centra la ventana en la pantalla.
        setDefaultCloseOperation(EXIT_ON_CLOSE); //Al cerrar la ventana, el programa termina.
        renderBoard();   // <-- pinta el contenido de tu matriz

        setVisible(true);
    }

    /* Convierte tus strings ("[P]", "(T)", " . ") en símbolos Unicode.
        " . " → "" (casilla vacía no muestra texto).
        Si llega algo raro, devuelve tal cual (default).*/
    private String toIcon(String s) {
        if (s == null || s.equals(" . ")) {
            return "";
        }
        switch (s) {
            case "[P]":
                return "♟";
            case "[T]":
                return "♜";
            case "[C]":
                return "♞";
            case "[A]":
                return "♝";
            case "[Q]":
                return "♛";
            case "[K]":
                return "♚";
            case "(P)":
                return "♙";
            case "(T)":
                return "♖";
            case "(C)":
                return "♘";
            case "(A)":
                return "♗";
            case "(Q)":
                return "♕";
            case "(K)":
                return "♔";
            default:
                return s;
        }
    }

    /*Estas dos líneas al final de renderBoard() sirven para actualizar la pantalla cuando cambia algo.
    revalidate() → le dice al layout de Swing: “por si el tamaño o la disposición cambiaron, vuelve a calcularlo”.
    repaint() → le dice al sistema: “redibuja la ventana”
    En resumen:
    Cuando tu código cambie la board y llame a renderBoard(), estas dos líneas garantizan que el tablero se refresque visualmente.*/
    public void renderBoard() {
        for (int r = 0; r < MAX_ROWS; r++) {
            for (int c = 0; c < MAX_COLUMNS; c++) {
                cells[r][c].setText(toIcon(board[r][c]));
            }
        }
        revalidate();
        repaint();
    }

    private boolean isEmpty(String s) {
        return s == null || s.equals(" . ");
    }

    private boolean isWhite(String s) {
        return s != null && s.startsWith("(");
    } // tus blancas

    private boolean isBlack(String s) {
        return s != null && s.startsWith("[");
    } // tus negras

    // Limpia la matriz de opciones válidas
    private void clearValid() {
        for (int r = 0; r < MAX_ROWS; r++) {
            for (int c = 0; c < MAX_COLUMNS; c++) {
                valid[r][c] = 0;
            }
        }
    }

    // Devuelve el color base de una casilla (rosa claro/medio)
    private Color baseColor(int r, int c) {
        return ((r + c) % 2 == 0)
                ? new Color(255, 240, 245) // claro
                : new Color(255, 182, 193);  // medio
    }

    // Pinta fondos según valid[][] (1 = resalta)
    private void paintHighlights() {
        for (int r = 0; r < MAX_ROWS; r++) {
            for (int c = 0; c < MAX_COLUMNS; c++) {
                if (valid[r][c] == 1) {
                    cells[r][c].setBackground(new Color(255, 218, 185)); // peachpuff suave
                } else {
                    cells[r][c].setBackground(baseColor(r, c));
                }
            }
        }
    }

    // Rellena valid[][] llamando a tus options... según la pieza
    private boolean computeOptionsFor(int r, int c) {
        clearValid();
        String p = board[r][c];
        if (p == null || p.equals(" . ")) {
            return false;
        }

        boolean any = false;
        int[][] out = valid; // alias por claridad

        switch (p) {
            // BLANCAS (tus paréntesis)
            case "(P)":
                any = Ajedrez.optionsPeon(board, r, c, out);
                break;
            case "(T)":
                any = Ajedrez.optionsRook(board, r, c, out);
                break;
            case "(A)":
                any = Ajedrez.optionsAlfil(board, r, c, out);
                break;
            case "(C)":
                any = Ajedrez.optionsKigth(board, r, c, out);
                break;
            case "(Q)":
                any = Ajedrez.optionsQueen(board, r, c, out);
                break;
            case "(K)":
                any = Ajedrez.optionsKing(board, r, c, out);
                break;

            // NEGRAS (tus corchetes)
            case "[P]":
                any = Ajedrez.optionsBlackPeon(board, r, c, out);
                break;
            case "[T]":
                any = Ajedrez.optionsBlackRook(board, r, c, out);
                break;
            case "[A]":
                any = Ajedrez.optionsBlackAlfil(board, r, c, out);
                break;
            case "[C]":
                any = Ajedrez.optionsBlackKigth(board, r, c, out);
                break;
            case "[Q]":
                any = Ajedrez.optionsBlackQueen(board, r, c, out);
                break;
            case "[K]":
                any = Ajedrez.optionsBlackKing(board, r, c, out);
                break;

            default:
                any = false;
        }
        return any;
    }
}
