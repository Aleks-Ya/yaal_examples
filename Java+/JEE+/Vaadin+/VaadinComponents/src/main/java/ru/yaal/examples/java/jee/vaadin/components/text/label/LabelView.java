package ru.yaal.examples.java.jee.vaadin.components.text.label;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class LabelView extends VerticalLayout implements EmptyEnterView {
    public LabelView() {
        Label shortLabel = new Label("A short label");

        Label multiLineLabel = new Label("A multiline label");
        multiLineLabel.setValue("Лукоморье — самая свободная, правдивая и открытая для всех вики-энциклопедия интернет-личностей, современной культуры, фольклора, субкультур и интернета, а также всего остального. Это альтернатива, свежий взгляд если хотите, традиционного устоявшегося и классического стандартного представления об интернет-энциклопедии. Заходите почаще на Lukomore и читайте в своё удовольствие, узнавайте что-то новое, здесь Вам действительно всегда очень рады. Если есть желание, то можете поделиться своими знаниями и информацией с другими читателями и участниками проекта на страницах сайта — дописывайте и переписывайте существующие статьи, смело создавайте новые, делитесь своим мнением на страницах обсуждений статей и в комментариях. Лукоморье так же как и Википедия располагается в международной не коммерческой доменной зоне ORG. ");

        addComponent(shortLabel);
        addComponent(multiLineLabel);
    }
}
