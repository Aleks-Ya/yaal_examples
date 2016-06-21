package Container.JComponent.JTextComponent.JTextArea;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class EnabledDisabled {
    public static void main(String[] args) {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setEnabled(false);
        area.setFocusable(false);
        area.setText("Происхождение Александра Сергеевича Пушкина идёт от разветвлённого нетитулованного дворянского " +
                "рода Пушкиных, восходившего по генеалогической легенде к «мужу честну» Ратше, современнику " +
                "Александра Невского. Пушкин неоднократно писал о своей родословной в стихах и прозе; он видел в своих " +
                "предках образец древнего рода, истинной «аристократии», честно служившего отечеству, но не " +
                "снискавшего благосклонности правителей и «гонимого». Не раз он обращался (в том числе в " +
                "художественной форме) и к образу своего прадеда по матери — африканца Абрама Петровича Ганнибала, " +
                "ставшего слугой и воспитанником Петра I, а потом военным инженером и генералом.");
        area.setLineWrap(true);

        JFrame frame = new JFrame();
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(area);
    }
}
