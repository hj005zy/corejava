package corejava.voumeII.chapter05.collation;

import java.util.Map;
import java.util.TreeMap;

import javax.swing.JComboBox;

/**
   A combo box that lets users choose from among static field
   values whose names are given in the constructor.
   @version 1.14 2012-01-26
   @author Cay Horstmann
*/
public class EnumCombo extends JComboBox<String> {

    private static final long serialVersionUID = 1339629290964180886L;

    private Map<String, Integer> table = new TreeMap<>();

    /**
       Constructs an EnumCombo.
       @param cl a class
       @param labels an array of static field names of cl
    */
    public EnumCombo(Class<?> cl, String... labels) {

        for (String label : labels) {
            String name = label.toUpperCase().replace(' ', '_');
            int value = 0;
            try {
                java.lang.reflect.Field f = cl.getField(name);
                value = f.getInt(cl);
            } catch (Exception e) {
                label = "(" + label + ")";
            }
            table.put(label, value);
            addItem(label);
        }
        setSelectedItem(labels[0]);
    }

    /**
       Returns the value of the field that the user selected.
       @return the static field value
    */
    public int getValue() {

        return table.get(getSelectedItem());
    }
}
