package com.dickow.chortlin.dtupay.merchant;

import com.dickow.chortlin.dtupay.shared.Constants;
import com.dickow.chortlin.dtupay.shared.dto.PaymentDTO;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "merchant")
public class Merchant {
    private final HttpClient httpClient = HttpClient.newHttpClient();

    @PostMapping(value = "{merchant}/pay")
    public ResponseEntity pay(@PathVariable String merchant, @RequestBody PaymentDTO payment){
        try {
            var request = HttpRequest.newBuilder(new URI(Constants.DTUPAY_BASE_URL+merchant+"/"+payment.getToken()))
                    .POST(HttpRequest.BodyPublisher.fromString(String.valueOf(payment.getAmount())))
                    .build();
            httpClient.send(request, HttpResponse.BodyHandler.asString());
        }
        catch(Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "hello")
    public String hello(){
        return "Hello world";
    }
}
