package ru.nsu.g.beryanov.messenger.client;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class ChatGraph extends JFrame {
    private final String version;
    private final JTextField input = new JTextField();
    private final JTextArea textArea = new JTextArea();
    private final Client client = new Client(this);
    private final ArrayList<String> history = new ArrayList<>();
    private int historyPosition = 0;

    public void setFrameName(String s) {
        this.setTitle(s);
    }

    public void receive(String text) {
        if (text != null) {
            if (!text.isEmpty()) {
                if (!textArea.getText().isEmpty())
                    textArea.append("\n");
                textArea.append(text);
            }
        }
    }

    public String getVersion() {
        return version;
    }

    class EnterKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent keyEvent) {

        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                history.add(input.getText());
                historyPosition = history.size() - 1;
                client.sendToServer(input.getText().strip());
                input.setText("");
            }
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
        }
    }

    class LineHistory extends AbstractAction {

        LineHistory() {
            super("LineHistory");
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (!history.isEmpty()) {
                input.setText(history.get(historyPosition));
                if (historyPosition == 0)
                    historyPosition = history.size() - 1;
                else
                    historyPosition--;
            }
        }
    }

    class ButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            history.add(input.getText());
            historyPosition = history.size() -1;
            client.sendToServer(input.getText().strip());
            input.setText("");
        }
    }

    public ChatGraph(String version) {
        super("Chat");
        this.version = version;
        this.setBounds(100, 100, 480, 640);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());
        this.setMinimumSize(new Dimension(480, 640));

        this.setFocusable(true);
        EnterKeyListener enterKeyListener = new EnterKeyListener();
        this.addKeyListener(enterKeyListener);
        textArea.addKeyListener(enterKeyListener);
        input.addKeyListener(enterKeyListener);

        input.getInputMap().put(KeyStroke.getKeyStroke("UP"), new LineHistory());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        ((DefaultCaret) textArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(new LineBorder(Color.GRAY, 3));
        container.add(scrollPane, gbc);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        container.add(input, gbc);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.gridx = 1;
        gbc.gridy = 1;
        JButton button = new JButton("Send");
        button.addActionListener(new ButtonEventListener());
        container.add(button, gbc);
        this.pack();
    }
}
