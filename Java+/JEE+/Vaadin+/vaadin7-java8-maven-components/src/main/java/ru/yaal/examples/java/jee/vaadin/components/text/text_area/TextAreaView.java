package ru.yaal.examples.java.jee.vaadin.components.text.text_area;

import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class TextAreaView extends VerticalLayout implements EmptyEnterView {
    public TextAreaView() {
        addComponent(editable());
        addComponent(readOnly());
        addComponent(large());
    }

    private TextArea large() {
        TextArea large = new TextArea("Large text");
        large.setValue("Лукоморье — самая свободная, правдивая и открытая для всех вики-энциклопедия интернет-личностей, современной культуры, фольклора, субкультур и интернета, а также всего остального. Это альтернатива, свежий взгляд если хотите, традиционного устоявшегося и классического стандартного представления об интернет-энциклопедии. Заходите почаще на Lukomore и читайте в своё удовольствие, узнавайте что-то новое, здесь Вам действительно всегда очень рады. Если есть желание, то можете поделиться своими знаниями и информацией с другими читателями и участниками проекта на страницах сайта — дописывайте и переписывайте существующие статьи, смело создавайте новые, делитесь своим мнением на страницах обсуждений статей и в комментариях. Лукоморье так же как и Википедия располагается в международной не коммерческой доменной зоне ORG. ");
        large.setRows(7);
        large.setColumns(50);
        return large;
    }

    private TextArea readOnly() {
        TextArea readOnly = new TextArea("A readonly area");
        readOnly.setValue("A row\nAnother row\nYet another row");
        readOnly.setReadOnly(true);
        return readOnly;
    }

    private TextArea editable() {
        TextArea editable = new TextArea("An editable area");
        editable.setValue("A row\nAnother row\nYet another row");
        return editable;
    }
}
