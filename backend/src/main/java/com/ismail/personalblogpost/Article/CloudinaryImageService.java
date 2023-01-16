package com.ismail.personalblogpost.Article;

import com.cloudinary.Util;
import com.ismail.personalblogpost.DtoWrapper;
import com.ismail.personalblogpost.exception.APIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.ismail.personalblogpost.DtoWrapper.*;

@Service
public class CloudinaryImageService {

    @Value("${spring.cloudinary.cloud_name}")
    private String cloudName ;
    @Value("${spring.cloudinary.api_key}")
    private String apiKey ;
    @Value("${spring.cloudinary.api_secret}")
    private String apiSecret ;
    @Value("${spring.cloudinary.upload_preset}")
    private String uploadPreset ;

    public  String generateExpectedToken(String version , String public_id ) {
        Map<String,Object> signatureProps = Map.of(
                "public_id",public_id ,
                "version" ,version
        );
        return  Util.produceSignature(signatureProps,apiSecret) ;
    }
    public CloudinarySignature produceSignature() {
        // By Default Expired After timestamp + 1 hour (in seconds)
        // we can decrease or increase by adding to removing seconds in timestamp so result after adding 1 hour could be diffrent
        var timeToDecreaseStampInSeconds =  5 * 60  ;
        var timestamp =    ( new Date().getTime() /1000  ) - timeToDecreaseStampInSeconds ;
        Map<String, Object> params = new HashMap<>();
        params.put("timestamp", timestamp);
        params.put("upload_preset",uploadPreset) ;
        final String signature = Util.produceSignature(params, apiSecret);
//        return Map.of("signature", signature, "timestamp", timestamp);
        return new CloudinarySignature(signature,timestamp) ;
    }
    public void validate(String version, String public_id, String actualSignature) {

        String expectedSignature = generateExpectedToken(version, public_id);
        if (!expectedSignature.equals(actualSignature))
            throw new APIException("invalid signature", HttpStatus.NOT_ACCEPTABLE);
    }

}
