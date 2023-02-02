package controller.multipart.commons;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
class CommonsMultipartController {
    static final String ENDPOINT = "/multipart";
    static final String DATA_PART_NAME = "data";
    static final String JSON_DATA_PART_NAME = "json-data";
    static final String PARAM_NAME = "a_param";

    @ResponseBody
    @RequestMapping(path = ENDPOINT, method = RequestMethod.POST)
    public String post(
            @RequestPart(JSON_DATA_PART_NAME) JsonPojo pojo,
            @RequestParam(PARAM_NAME) String param,
            @RequestParam(value = DATA_PART_NAME) List<MultipartFile> files) {

        var multipartStr = files.stream().map(file -> {
            try {
                return new String(file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }).collect(Collectors.joining());

        return multipartStr + pojo.getName() + param;
    }

    static class JsonPojo {
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
