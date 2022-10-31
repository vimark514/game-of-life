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

    int sizeOfArray = 50;
    int WIDTH = 800, HEIGHT = 800;
    boolean isCellsAlive[][];
    JPanel oneCell[][];

    public FrameForGame() {
        frame = new JFrame("Game of Life(Moskaliuk CS21)");
        isCellsAlive = new boolean[sizeOfArray][sizeOfArray];
        oneCell = new JPanel[sizeOfArray][sizeOfArray];

        panel = new JPanel();
        panel.setSize(WIDTH, HEIGHT);
        panel.setBackground(Color.black);
        panel.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        panel.setLayout(new GridLayout(sizeOfArray, sizeOfArray));

        panelForButtons = new JPanel();
        panelForButtons.setBackground(Color.BLACK);

        buttonStop = new JButton("Stop");
        selectForm = new JButton("One cell");

        panelForButtons.add(selectForm);
        panelForButtons.add(buttonStop);

        for (int i = 0; i < sizeOfArray; i++) {
            for (int j = 0; j < sizeOfArray; j++) {
                isCellsAlive[i][j] = random.nextInt(100) < 30;
                JPanel temp = new JPanel();
                if (isCellsAlive[i][j]) {
                    temp.setBackground(Color.white);
                    temp.setBorder(BorderFactory.createLineBorder(Color.darkGray));
                } else {
                    temp.setBackground(Color.black);
                    temp.setBorder(BorderFactory.createLineBorder(Color.darkGray));
                }
                panel.add(temp);
                oneCell[i][j] = temp;
            }
        }

        selectForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if("Glider".equalsIgnoreCase(String.valueOf(selectForm.getText()))){
                    selectForm.setText("One cell");
                } else if("One cell".equalsIgnoreCase(String.valueOf(selectForm.getText()))){
                    selectForm.setText("3 cells");
                } else if("3 cells".equalsIgnoreCase(String.valueOf(selectForm.getText()))){
                    selectForm.setText("12 cells");
                } else if("12 cells".equalsIgnoreCase(String.valueOf(selectForm.getText()))){
                    selectForm.setText("Glider");
                }
            }
        });

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = (e.getX() - 25)/15;
                int y = (e.getY() - 80)/14;
                if("One cell".equalsIgnoreCase(String.valueOf(selectForm.getText()))) {
                    if (isCellsAlive[y][x]) {
                        oneCell[y][x].setBackground(Color.black);
                        oneCell[y][x].setBorder(BorderFactory.createLineBorder(Color.darkGray));
                        isCellsAlive[y][x] = false;
                    } else {
                        oneCell[y][x].setBackground(Color.white);
                        oneCell[y][x].setBorder(BorderFactory.createLineBorder(Color.darkGray));
                        isCellsAlive[y][x] = true;
                    }
                } else if("3 cells".equalsIgnoreCase(String.valueOf(selectForm.getText()))) {
                    int i = y - 1;
                    while (i < y + 2) {
                        if (i < 50 && i >= 0) {
                            oneCell[i][x].setBackground(Color.white);
                            oneCell[i][x].setBorder(BorderFactory.createLineBorder(Color.darkGray));
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
                                oneCell[i][x].setBackground(Color.black);
                                oneCell[i][x].setBorder(BorderFactory.createLineBorder(Color.darkGray));
                                isCellsAlive[i][x] = false;
                            } else {
                                oneCell[i][x].setBackground(Color.white);
                                oneCell[i][x].setBorder(BorderFactory.createLineBorder(Color.darkGray));
                                isCellsAlive[i][x] = true;
                            }
                        }
                        i++;
                    }
                    while(j < x + 4) {
                        if(j < 50 && j >= 0) {
                            if (j == x) {
                                oneCell[y][j].setBackground(Color.black);
                                oneCell[y][j].setBorder(BorderFactory.createLineBorder(Color.darkGray));
                                isCellsAlive[y][j] = false;
                            } else {
                                oneCell[y][j].setBackground(Color.white);
                                oneCell[y][j].setBorder(BorderFactory.createLineBorder(Color.darkGray));
                                isCellsAlive[y][j] = true;
                            }
                        }
                        j++;
                    }
                } else if("Glider".equalsIgnoreCase(String.valueOf(selectForm.getText()))) {
                    int vertical = random.nextInt(1, 3);
                    int horizontal = random.nextInt(1, 3);
                    if ((x >= 2 && x <= 47) && (y >= 2 && y <= 47)) {
                        if (vertical == 1) {
                            for (int i = y; i >= y - 2; i--) {
                                oneCell[i][x].setBackground(Color.white);
                                oneCell[i][x].setBorder(BorderFactory.createLineBorder(Color.darkGray));
                                isCellsAlive[i][x] = true;
                            }
                            if (horizontal == 1) {
                                for (int j = 0; j < 2; j++) {
                                    oneCell[y - j][x - 1 - j].setBackground(Color.white);
                                    oneCell[y - j][x - 1 - j].setBorder(BorderFactory.createLineBorder(Color.darkGray));
                                    isCellsAlive[y - j][x - 1 - j] = true;
                                }
                            } else {
                                for (int j = 0; j < 2; j++) {
                                    oneCell[y - j][x + 1 + j].setBackground(Color.white);
                                    oneCell[y - j][x + 1 + j].setBorder(BorderFactory.createLineBorder(Color.darkGray));
                                    isCellsAlive[y - j][x + 1 + j] = true;
                                }
                            }
                        } else {
                            for (int i = y; i <= y + 2; i++) {
                                oneCell[i][x].setBackground(Color.white);
                                oneCell[i][x].setBorder(BorderFactory.createLineBorder(Color.darkGray));
                                isCellsAlive[i][x] = true;
                            }
                            if (horizontal == 1) {
                                for (int j = 0; j < 2; j++) {
                                    oneCell[y + j][x - 1 - j].setBackground(Color.white);
                                    oneCell[y + j][x - 1 - j].setBorder(BorderFactory.createLineBorder(Color.darkGray));
                                    isCellsAlive[y + j][x - 1 - j] = true;
                                }
                            } else {
                                for (int j = 0; j < 2; j++) {
                                    oneCell[y + j][x + 1 + j].setBackground(Color.white);
                                    oneCell[y + j][x + 1 + j].setBorder(BorderFactory.createLineBorder(Color.darkGray));
                                    isCellsAlive[y + j][x + 1 + j] = true;
                                }
                            }
                        }
                    }
                }
            }
        });

        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean[][] temp = new boolean[sizeOfArray][sizeOfArray];
                for (int i = 0; i < sizeOfArray; i++) {
                    for (int j = 0; j < sizeOfArray; j++) {
                        int count = countNeighbours(i, j);
                        if (isCellsAlive[i][j]) {
                            if (count < 2) {
                                temp[i][j] = false;
                                oneCell[i][j].setBackground(Color.black);
                            }
                            if (count == 3 || count == 2) {
                                temp[i][j] = true;
                                oneCell[i][j].setBackground(Color.white);
                            }
                            if (count > 3) {
                                temp[i][j] = false;
                                oneCell[i][j].setBackground(Color.black);
                            }
                        } else {
                            if (count == 3) {
                                temp[i][j] = true;
                                oneCell[i][j].setBackground(Color.white);
                            }
                        }
                    }
                }
                isCellsAlive = temp;
            }
        });

        timer.start();

        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if("Stop".equalsIgnoreCase(String.valueOf(buttonStop.getText()))){
                    timer.stop();
                    buttonStop.setText("Start");
                } else {
                    timer.start();
                    buttonStop.setText("Stop");
                }
            }
        });

        frame.add(panel);
        frame.add(panelForButtons, BorderLayout.PAGE_START);
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
                } catch (Exception e) {

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
