package org.example.controller;

import org.example.model.entities.Jefe;
import org.example.model.exceptions.DAOException;
import org.example.view.ModelComponentsVisuals;
import org.example.model.impls.AlumneDAOJDBCOracleImpl;
import org.example.view.MatriculaView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Controller implements PropertyChangeListener { //1. Implementació de interfície PropertyChangeListener


    private final ModelComponentsVisuals modelComponentsVisuals = new ModelComponentsVisuals();
    private final AlumneDAOJDBCOracleImpl dadesJefe;
    private final MatriculaView view;

    public Controller(AlumneDAOJDBCOracleImpl dadesAlumnes, MatriculaView view) {
        this.dadesJefe = dadesAlumnes;
        this.view = view;

        //5. Necessari per a que Controller reaccione davant de canvis a les propietats lligades
        canvis.addPropertyChangeListener(this);

        lligaVistaModel();

        afegirListeners();

        //Si no hem tingut cap poroblema amb la BD, mostrem la finestra
        view.setVisible(true);

    }

    private void lligaVistaModel() {

        //Carreguem la taula d'alumnes en les dades de la BD
        try {
            dadesJefe.creartaules();
            setModelTaulaAlumne(modelComponentsVisuals.getModelTaulaJefe(), dadesJefe.getAll());
        } catch (DAOException ignored) {
        }

        //Fixem el model de la taula dels alumnes
        JTable taula = view.getTaula();
        taula.setModel(this.modelComponentsVisuals.getModelTaulaJefe());
        comboBoxModel();


    }

    private void setModelTaulaAlumne(DefaultTableModel modelTaulaAlumne, List<Jefe> all) {

        // Fill the table model with data from the collection
        for (Jefe estudiant : all) {
            modelTaulaAlumne.addRow(new Object[]{estudiant.getNomJefe(), estudiant.getVida(), estudiant.isDificultat(), estudiant.getNumAtacs(), estudiant.getLocalitzacio()});
        }
    }

    private void comboBoxModel() {
        JComboBox comboBox = view.getComboBox1();
        comboBox.setModel(this.modelComponentsVisuals.getComboBoxModel());
    }

    private void afegirListeners() {

        DefaultTableModel model = this.modelComponentsVisuals.getModelTaulaJefe();
        JTable taula = view.getTaula();
        JButton modificarButton = view.getModificarButton();
        JButton borrarButton = view.getBorrarButton();
        JTextField campNomJefe = view.getCampNomJefe();
        JTextField campVida = view.getCampVida();
        JTextField campNumAtacs = view.getCampNumAtacs();
        JCheckBox caixaDificultat = view.getCaixaDificultat();
        JTabbedPane pestanyes = view.getPestanyes();
        JComboBox comboBox = view.getComboBox1();

        //Botó insertar
        view.getInsertarButton().addActionListener(
                new ActionListener() {
                    /**
                     * Invoked when an action occurs.
                     *
                     * @param e the event to be processed
                     */
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int cont = 0;
                        if (pestanyes.getSelectedIndex() == 0) {
                            if (campNomJefe.getText().isBlank() || campVida.getText().isBlank() || campNumAtacs.getText().isBlank()) {
                                setExcepcio(new DAOException(5));
                            } else {

                                try {
                                    for (int i = 0; i < model.getRowCount(); i++) {
                                        if (campNomJefe.getText().trim().equals(model.getValueAt(i, 0))) {
                                            cont++;
                                            throw new SecurityException();
                                        }
                                    }
                                } catch (SecurityException ex) {
                                    setExcepcio(new DAOException(4));
                                }

                                if (cont == 0) {
                                    String regex1 = "^[a-zA-ZÀ-ÿ0-9\\s]+, [a-zA-ZÀ-ÿ0-9\\s]+$";


                                    if (!campNomJefe.getText().matches(regex1)) {
                                        setExcepcio(new DAOException(2));
                                    } else {
                                        try {
                                            if (compilarvida() < 1 || compilaratacs() < 1)
                                                throw new ParseException("", 0);
                                            model.addRow(new Object[]{campNomJefe.getText().trim(), compilarvida(), caixaDificultat.isSelected(), compilaratacs(), comboBox.getSelectedItem()});
                                            dadesJefe.insertar(new Jefe(campNomJefe.getText(), compilarvida(), caixaDificultat.isSelected(), compilaratacs(), Objects.requireNonNull(comboBox.getSelectedItem()).toString()));
                                            limpiar();
                                        } catch (ParseException ex) {
                                            setExcepcio(new DAOException(3));

                                        } catch (DAOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
        );
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int filaSel = taula.getSelectedRow();
                if (filaSel != -1) {
                    if (campNomJefe.getText().isBlank() || campVida.getText().isBlank()) {
                        setExcepcio(new DAOException(5));
                    } else {
                        try {
                            if (model.getValueAt(filaSel, 0).equals(campNomJefe.getText()) && model.getValueAt(filaSel, 1).equals(compilarvida()) && model.getValueAt(filaSel, 2).equals(caixaDificultat.isSelected()) && model.getValueAt(filaSel, 3).equals(compilaratacs()) && model.getValueAt(filaSel, 4).equals(comboBox.getSelectedItem())) {
                                setExcepcio(new DAOException(6));
                            } else {
                                String regex1 = "^[a-zA-ZÀ-ÿ0-9\\s]+, [a-zA-ZÀ-ÿ0-9\\s]+$";
                                if (!campNomJefe.getText().matches(regex1)) {
                                    setExcepcio(new DAOException(2));
                                } else {
                                    try {
                                        if (compilarvida() < 1 || compilaratacs() < 1) throw new ParseException("", 0);
                                        dadesJefe.borrar(new Jefe(model.getValueAt(filaSel, 0).toString(), (double) model.getValueAt(filaSel, 1), (boolean) model.getValueAt(filaSel, 2), (int) model.getValueAt(filaSel, 3), model.getValueAt(filaSel, 4).toString()));
                                        model.removeRow(filaSel);
                                        model.insertRow(filaSel, new Object[]{campNomJefe.getText(), compilarvida(), caixaDificultat.isSelected(), compilaratacs(), comboBox.getSelectedItem()});
                                        dadesJefe.insertar(new Jefe(campNomJefe.getText(), compilarvida(), caixaDificultat.isSelected(), compilaratacs(), Objects.requireNonNull(comboBox.getSelectedItem()).toString()));
                                        limpiar();
                                    } catch (ParseException ex) {
                                        setExcepcio(new DAOException(3));
                                    } catch (DAOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        } catch (ParseException e) {
                            setExcepcio(new DAOException(3));
                        }
                    }
                } else {
                    setExcepcio(new DAOException(7));
                }
            }
        });
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int filaSel = taula.getSelectedRow();
                if (filaSel != -1) {
                    try {
                        dadesJefe.borrar(new Jefe(model.getValueAt(filaSel, 0).toString(), (double) model.getValueAt(filaSel, 1), (boolean) model.getValueAt(filaSel, 2), (int) model.getValueAt(filaSel, 3), model.getValueAt(filaSel, 4).toString()));
                    } catch (DAOException e) {
                        throw new RuntimeException(e);
                    }
                    limpiar();
                    model.removeRow(filaSel);

                } else {
                    setExcepcio(new DAOException(8));
                }

            }
        });

        taula.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int filaSel = taula.getSelectedRow();
                if (filaSel != -1) {
                    campNomJefe.setText(model.getValueAt(filaSel, 0).toString());
                    campVida.setText(model.getValueAt(filaSel, 1).toString().replaceAll("\\.", ","));
                    caixaDificultat.setSelected((Boolean) model.getValueAt(filaSel, 2));
                    campNumAtacs.setText(model.getValueAt(filaSel, 3).toString());
                } else {
                    limpiar();
                }
            }
        });

    }

    public void limpiar() {
        JTextField campNomJefe = view.getCampNomJefe();
        JTextField campVida = view.getCampVida();
        JTextField campNumAtacs = view.getCampNumAtacs();
        JCheckBox caixaDificultat = view.getCaixaDificultat();
        campNomJefe.setText("");
        campVida.setText("");
        campNumAtacs.setText("");
        caixaDificultat.setSelected(false);

    }

    public double compilarvida() throws ParseException {
        JTextField campVida = view.getCampVida();
        NumberFormat num = NumberFormat.getNumberInstance(Locale.getDefault());
        return num.parse(campVida.getText().trim()).doubleValue();
    }

    public int compilaratacs() throws ParseException {
        JTextField campNumAtacs = view.getCampNumAtacs();
        NumberFormat num = NumberFormat.getNumberInstance(Locale.getDefault());
        return num.parse(campNumAtacs.getText()).intValue();
    }

    public static final String PROP_EXCEPCIO = "excepcio";
    private DAOException excepcio;

    public void setExcepcio(DAOException excepcio) {
        DAOException valorVell = this.excepcio;
        this.excepcio = excepcio;
        canvis.firePropertyChange(PROP_EXCEPCIO, valorVell, excepcio);
    }


    //3. Propietat PropertyChangesupport necessària per poder controlar les propietats lligades
    PropertyChangeSupport canvis = new PropertyChangeSupport(this);


    //4. Mètode on posarem el codi de tractament de les excepcions --> generat per la interfície PropertyChangeListener

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        DAOException rebuda = (DAOException) evt.getNewValue();

        try {
            throw rebuda;
        } catch (DAOException e) {
            //Aquí farem ele tractament de les excepcions de l'aplicació
            if (evt.getPropertyName().equals(PROP_EXCEPCIO)) {
                switch (rebuda.getTipo()) {
                    case 0:
                        JOptionPane.showMessageDialog(null, rebuda.getMessage());
                        System.exit(1);
                        break;
                    case 3, 5, 6, 7, 8:
                        JOptionPane.showMessageDialog(null, rebuda.getMessage());

                        break;
                    case 2, 4:
                        JOptionPane.showMessageDialog(null, rebuda.getMessage());
                        this.view.getCampNomJefe().setSelectionStart(0);
                        this.view.getCampNomJefe().requestFocus();
                        break;
                }
            }
        }
    }
}