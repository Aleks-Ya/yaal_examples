package controller.multipart;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class CommonsMultipartController {
    static final String ENDPOINT = "/multipart";

    @ResponseBody
    @RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
    public String post(@RequestBody BodyJson bodyJson) {
        return "response=" +bodyJson.getName();
    }

    static class BodyJson {
        private String name;

        String getName() {
            return name;
        }

        @SuppressWarnings("unused")
        void setName(String name) {
            this.name = name;
        }
    }

}
