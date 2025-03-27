package com.chargepoint.authorization.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.chargepoint.authorization.domain.AuthorizationRequest;
import com.chargepoint.authorization.domain.AuthorizationResponse;
import com.chargepoint.authorization.enums.AuthorizationStatus;
import com.chargepoint.authorization.service.AuthorizationServiceImpl;


@Component
public class AuthorizationConsumer {

	private final KafkaTemplate<String, AuthorizationResponse> kafkaTemplate;
    private final AuthorizationServiceImpl authorizationService;

    @Autowired
    public AuthorizationConsumer(KafkaTemplate<String, AuthorizationResponse> kafkaTemplate,
                         AuthorizationServiceImpl authorizationService) {
        this.kafkaTemplate = kafkaTemplate;
        this.authorizationService = authorizationService;
    }
    


     @KafkaListener(topics = "authorization-requests", groupId = "auth-group")
     public void processAuthorization(@Payload AuthorizationRequest request, @Header(KafkaHeaders.RECEIVED_KEY) String key) {
        String identifier = request.getDriverIdentifier().getId();
        AuthorizationStatus status = authorizationService.determineAuthorizationStatus(identifier);
       
        AuthorizationResponse response = new AuthorizationResponse(status);
        
        kafkaTemplate.send("authorization-responses", key, response);
    }
}	
