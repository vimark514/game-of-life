package Life;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class FrameForGame extends JPanel{
    Random random = new Random();

    JFrame frame;
    JPanel panel;
    JPanel panelForButtons;
    JButton selectForm;
    JButton buttonStop;
    Cursor cursor = new Cursor(Cursor.HAND_CURSOR);

    int sizeOfArray = 50;
    int WIDTH = 1000, HEIGHT = 800;
    boolean[][] isCellsAlive;
    JPanel[][] oneCell;

    public FrameForGame() {
        frame = new JFrame("Game of Life(Moskaliuk CS21)");
        isCellsAlive = new boolean[sizeOfArray][70];
        oneCell = new JPanel[sizeOfArray][70];

        panel = new JPanel();
        panel.setSize(WIDTH, HEIGHT);
        panel.setBackground(Color.black);
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.setLayout(new GridLayout(sizeOfArray, 70));

        panelForButtons = new JPanel();
        panelForButtons.setBackground(Color.black);

        buttonStop = new JButton("Stop");
        selectForm = new JButton("One cell");

        buttonStop.setCursor(cursor);
        selectForm.setCursor(cursor);

        panelForButtons.add(selectForm);
        panelForButtons.add(buttonStop);

        for (int i = 0; i < sizeOfArray; i++) {
            for (int j = 0; j < 70; j++) {
                isCellsAlive[i][j] = random.nextInt(150) < 70;
                JPanel temp = new JPanel();
                if (isCellsAlive[i][j]) {
                    temp.setBackground(Color.black);
                    temp.setBorder(BorderFactory.createLineBorder(Color.darkGray));
                } else {
                    temp.setBackground(Color.white);
                    temp.setBorder(BorderFactory.createLineBorder(Color.darkGray));
                }
                panel.add(temp);
                oneCell[i][j] = temp;
            }
        }

        selectForm.addActionListener(e -> {
            if("Glider".equalsIgnoreCase(String.valueOf(selectForm.getText()))){
                selectForm.setText("One cell");
            } else if("One cell".equalsIgnoreCase(String.valueOf(selectForm.getText()))){
                selectForm.setText("3 cells");
            } else if("3 cells".equalsIgnoreCase(String.valueOf(selectForm.getText()))){
                selectForm.setText("12 cells");
            } else if("12 cells".equalsIgnoreCase(String.valueOf(selectForm.getText()))){
                selectForm.setText("Glider");
            }
        });

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = (int) ((e.getX() - 10)/14.1);
                int y = (e.getY() - 40)/14;
                if("One cell".equalsIgnoreCase(String.valueOf(selectForm.getText()))) {
                    if (isCellsAlive[y][x]) {
                        oneCell[y][x].setBackground(Color.white);
                        isCellsAlive[y][x] = false;
                    } else {
                        oneCell[y][x].setBackground(Color.black);
                        isCellsAlive[y][x] = true;
                    }
                } else if("3 cells".equalsIgnoreCase(String.valueOf(selectForm.getText()))) {
                    int i = y - 1;
                    while (i < y + 2) {
                        if (i < 50 && i >= 0) {
                            oneCell[i][x].setBackground(Color.black);
                            isCellsAlive[i][x] = true;
                        }
                        i++;
                    }
                }else if("12 cells".equalsIgnoreCase(String.valueOf(selectForm.getText()))){
                    int i = y - 3;
                    int j = x - 3;
                    while(i < y + 4) {
                        if(i < 50 && i >= 0) {
                            if (i == y) {
                                oneCell[i][x].setBackground(Color.white);
                                isCellsAlive[i][x] = false;
                            } else {
                                oneCell[i][x].setBackground(Color.black);
                                isCellsAlive[i][x] = true;
                            }
                        }
                        i++;
                    }
                    while(j < x + 4) {
                        if(j < 70 && j >= 0) {
                            if (j == x) {
                                oneCell[y][j].setBackground(Color.white);
                                isCellsAlive[y][j] = false;
                            } else {
                                oneCell[y][j].setBackground(Color.black);
                                isCellsAlive[y][j] = true;
                            }
                        }
                        j++;
                    }
                } else if("Glider".equalsIgnoreCase(String.valueOf(selectForm.getText()))) {
                    int vertical = random.nextInt(1, 3);
                    int horizontal = random.nextInt(1, 3);
                    if ((x >= 2 && x <= 67) && (y >= 2 && y <= 47)) {
                        if (vertical == 1) {
                            for (int i = y; i >= y - 2; i--) {
                                oneCell[i][x].setBackground(Color.black);
                                isCellsAlive[i][x] = true;
                            }
                            if (horizontal == 1) {
                                for (int j = 0; j < 2; j++) {
                                    oneCell[y - j][x - 1 - j].setBackground(Color.black);
                                    isCellsAlive[y - j][x - 1 - j] = true;
                                }
                            } else {
                                for (int j = 0; j < 2; j++) {
                                    oneCell[y - j][x + 1 + j].setBackground(Color.black);
                                    isCellsAlive[y - j][x + 1 + j] = true;
                                }
                            }
                        } else {
                            for (int i = y; i <= y + 2; i++) {
                                oneCell[i][x].setBackground(Color.black);
                                isCellsAlive[i][x] = true;
                            }
                            if (horizontal == 1) {
                                for (int j = 0; j < 2; j++) {
                                    oneCell[y + j][x - 1 - j].setBackground(Color.black);
                                    isCellsAlive[y + j][x - 1 - j] = true;
                                }
                            } else {
                                for (int j = 0; j < 2; j++) {
                                    oneCell[y + j][x + 1 + j].setBackground(Color.black);
                                    isCellsAlive[y + j][x + 1 + j] = true;
                                }
                            }
                        }
                    }
                }
            }
        });

        Timer timer = new Timer(100, e -> {
            boolean[][] temp = new boolean[sizeOfArray][70];
            for (int i = 0; i < sizeOfArray; i++) {
                for (int j = 0; j < 70; j++) {
                    int count = countNeighbours(i, j);
                    if (isCellsAlive[i][j]) {
                        if (count < 2) {
                            temp[i][j] = false;
                            oneCell[i][j].setBackground(Color.white);
                        }
                        if (count == 3 || count == 2) {
                            temp[i][j] = true;
                            oneCell[i][j].setBackground(Color.black);
                        }
                        if (count > 3) {
                            temp[i][j] = false;
                            oneCell[i][j].setBackground(Color.white);
                        }
                    } else {
                        if (count == 3) {
                            temp[i][j] = true;
                            oneCell[i][j].setBackground(Color.black);
                        }
                    }
                }
            }
            isCellsAlive = temp;
        });

        timer.start();

        buttonStop.addActionListener(e -> {
            if("Stop".equalsIgnoreCase(String.valueOf(buttonStop.getText()))){
                timer.stop();
                buttonStop.setText("Start");
            } else {
                timer.start();
                buttonStop.setText("Stop");
            }
        });

        frame.add(panel);
        frame.add(panelForButtons, BorderLayout.PAGE_END);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

    private int countNeighbours(int x, int y) {
        int count = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                try {
                    if (isCellsAlive[i][j]) {
                        count++;
                    }
                } catch (Exception ignored) {

                }
            }
        }
        if (isCellsAlive[x][y]) {
            count--;
        }
        return count;
    }

    public Dimension getPreferredSize () {return new Dimension(WIDTH, HEIGHT);}
}
