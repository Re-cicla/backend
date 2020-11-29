package es.recicla;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.cloud.automl.v1.*;
import com.sun.org.apache.xpath.internal.operations.Plus;
import es.recicla.model.Container;
import es.recicla.model.Predict;
import es.recicla.service.ContainerService;
import io.grpc.netty.shaded.io.netty.handler.codec.base64.Base64Decoder;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@RestController
public class ReciclaApplication {

    private static final String eduPass
            = "ya29.c.Kp0B5wcH26w8KR34OOtSGoSpa__y9oV4IsWbiIJO_oNtQysLzH8RpX9LcYhvyeAPyDJirBR9lYRw4dNskYKWeM4OWNU7ceWhrT5BU96cAiYKpqjnMGRDBwGK1DsAECi4JCeOTl8dNrnUuqm72DcJpl9c87UlGBl51M0RrPOVzUfvP-uwJS9c5VcPIbAFr9d8KNgv8GABFmjxeBvstICkhg";
    private static final String predictUrl
            = "https://automl.googleapis.com/v1beta1/projects/164678665589/locations/us-central1/models/ICN7600679791234121728:predict";
    private static final Logger LOGGER=LoggerFactory.getLogger(ResponseEntity.class);

    @Autowired
    private ContainerService containerService;

    @CrossOrigin
    @PostMapping("/predict")
    public String predict(@RequestBody Predict predict){
        String base64Image = predict.getImage();
        Character separator = ',';
        String response = "{\"payload\": [{\"annotationSpecId\": \"5737459481432817664\",\"classification\": {\"score\": 0.8103025},\"displayName\": \"R\"}]}";
        if(base64Image != null && base64Image.indexOf(separator.toString()) > 0){
            URL url = null;
            String imageReduced = base64Image.substring(base64Image.indexOf(separator.toString())+1);

            String projectId = "re-ciclo-hackaton";
            String modelId = "ICN7600679791234121728";
            // Initialize client that will be used to send requests. This client only needs to be created
            // once, and can be reused for multiple requests. After completing all of your requests, call
            // the "close" method on the client to safely clean up any remaining background resources.
            try (PredictionServiceClient client = PredictionServiceClient.create()) {
                // Get the full path of the model.
                ModelName name = ModelName.of(projectId, "us-central1", modelId);
                Image image = Image.newBuilder().setImageBytes( imageReduced).build();
                ExamplePayload payload = ExamplePayload.newBuilder().setImage(image).build();
                PredictRequest predictRequest =
                        PredictRequest.newBuilder()
                                .setName(name.toString())
                                .setPayload(payload)
                                .putParams(
                                        "score_threshold", "0.8") // [0.0-1.0] Only produce results higher than this value
                                .build();

                PredictResponse response2 = client.predict(predictRequest);

                for (AnnotationPayload annotationPayload : response2.getPayloadList()) {
                    System.out.format("Predicted class name: %s\n", annotationPayload.getDisplayName());
                    System.out.format(
                            "Predicted class score: %.2f\n", annotationPayload.getClassification().getScore());
                }
            }

             /*
            try {
               HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
                JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

                GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream("MyProject-1234.json"))
                        .createScoped(Collections.singleton(PlusScopes.PLUS_ME));

                plus = new Plus.Builder(httpTransport, jsonFactory, credential)
                        .setApplicationName(APPLICATION_NAME).build();

                url = new URL(this.predictUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Authorization","Bearer "+this.eduPass);
                conn.setRequestProperty("Content-Type","application/json");
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                JSONObject imageBytes = new JSONObject();
                JSONObject image   = new JSONObject();
                JSONObject payload = new JSONObject();

                imageBytes.put("imageBytes",imageReduced);
                image.put("image", imageBytes.toString());
                payload.put("payload", image.toString());

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(payload.toString());
                wr.flush();

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String output;

                StringBuffer responseBuffer = new StringBuffer();
                while ((output = in.readLine()) != null) {
                    responseBuffer.append(output);
                }

                in.close();
                response = responseBuffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            */
        }
        return response;
    }

    @CrossOrigin
    @GetMapping("/containers/{type}")
    public ResponseEntity<Container> container(@PathVariable String type){
        LOGGER.info("REQUESTED");
        List<Container> containers = containerService.listByContainerType(type);
        return new ResponseEntity(containers, HttpStatus.OK);
    }

    public static void main(String[] args) {
        SpringApplication.run(ReciclaApplication.class, args);
    }

}
