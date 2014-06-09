package corejava.voumeII.chapter05.collation;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * This program demonstrates collating strings under various locales.
 * @version 1.14 2012-01-26
 * @author Cay Horstmann
 */
public class CollationTest {

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {

                JFrame frame = new CollationFrame();
                frame.setTitle("CollationTest");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

/**
 * This frame contains combo boxes to pick a locale, collation strength and decomposition rules, a
 * text field and button to add new strings, and a text area to list the collated strings.
 */
class CollationFrame extends JFrame {

    private static final long serialVersionUID = 8500864779309187928L;

    private Collator collator = Collator.getInstance(Locale.getDefault());

    private List<String> strings = new ArrayList<>();

    private Collator currentCollator;

    private Locale[] locales;

    private JComboBox<String> localeCombo = new JComboBox<>();

    private JTextField newWord = new JTextField(20);

    private JTextArea sortedWords = new JTextArea(20, 20);

    private JButton addButton = new JButton("Add");

    private EnumCombo strengthCombo = new EnumCombo(Collator.class, "Primary", "Secondary", "Tertiary", "Identical");

    private EnumCombo decompositionCombo = new EnumCombo(Collator.class, "Canonical Decomposition",
            "Full Decomposition", "No Decomposition");

    public CollationFrame() {

        setLayout(new GridBagLayout());
        add(new JLabel("Locale"), new GBC(0, 0).setAnchor(GridBagConstraints.EAST));
        add(new JLabel("Strength"), new GBC(0, 1).setAnchor(GridBagConstraints.EAST));
        add(new JLabel("Decomposition"), new GBC(0, 2).setAnchor(GridBagConstraints.EAST));
        add(addButton, new GBC(0, 3).setAnchor(GridBagConstraints.EAST));
        add(localeCombo, new GBC(1, 0).setAnchor(GridBagConstraints.WEST));
        add(strengthCombo, new GBC(1, 1).setAnchor(GridBagConstraints.WEST));
        add(decompositionCombo, new GBC(1, 2).setAnchor(GridBagConstraints.WEST));
        add(newWord, new GBC(1, 3).setFill(GridBagConstraints.HORIZONTAL));
        add(new JScrollPane(sortedWords), new GBC(0, 4, 2, 1).setFill(GridBagConstraints.BOTH));

        locales = Collator.getAvailableLocales().clone();
        Arrays.sort(locales, new Comparator<Locale>() {

            @SuppressWarnings("synthetic-access")
            @Override
            public int compare(Locale l1, Locale l2) {

                return collator.compare(l1.getDisplayName(), l2.getDisplayName());
            }
        });
        for (Locale loc : locales) {
            localeCombo.addItem(loc.getDisplayName());
        }
        localeCombo.setSelectedItem(Locale.getDefault().getDisplayName());

        strings.add("America");
        strings.add("able");
        strings.add("Zulu");
        strings.add("zebra");
        strings.add("\u00C5ngstr\u00F6m");
        strings.add("A\u030angstro\u0308m");
        strings.add("Angstrom");
        strings.add("Able");
        strings.add("office");
        strings.add("o\uFB03ce");
        strings.add("Java\u2122");
        strings.add("JavaTM");
        updateDisplay();

        addButton.addActionListener(new ActionListener() {

            @SuppressWarnings("synthetic-access")
            @Override
            public void actionPerformed(ActionEvent event) {

                strings.add(newWord.getText());
                updateDisplay();
            }
        });

        ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {

                updateDisplay();
            }
        };

        localeCombo.addActionListener(listener);
        strengthCombo.addActionListener(listener);
        decompositionCombo.addActionListener(listener);
        pack();
    }

    /**
     * Updates the display and collates the strings according to the user settings.
     */
    public void updateDisplay() {

        Locale currentLocale = locales[localeCombo.getSelectedIndex()];
        localeCombo.setLocale(currentLocale);

        currentCollator = Collator.getInstance(currentLocale);
        currentCollator.setStrength(strengthCombo.getValue());
        currentCollator.setDecomposition(decompositionCombo.getValue());

        Collections.sort(strings, currentCollator);

        sortedWords.setText("");
        for (int i = 0; i < strings.size(); i++) {
            String s = strings.get(i);
            if (i > 0 && currentCollator.compare(s, strings.get(i - 1)) == 0) {
                sortedWords.append("= ");
            }
            sortedWords.append(s + "\n");
        }
        pack();
    }
}
