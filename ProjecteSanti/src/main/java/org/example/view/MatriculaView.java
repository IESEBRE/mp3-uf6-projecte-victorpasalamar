package org.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MatriculaView extends JFrame {
    private JTabbedPane pestanyes;
    private JTable taula;
    private JScrollPane scrollPane1;
    private JButton insertarButton;
    private JButton modificarButton;
    private JButton borrarButton;
    private JTextField campNomJefe;
    private JTextField campVida;
    private JCheckBox caixaDificultat;
    private JPanel panel;
    private JTextField campNumAtacs;
    private JComboBox comboBox1;

    public JTabbedPane getPestanyes() {
        return pestanyes;
    }

    public JTable getTaula() {
        return taula;
    }

    public JButton getBorrarButton() {
        return borrarButton;
    }

    public JButton getModificarButton() {
        return modificarButton;
    }

    public JButton getInsertarButton() {
        return insertarButton;
    }

    public JTextField getCampNomJefe() {
        return campNomJefe;
    }

    public JTextField getCampVida() {
        return campVida;
    }

    public JCheckBox getCaixaDificultat() {
        return caixaDificultat;
    }

    public JTextField getCampNumAtacs() {
        return campNumAtacs;
    }

    public JComboBox getComboBox1() {
        return comboBox1;
    }

    //Constructor de la classe
    public MatriculaView() {


        //Per poder vore la finestra
        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(false);
        this.setSize(650, 750);


    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        scrollPane1 = new JScrollPane();
        taula = new JTable();
        pestanyes = new JTabbedPane();
        taula.setModel(new DefaultTableModel());
        taula.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPane1.setViewportView(taula);

    }

}
