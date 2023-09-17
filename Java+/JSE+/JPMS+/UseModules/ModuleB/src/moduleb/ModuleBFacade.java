package moduleb;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import modulea.ModuleAFacade;

public class ModuleBFacade {
    public static void main(String[] args) {
        useClassesFromModuleA();
        useClassesFromFlexmarkLibrary();
    }

    private static void useClassesFromFlexmarkLibrary() {
        var parser = Parser.builder().build();
        var document = parser.parse("# Chapter 1: *Beginning*");
        assert document.getLineCount() == 1;
        var renderer = HtmlRenderer.builder().build();
        var html = renderer.render(document);
        assert html.equals("<h1>Chapter 1: <em>Beginning</em></h1>\n");
    }

    private static void useClassesFromModuleA() {
        var s = ModuleAFacade.getString();
        assert "ModuleAStr".equals(s);
    }
}
