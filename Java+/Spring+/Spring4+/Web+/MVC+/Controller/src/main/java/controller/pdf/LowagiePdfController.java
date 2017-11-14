package controller.pdf;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path = LowagiePdfController.ENDPOINT, produces = MediaType.APPLICATION_PDF_VALUE)
class LowagiePdfController extends AbstractController {
    static final String ENDPOINT = "/pdf";

//    @RequestMapping("/requestParam")
//    public void missingServletRequestParameterException(
//            @RequestParam("abc") String param
//    ) {
//    }
//
//
//    @ResponseBody
//    @RequestMapping("/model")
//    public String helloWorld(Model model) {
//        model.addAttribute("message", "Hello World!");
//        return "helloWorld";
//    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {
        //user data
//        Map<String,String> userData = new HashMap<>();
//        userData.put("1", "Mahesh");
//        userData.put("2", "Suresh");
//        userData.put("3", "Ramesh");
//        userData.put("4", "Naresh");
//        return new ModelAndView("UserSummary","userData",userData);


        return new ModelAndView(new UserPDFView());
    }

}
