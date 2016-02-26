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
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();


    @Autowired
    private GridFsTemplate gridFsTemplate;


    @RequestMapping("/photo3")
    public ResponseEntity<byte[]> testphotoConLatLon(@RequestParam("lat") String lat,
                                                     @RequestParam("long") String lon) throws IOException {

//        metaData.put("coordinates-lat", lat);
//        metaData.put("coordinates-long", lon);
        List<GridFSDBFile> files = gridFsTemplate.find(Query.query(Criteria.where("metadata.coordinates-lat").is(lat).and("coordinates-long").is(lon)));


        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<byte[]>(IOUtils.toByteArray(files.get(files.size() - 1).getInputStream()), headers, HttpStatus.OK);
    }


    @RequestMapping("/photo2")
    public ResponseEntity<byte[]> testphoto() throws IOException {

        List<GridFSDBFile> files = gridFsTemplate.find(Query.query(Criteria.where("metadata.coordinates-id").is("3")));


        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<byte[]>(IOUtils.toByteArray(files.iterator().next().getInputStream()), headers, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/uploadImage")
    public String handleFileUploads(@RequestParam("lat") String lat,
                                    @RequestParam("long") String lon,
                                    @RequestParam("key") MultipartFile file) {


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


//    @RequestMapping(value="/uploadImage",method = RequestMethod.POST)
//    public @ResponseBody String uploadImage(@RequestBody String encodedImage)
//    {
//        System.out.println(encodedImage);
//        return null;
//    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    String handleFileUpload(
            @RequestParam("file") MultipartFile file) {
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


    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping("/hola")
    public String sayHello() {
        return "hola";
    }
}
