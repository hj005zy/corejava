package corejava.voumeII.chapter05.retire;

import java.awt.Component;
import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListDataListener;

/**
 * This combo box lets a user pick a locale. The locales are displayed in the locale of the combo
 * box, and sorted according to the collator of the display locale.
 * @version 1.00 2004-09-15
 * @author Cay Horstmann
 */
public class LocaleCombo extends JComboBox<Locale> {

    private static final long serialVersionUID = 100775763628024038L;

    private int selected;

    private Locale[] locales;

    private ListCellRenderer<Locale> listCellRenderer;

    /**
     * Constructs a locale combo that displays an immutable collection of locales.
     * @param locales the locales to display in this combo box
     */
    public LocaleCombo(Locale[] locales) {

        this.locales = locales.clone();
        sort();
        setSelectedItem(getLocale());
    }

    @Override
    public void setLocale(Locale newValue) {

        super.setLocale(newValue);
        sort();
    }

    private void sort() {

        final Locale loc = getLocale();
        final Collator collator = Collator.getInstance(loc);
        final Comparator<Locale> comp = new Comparator<Locale>() {

            @Override
            public int compare(Locale a, Locale b) {

                return collator.compare(a.getDisplayName(loc), b.getDisplayName(loc));
            }
        };
        Arrays.sort(locales, comp);
        setModel(new ComboBoxModel<Locale>() {

            @SuppressWarnings("synthetic-access")
            @Override
            public Locale getElementAt(int i) {

                return locales[i];
            }

            @SuppressWarnings("synthetic-access")
            @Override
            public int getSize() {

                return locales.length;
            }

            @Override
            public void addListDataListener(ListDataListener l) {

            }

            @Override
            public void removeListDataListener(ListDataListener l) {

            }

            @SuppressWarnings("synthetic-access")
            @Override
            public Locale getSelectedItem() {

                return selected >= 0 ? locales[selected] : null;
            }

            @SuppressWarnings("synthetic-access")
            @Override
            public void setSelectedItem(Object anItem) {

                if (anItem == null) {
                    selected = -1;
                } else {
                    selected = Arrays.binarySearch(locales, (Locale) anItem, comp);
                }
            }
        });
        setSelectedItem(selected);
    }

    @Override
    public ListCellRenderer<Locale> getRenderer() {

        if (listCellRenderer == null) {
            @SuppressWarnings("unchecked")
            final ListCellRenderer<Object> originalRenderer = (ListCellRenderer<Object>) super.getRenderer();
            if (originalRenderer == null) {
                return null;
            }
            listCellRenderer = new ListCellRenderer<Locale>() {

                @Override
                public Component getListCellRendererComponent(JList<? extends Locale> list, Locale value, int index,
                        boolean isSelected, boolean cellHasFocus) {

                    String renderedValue = value.getDisplayName(getLocale());
                    return originalRenderer.getListCellRendererComponent(list, renderedValue, index, isSelected,
                            cellHasFocus);
                }
            };
        }
        return listCellRenderer;
    }

    @Override
    public void setRenderer(ListCellRenderer<? super Locale> newValue) {

        listCellRenderer = null;
        super.setRenderer(newValue);
    }
}
