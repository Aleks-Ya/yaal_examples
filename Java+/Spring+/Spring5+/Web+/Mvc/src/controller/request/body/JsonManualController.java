package controller.request.body;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
class JsonManualController {
    static final String ENDPOINT = "/json";

    private static final ObjectMapper mapper = new ObjectMapper();

    @ResponseBody
    @RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
    public String post(@RequestBody String bodyJson) throws IOException {
        BodyJson bodyJsonObj = mapper.readValue(bodyJson.getBytes(), BodyJson.class);
        return "response=" + bodyJsonObj.getName();
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
