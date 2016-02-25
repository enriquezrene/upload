package demo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import java.io.File;

/**
 * Created by rene on 25/02/16.
 */
public class Test {

//    public static void main(String[] args) throws Exception {
//        HttpClient httpclient = new DefaultHttpClient();
//        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
//
//        HttpPost httppost = new HttpPost("http://localhost:8080/upload");
//        File file = new File("/home/rene/Downloads/1.txt");
//
//        MultipartEntity mpEntity = new MultipartEntity();
//        ContentBody cbFile = new FileBody(file, "multipart/form-data");
//        mpEntity.addPart("file", cbFile);
//
//
//        httppost.setEntity(mpEntity);
//        System.out.println("executing request " + httppost.getRequestLine());
//        HttpResponse response = httpclient.execute(httppost);
//        HttpEntity resEntity = response.getEntity();
//
//        System.out.println(response.getStatusLine());
//        if (resEntity != null) {
//            System.out.println(EntityUtils.toString(resEntity));
//        }
//        if (resEntity != null) {
//            resEntity.consumeContent();
//        }
//
//        httpclient.getConnectionManager().shutdown();
//    }
}
