package barile.vittorio.ui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public static final String NOME_GIOCO = "Duello tra maghi";
    public static final int LARGHEZZA = 800;
    public static final int ALTEZZA = 780;

    private ChoicePanel choicePanel;
    private GameField gameField;

    public MainWindow() {
        super(NOME_GIOCO);

        this.setSize(LARGHEZZA, ALTEZZA);
        this.setResizable(false);
        //this.setAlwaysOnTop(true);

        this.setLayout(null);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void init() {
        gameField = new GameField();
        choicePanel = new ChoicePanel(gameField, gameField);

        gameField.setChoice(choicePanel);

        add(gameField);
        add(choicePanel);

        setVisible(true);
    }


}
