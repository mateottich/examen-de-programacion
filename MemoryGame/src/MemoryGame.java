
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 
/**
 *
 * @author LENOVO
 */

public class MemoryGame extends JFrame {
     private ArrayList<JButton> buttons;
    private ArrayList<ImageIcon> icons;
    private JButton firstSelected;
    private JButton secondSelected;
    private int pairsFound;
    private final JButton resetButton;
    private JLabel Jlabel;
    public MemoryGame() {
        setTitle("Memory Game");
        setSize(500, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel de botones de juego
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(4, 4));
        add(gamePanel, BorderLayout.CENTER);

        buttons = new ArrayList<>();
        icons = new ArrayList<>();

        
        for (int i = 0; i < 8; i++) {
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource("/images/icon" + i + ".png"));
                icons.add(icon);
                icons.add(icon);
            } catch (Exception e) {
                System.out.println("Error cargando la imagen: " + e.getMessage());
            }
        }
        Collections.shuffle(icons);

       
        for (int i = 0; i < 16; i++) {
            JButton button = new JButton();
            button.addActionListener(new ButtonClickListener());
            buttons.add(button);
            gamePanel.add(button);
        }

         
        resetButton = new JButton("Reset");
        resetButton.addActionListener((ActionEvent e) -> {
            resetGame();
        });
        add(resetButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();

            if (firstSelected == null) {
                firstSelected = clickedButton;
                int index = buttons.indexOf(clickedButton);
                firstSelected.setIcon(icons.get(index));
            } else if (secondSelected == null && clickedButton != firstSelected) {
                secondSelected = clickedButton;
                int index = buttons.indexOf(clickedButton);
                secondSelected.setIcon(icons.get(index));

                 
                if (firstSelected.getIcon() != null && firstSelected.getIcon().toString().equals(secondSelected.getIcon().toString())) {
                    pairsFound++;
                    firstSelected.setEnabled(false);
                    secondSelected.setEnabled(false);
                    firstSelected = null;
                    secondSelected = null;
                    System.out.println("es par");
                } else {
                    Timer timer = new Timer(1000, (ActionEvent evt) -> {
                        if (firstSelected != null && secondSelected != null) {
                            firstSelected.setIcon(null);
                            secondSelected.setIcon(null);
                            firstSelected = null;
                            secondSelected = null;
                            System.out.println("no es par");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
            }

            if (pairsFound == 8) {
                JOptionPane.showMessageDialog(null, "¡Has encontrado todos los pares!");
            }
        }
    }

    private void resetGame() {
        Collections.shuffle(icons);
        for (JButton button : buttons) {
            button.setEnabled(true);
            button.setIcon(null);
        }
        pairsFound = 0;
        firstSelected = null;
        secondSelected = null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MemoryGame::new);
    }
}

