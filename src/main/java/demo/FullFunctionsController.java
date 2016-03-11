package demo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by rene on 25/02/16.
 */
@RestController
@RequestMapping(value = "/odra")
public class FullFunctionsController {

    public static final String TOKEN = "123456789";


    @Autowired
    private GridFsTemplate gridFsTemplate;

//    @RequestMapping(method = RequestMethod.POST, value = "/upload")
//    public ResponseEntity<String> handleFileUploads(HttpServletRequest request, @RequestParam("lat") String lat,
//                                                    @RequestParam("long") String lon,
//                                                    @RequestParam("key") MultipartFile file) {
//
//        System.out.println(request.getHeader("Authorization"));
//        System.out.println(request.getHeader("common"));
//        System.out.println(lat);
//        System.out.println(lon);
//        String response = greetingController.handleFileUploads(lat, lon, file);
//        return new ResponseEntity<String>(response, HttpStatus.CREATED);
//    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    String handleFileUpload(@RequestParam("lat") String lat,
                            @RequestParam("long") String lon,
            @RequestParam("key") MultipartFile file) {
        System.out.println(lat);
        System.out.println(lon);
        String name = "test11";
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(name + "-uploaded")));
                stream.write(bytes);
                stream.close();
                System.out.println("You successfully uploaded " + name + " into " + name + "-uploaded !");
                return "You successfully uploaded " + name + " into " + name + "-uploaded !";
            } catch (Exception e) {
                System.out.println("You failed to upload " + name + " => " + e.getMessage());
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            System.out.println("You failed to upload " + name + " because the file was empty.");
            return "You failed to upload " + name + " because the file was empty.";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/uploadImage")
    public
    @ResponseBody
    String handleFileUploads(@RequestParam("lat") String lat,
                             @RequestParam("long") String lon,
                             @RequestParam("key") MultipartFile file) {


        System.out.println(lat);
        System.out.println(lon);
        System.out.println(file);
        if (!file.isEmpty()) {
            try {

                DBObject metaData = new BasicDBObject();
                metaData.put("coordinates-lat", lat);
                metaData.put("coordinates-long", lon);


                gridFsTemplate.store(file.getInputStream(), "image/jpeg", metaData);

                System.out.println("OK");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("ERROR");
        }

        System.out.println(file);
        return "redirect:upload";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        System.out.println(user);
        if (user.getEmail().equals(user.getPassword())) {
            return new ResponseEntity<String>(TOKEN, HttpStatus.OK);
        }
        throw new IllegalArgumentException("Invalid credentials");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        System.out.println(user);
        if (user.getEmail().startsWith("1")) {
            throw new IllegalArgumentException("Correo no valido");
        } else if (user.getPassword().startsWith("1")) {
            throw new IllegalArgumentException("Pwd no valido");
        } else if (user.getName().startsWith("1")) {
            throw new IllegalArgumentException("Invalid name");
        }
        return new ResponseEntity<String>(TOKEN, HttpStatus.CREATED);
    }
}
