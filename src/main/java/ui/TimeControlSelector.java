package ui;

import domain.TimeControl;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JLabel;

public final class TimeControlSelector extends JComboBox<TimeControl> {

    public TimeControlSelector() {
        super(TimeControl.values());
        setSelectedItem(TimeControl.UNTIMED);

        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof TimeControl) {
                    label.setText(displayName((TimeControl) value));
                }

                return label;
            }
        });
    }

    public TimeControl getSelectedTimeControl() {
        return (TimeControl) getSelectedItem();
    }

    private static String displayName(TimeControl control) {
        switch (control) {
            case UNTIMED:
                return "Untimed";
            case BLITZ_3_MIN:
                return "Blitz (3 min)";
            case RAPID_10_MIN:
                return "Rapid (10 min)";
            case CLASSICAL_60_MIN:
                return "Classical (60 min)";
            default:
                throw new IllegalArgumentException("Unsupported time control: " + control);
        }
    }
}