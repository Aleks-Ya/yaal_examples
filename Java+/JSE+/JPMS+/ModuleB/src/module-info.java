module Java.JSE.JPMS.ModuleB.main {
    requires Java.JSE.JPMS.ModuleA.main;
    requires flexmark;
    requires flexmark.util.ast;
    requires flexmark.util.format;
    requires flexmark.util.builder;
    requires flexmark.util.dependency;
    requires flexmark.util.html;
    requires flexmark.util.sequence;
    requires flexmark.util.collection;
    requires flexmark.util.data;
    requires flexmark.util.misc;
    requires flexmark.util.visitor;
}