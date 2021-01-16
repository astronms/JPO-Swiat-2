import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

public class Main {
    public static void main(String[] args) {
        final int frameWidth = 500, frameHeight = 190;
        final int LCDHeight = 768, LCDWidth = 1366;
        JFrame frame = new JFrame();
        JButton setBoardBtn = new JButton("Ustaw wielkosc pola gry");
        JButton readFromFileBtn = new JButton("Wczytaj zapis");
        JLabel emptyLabel = new JLabel("");
        JLabel xLabel = new JLabel("X");
        JLabel yLabel = new JLabel("Y");
        JPanel labelPanel = new JPanel();
        JPanel setSizePanel = new JPanel();
        JPanel restPanel = new JPanel();
        JSpinner sizeXSpin = new JSpinner();
        JSpinner sizeYSpin = new JSpinner();

        frame.setLayout(null);
        frame.setTitle("Projekt JPO JAVA Maciej Stępień 148339");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds((LCDWidth - frameWidth) / 2, (LCDHeight - frameHeight) / 2, frameWidth, frameHeight);
        frame.setResizable(false);

        labelPanel.setLayout(new GridLayout(1, 3, 10, 10));
        labelPanel.add(emptyLabel);
        labelPanel.add(xLabel);
        labelPanel.add(yLabel);
        labelPanel.setBounds(20, 0, frameWidth - 60, 20);
        frame.add(labelPanel);

        setSizePanel.setLayout(new GridLayout(1, 3, 10, 10));
        setSizePanel.add(readFromFileBtn);
        setSizePanel.add(sizeXSpin);
        setSizePanel.add(sizeYSpin);
        sizeXSpin.setValue(20);
        sizeYSpin.setValue(20);
        setSizePanel.setBounds(20, 20, frameWidth - 60, 53);
        frame.add(setSizePanel);

        restPanel.setLayout(new GridLayout(1, 1, 10, 10));
        restPanel.add(setBoardBtn);
        restPanel.setBounds(120, 88, frameWidth - 260, 57);
        frame.add(restPanel);

        frame.setVisible(true);

        setBoardBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((int) sizeXSpin.getValue() >= 3 && (int) sizeYSpin.getValue() >= 3) {
                    frame.dispose();
                    new World((int) sizeXSpin.getValue(), (int) sizeYSpin.getValue());
                }

            }
        });
        readFromFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new World();
            }
        });
    }
}
