package org.example.view;

import org.example.model.entities.Jefe;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ModelComponentsVisuals {

    private final DefaultTableModel modelTaulaAlumne;
    public DefaultTableModel getModelTaulaJefe() {
        return modelTaulaAlumne;
    }

    private final ComboBoxModel<Jefe.Locacions> comboBoxModel;

    public ComboBoxModel<Jefe.Locacions> getComboBoxModel() {
        return comboBoxModel;
    }

    public ModelComponentsVisuals() {


        //Anem a definir l'estructura de la taula dels alumnes
        modelTaulaAlumne = new DefaultTableModel(new Object[]{"Nom Jefe", "Vida", "És Dificil?", "Nombre d'atacs", "localitzacio"}, 0) {
            /**
             * Returns true regardless of parameter values.
             *
             * @param row    the row whose value is to be queried
             * @param column the column whose value is to be queried
             * @return true
             * @see #setValueAt
             */
            @Override
            public boolean isCellEditable(int row, int column) {

                //Fem que TOTES les cel·les de la columna 1 de la taula es puguen editar
                //if(column==1) return true;
                return false;
            }


            //Permet definir el tipo de cada columna
            @Override
            public Class getColumnClass(int column) {
                return switch (column) {
                    case 0 -> String.class;
                    case 1 -> Double.class;
                    case 2 -> Boolean.class;
                    case 3 -> Integer.class;
                    default -> Object.class;
                };
            }
        };
        comboBoxModel = new DefaultComboBoxModel<>(Jefe.Locacions.values());
    }

}
