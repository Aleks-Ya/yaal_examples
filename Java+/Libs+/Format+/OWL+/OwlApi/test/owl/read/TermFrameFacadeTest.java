package owl.read;

import org.junit.jupiter.api.Test;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatParser;
import util.ResourceUtil;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TermFrameFacadeTest {
    private static final String STUDENT_ID = "http://example.com/Student";
    private static final String TEACHER_ID = "http://example.com/Teacher";
    private static final String ADMISSION_YEAR_TAG = "admissionYear";
    private static final String EMPLOYMENT_YEAR_TAG = "employmentYear";
    private static final String ACCEPT_APPROVED_TAG = "acceptApproved";
    private final TermFrameFacade facade = new TermFrameFacade();

    @Test
    void getId() throws IOException {
        var frame = getStudentFrame();
        var id = facade.getClassId(frame);
        assertThat(id).isEqualTo(STUDENT_ID);
    }

    @Test
    void getAnnotationValues() throws IOException {
        var frame = getStudentFrame();
        var values = facade.getAnnotationValues(frame, ADMISSION_YEAR_TAG);
        assertThat(values).containsExactlyInAnyOrder("2015", "2021");
    }

    @Test
    void getAllNestedAnnotationValues() throws IOException {
        var frame = getTeacherFrame();
        var values = facade.getAllNestedAnnotationValues(frame, EMPLOYMENT_YEAR_TAG);
        assertThat(values).containsExactlyInAnyOrder("the office", "the president", "Mark", "Rick");
    }

    @Test
    void getOuterAndNestedAnnotationValues() throws IOException {
        var frame = getTeacherFrame();
        var values = facade.getOuterAndNestedAnnotationValues(frame, EMPLOYMENT_YEAR_TAG, ACCEPT_APPROVED_TAG);
        assertThat(values).containsExactlyInAnyOrder(
                new TermFrameFacade.AnnotationValue("2005", List.of("the office", "the president")));
    }

    private static OBODoc getOboDoc() throws IOException {
        var parser = new OBOFormatParser();
        var oboReader = new InputStreamReader(ResourceUtil.resourceToInputStream("owl/read/TermFrameFacadeTest.obo"));
        return parser.parse(oboReader);
    }

    private static Frame getStudentFrame() throws IOException {
        return getOboDoc().getTermFrame(STUDENT_ID);
    }

    private static Frame getTeacherFrame() throws IOException {
        return getOboDoc().getTermFrame(TEACHER_ID);
    }

}