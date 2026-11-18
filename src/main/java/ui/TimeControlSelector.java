package ui;

import domain.TimeControl;
import domain.locale.I18n;

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
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

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
                return I18n.get().t("welcome.timeControlUntimed");
            case BLITZ_3_MIN:
                return I18n.get().t("welcome.timeControlBlitz");
            case RAPID_10_MIN:
                return I18n.get().t("welcome.timeControlRapid");
            case CLASSICAL_60_MIN:
                return I18n.get().t("welcome.timeControlClassical");
            default:
                throw new IllegalArgumentException("Unsupported time control: " + control);
        }
    }
}