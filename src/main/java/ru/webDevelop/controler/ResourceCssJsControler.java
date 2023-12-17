package ru.webDevelop.controler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Controller
public class ResourceCssJsControler {
    @GetMapping("/styles/css/{code}.css")
    @ResponseBody
    public ResponseEntity<String> styles(@PathVariable("code") String code) {
        ResponseEntity<String> resporence = null;
        try(InputStream inputStream =
                    getClass().getClassLoader().getResourceAsStream("static/css/"+ code + ".css");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null ) {
                stringBuilder.append(line+"\n");
            }
            final HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", "text/css; charset=utf-8");
            resporence = new ResponseEntity<String>(stringBuilder.toString(), httpHeaders, HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
        }
     return resporence;
    }
}
