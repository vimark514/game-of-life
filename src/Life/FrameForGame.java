package Life;

import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;

public class FrameForGame extends JPanel{
    Random random = new Random();

    private final JFrame frame;
    private JPanel panel;
    private final JPanel panelForButtons;
    private final JPanel panelForHelp;
    private final JButton buttonStop;
    private final JButton buttonClear;
    private final JButton help;
    private final JComboBox<String> forms;
    private final JButton music;
    private final Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
    private final String SONG;

    private final int sizeOfY = 50;
    private final int sizeOfX = 70;
    private final int WIDTH = 1000;
    private final int HEIGHT = 800;
    private boolean[][] isCellsAlive;
    private final JPanel[][] oneCell;
    private final JLabel icon;
    private final String pathToImage;
    private final Music mu;
    private XYfromFile xy;

    public FrameForGame() {
        mu = new Music();
        SONG = "Адажио Контабиле   Гайдн.wav";
        mu.setFile(SONG);
        mu.play();

        frame = new JFrame("Game of Life(Moskaliuk CS21)");
        isCellsAlive = new boolean[sizeOfY][sizeOfX];
        oneCell = new JPanel[sizeOfY][sizeOfX];

        panel = new JPanel();
        panel.setSize(WIDTH, HEIGHT);
        icon = new JLabel();
        pathToImage = "StartFrame.png";
        icon.setIcon(new ImageIcon(pathToImage));
        panel.add(icon, BorderLayout.CENTER);
        frame.add(panel);

        panelForButtons = new JPanel();
        panelForButtons.setBackground(Color.black);
        panelForHelp = new JPanel();

        forms = new JComboBox<>();
        forms.addItem("One cell");
        forms.addItem("3 cells");
        forms.addItem("12 cells");
        forms.addItem("Glider");
        forms.addItem("Spaceship");
        forms.addItem("Oscillator");
        forms.addItem("Blinker Fuse");

        music = new JButton("Stop music");
        music.addActionListener(e ->{
            if("Stop music".equalsIgnoreCase(String.valueOf(music.getText()))){
                music.setText("Start music");
                try {
                    mu.stop();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                music.setText("Stop music");
                mu.setFile(SONG);
                mu.play();
            }
        });

        buttonStop = new JButton("Stop life");
        buttonClear = new JButton("Start game");
        help = new JButton("Help");
        help.setBorderPainted(false);
        help.setBackground(Color.lightGray);
        help.setFocusPainted(false);

        help.setCursor(cursor);
        buttonStop.setCursor(cursor);
        forms.setCursor(cursor);
        buttonClear.setCursor(cursor);

        help.addActionListener(e ->{
            JOptionPane.showMessageDialog(new JFrame(), "Правила гри: " +
                    "\n\n1. Якщо клітина має менше 2 сусідів - вона помирає від самотності" +
                    "\n2. Якщо клітина має більше 3 сусідів - вона помирає від перенаселення" +
                    "\n3. Якщо клітина має 2 або 3 сусіди - вона лишається живою" +
                    "\n4. Нова клітина створюється, якщо в неї є рівно 3 сусіди" +
                    "\n\nЗначення кнопок:" +
                    "\n\"Start game\" - розпочинає гру та вмикає фонову музику" +
                    "\n\"Clear\" - заповнює пусте поле без клітин" +
                    "\n\"Random fill\" - заповнює поле з рандомним розташуванням клітин" +
                    "\n\"Stop music\" та \"Start music\" - зупиняє та вмикає заново фонову музику, відповідно" +
                    "\n\"Stop life\" та \"Start life\" - зупиняє та вмикає процес гри" +
                    "\nКомбіноване меню з різними назвами форм - створює на полі обраний вами набір клітинок:" +
                    "\n1) \"One cell\" - одна клітина" +
                    "\n2) \"3 cells\" - 3 розташовані вертикально клітини, що утворюють разом блінкер(осцилятор)" +
                    "\n3) \"12 cells\" - 12 клітин" +
                    "\n4) \"Glider\" - рухомий у рандомному напрямку глайдер" +
                    "\n5) \"Spaceship\" - рухомий космічний корабель" +
                    "\n6) \"Oscillator\" - нерухомий осцилятор" +
                    "\n7) \"Blinker Fuse\" - рухомий блінкер ф'юз, що \"їсть\" 3-клітинні осцилятори(блінкери)");
        });

        panelForButtons.add(buttonClear);
        panelForButtons.add(buttonStop);
        panelForButtons.add(music);
        panelForButtons.add(forms);
        panelForHelp.add(help);

        Timer timer = new Timer(100, e -> {
            boolean[][] temp = new boolean[sizeOfY][sizeOfX];
            for (int i = 0; i < sizeOfY; i++) {
                for (int j = 0; j < sizeOfX; j++) {
                    int count = countNeighbours(i, j);
                    if (isCellsAlive[i][j]) {
                        if (count < 2) {
                            temp[i][j] = false;
                        } else if (count == 3 || count == 2) {
                            temp[i][j] = true;
                        } else if (count > 3) {
                            temp[i][j] = false;
                        }
                    } else {
                        if (count == 3) {
                            temp[i][j] = true;
                        }
                    }
                }
            }
            isCellsAlive = temp;
            for (int i = 0; i < sizeOfY; i++) {
                for (int j = 0; j < sizeOfX; j++) {
                    if (isCellsAlive[i][j]) {
                        oneCell[i][j].setBackground(Color.yellow);
                    } else{
                        oneCell[i][j].setBackground(Color.gray);
                    }
                }
            }
        });

        buttonStop.addActionListener(e -> {
            if("Stop life".equalsIgnoreCase(String.valueOf(buttonStop.getText()))){
                timer.stop();
                buttonStop.setText("Start life");
            } else {
                timer.start();
                buttonStop.setText("Stop life");
            }
        });

        buttonClear.addActionListener(e -> {
            frame.remove(panel);

            timer.start();
            buttonStop.setText("Stop life");

            panel = new JPanel();
            panel.setSize(WIDTH, HEIGHT);
            panel.setBackground(Color.gray);
            panel.setLayout(new GridLayout(sizeOfY, sizeOfX));
            frame.add(panel);

            if("Random fill".equalsIgnoreCase(String.valueOf(buttonClear.getText())) ||
                    "Start game".equalsIgnoreCase(String.valueOf(buttonClear.getText()))){
                buttonClear.setText("Clear");
                for (int i = 0; i < sizeOfY; i++) {
                    for (int j = 0; j < sizeOfX; j++) {
                        isCellsAlive[i][j] = random.nextInt(150) < 70;
                        JPanel temp = new JPanel();
                        if (isCellsAlive[i][j]) {
                            temp.setBackground(Color.yellow);
                            temp.setBorder(BorderFactory.createLineBorder(Color.darkGray));
                        } else {
                            temp.setBackground(Color.gray);
                            temp.setBorder(BorderFactory.createLineBorder(Color.darkGray));
                        }
                        panel.add(temp);
                        oneCell[i][j] = temp;
                    }
                }
            } else if("Clear".equalsIgnoreCase(String.valueOf(buttonClear.getText()))){
                buttonClear.setText("Random fill");
                for (int i = 0; i < sizeOfY; i++) {
                    for (int j = 0; j < sizeOfX; j++) {
                        isCellsAlive[i][j] = false;
                        JPanel temp = new JPanel();
                        temp.setBackground(Color.gray);
                        temp.setBorder(BorderFactory.createLineBorder(Color.darkGray));
                        panel.add(temp);
                        oneCell[i][j] = temp;
                    }
                }
            }
        });

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = (int) ((e.getX() - 10) / 14.1);
                int y = (e.getY() - 85) / 13;
                if (!"Start game".equalsIgnoreCase(buttonClear.getText()) && (y >= 0 && y < 50)) {
                    if ("One cell".equalsIgnoreCase(String.valueOf(forms.getSelectedItem()))) {
                        if (isCellsAlive[y][x]) {
                            oneCell[y][x].setBackground(Color.gray);
                            isCellsAlive[y][x] = false;
                        } else {
                            oneCell[y][x].setBackground(Color.yellow);
                            isCellsAlive[y][x] = true;
                        }
                    } else if ("3 cells".equalsIgnoreCase(String.valueOf(forms.getSelectedItem()))) {
                        int i = y - 1;
                        while (i < y + 2) {
                            if (i < sizeOfY && i >= 0) {
                                oneCell[i][x].setBackground(Color.yellow);
                                isCellsAlive[i][x] = true;
                            }
                            i++;
                        }
                    } else if ("12 cells".equalsIgnoreCase(String.valueOf(forms.getSelectedItem()))) {
                        int i = y - 3;
                        int j = x - 3;
                        while (i < y + 4) {
                            if (i < sizeOfY && i >= 0) {
                                if (i == y) {
                                    oneCell[i][x].setBackground(Color.gray);
                                    isCellsAlive[i][x] = false;
                                } else {
                                    oneCell[i][x].setBackground(Color.yellow);
                                    isCellsAlive[i][x] = true;
                                }
                            }
                            i++;
                        }
                        while (j < x + 4) {
                            if (j < sizeOfX && j >= 0) {
                                if (j == x) {
                                    oneCell[y][j].setBackground(Color.gray);
                                    isCellsAlive[y][j] = false;
                                } else {
                                    oneCell[y][j].setBackground(Color.yellow);
                                    isCellsAlive[y][j] = true;
                                }
                            }
                            j++;
                        }
                    } else if ("Glider".equalsIgnoreCase(String.valueOf(forms.getSelectedItem()))) {
                        int vertical = random.nextInt(1, 3);
                        int horizontal = random.nextInt(1, 3);
                        if ((x >= 2 && x <= sizeOfX - 3) && (y >= 2 && y <= sizeOfY - 3)) {
                            if (vertical == 1) {
                                for (int i = y; i >= y - 2; i--) {
                                    oneCell[i][x].setBackground(Color.yellow);
                                    isCellsAlive[i][x] = true;
                                }
                                if (horizontal == 1) {
                                    for (int j = 0; j < 2; j++) {
                                        oneCell[y - j][x - 1 - j].setBackground(Color.yellow);
                                        isCellsAlive[y - j][x - 1 - j] = true;
                                    }
                                } else {
                                    for (int j = 0; j < 2; j++) {
                                        oneCell[y - j][x + 1 + j].setBackground(Color.yellow);
                                        isCellsAlive[y - j][x + 1 + j] = true;
                                    }
                                }
                            } else {
                                for (int i = y; i <= y + 2; i++) {
                                    oneCell[i][x].setBackground(Color.yellow);
                                    isCellsAlive[i][x] = true;
                                }
                                if (horizontal == 1) {
                                    for (int j = 0; j < 2; j++) {
                                        oneCell[y + j][x - 1 - j].setBackground(Color.yellow);
                                        isCellsAlive[y + j][x - 1 - j] = true;
                                    }
                                } else {
                                    for (int j = 0; j < 2; j++) {
                                        oneCell[y + j][x + 1 + j].setBackground(Color.yellow);
                                        isCellsAlive[y + j][x + 1 + j] = true;
                                    }
                                }
                            }
                        }
                    } else {
                        if ("Spaceship".equalsIgnoreCase(String.valueOf(forms.getSelectedItem()))) {
                            xy = new XYfromFile("spaceship.txt");
                        } else if ("Oscillator".equalsIgnoreCase(String.valueOf(forms.getSelectedItem()))) {
                            xy = new XYfromFile("oscillator1.txt");
                        } else if ("Blinker Fuse".equalsIgnoreCase(String.valueOf(forms.getSelectedItem()))) {
                            xy = new XYfromFile("blinker_fuse.txt");
                        }
                        for (int i = 0; i < xy.arrayX.size(); i++) {
                            if ((y + xy.getArrayY().get(i) > 0 && y + xy.getArrayY().get(i) < sizeOfY) &&
                                    (x + xy.getArrayX().get(i) > 0 && x + xy.getArrayX().get(i) < sizeOfX)) {
                                    oneCell[y + xy.getArrayY().get(i)][x + xy.getArrayX().get(i)].setBackground(Color.yellow);
                                    isCellsAlive[y + xy.getArrayY().get(i)][x + xy.getArrayX().get(i)] = true;
                            }
                        }
                    }
                }
            }
        });

        frame.add(panelForButtons, BorderLayout.PAGE_START);
        frame.add(panelForHelp, BorderLayout.PAGE_END);
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