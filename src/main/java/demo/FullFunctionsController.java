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
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by rene on 25/02/16.
 */
@RestController
@RequestMapping(value = "/odra")
public class FullFunctionsController {

    public static final String TOKEN = "TLf3%2$kYteyG-@6UGfUy!2";

    @Autowired
    private GreetingController greetingController;

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public ResponseEntity<String> handleFileUploads(HttpServletRequest request, @RequestParam("lat") String lat,
                                                    @RequestParam("long") String lon,
                                                    @RequestParam("key") MultipartFile file) {

        System.out.println(request.getHeader("Authorization"));
        System.out.println(request.getHeader("common"));
        System.out.println(lat);
        System.out.println(lon);
        String response = greetingController.handleFileUploads(lat, lon, file);
        return new ResponseEntity<String>(response, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<String> login(@RequestBody User user){
        System.out.println(user);
        if(user.getEmail().equals(user.getPassword()) ){
            return new ResponseEntity<String>(TOKEN, HttpStatus.OK);
        }
        throw  new IllegalArgumentException("Invalid credentials");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public ResponseEntity<String> signup(User user){
        System.out.println(user);
        return new ResponseEntity<String>(TOKEN, HttpStatus.CREATED);
    }
}
