package owl.read;

import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.QualifierValue;

import java.util.List;

import static org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag.TAG_PROPERTY_VALUE;

public class TermFrameFacade {

    public String getClassId(Frame frame) {
        return frame.getId();
    }

    public List<String> getAnnotationValues(Frame term, String tag) {
        return term.getClauses(TAG_PROPERTY_VALUE).stream()
                .filter(clause -> tag.equals(clause.getValue()))
                .map(Clause::getValue2)
                .map(Object::toString)
                .toList();
    }

    public List<String> getAllNestedAnnotationValues(Frame term, String tag) {
        return term.getClauses(TAG_PROPERTY_VALUE).stream()
                .filter(clause -> tag.equals(clause.getValue()))
                .flatMap(clause -> clause.getQualifierValues().stream())
                .map(QualifierValue::getValue)
                .map(Object::toString)
                .toList();
    }

    public List<AnnotationValue> getOuterAndNestedAnnotationValues(Frame term, String tag, String qualifier) {
        return term.getClauses(TAG_PROPERTY_VALUE).stream()
                .filter(clause -> tag.equals(clause.getValue()))
                .map(clause -> {
                    var value = clause.getValue2().toString();
                    var nested = clause.getQualifierValues().stream()
                            .filter(q -> qualifier.equals(q.getQualifier()))
                            .map(QualifierValue::getValue)
                            .map(Object::toString)
                            .toList();
                    return new AnnotationValue(value, nested);
                })
                .toList();
    }

    public record AnnotationValue(String annotationValue, List<String> nestedAnnotationValues) {
    }
}
