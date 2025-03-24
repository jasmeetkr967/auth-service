package com.chargepoint.authorization.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chargepoint.authorization.db.domain.DriverAuthorization;
import com.chargepoint.authorization.enums.AuthorizationStatus;
import com.chargepoint.authorization.repo.DriverAuthorizationRepository;

@Service
public class AuthorizationServiceImpl{
	
	
	@Autowired
    private DriverAuthorizationRepository driverAuthorizationRepository;

    public AuthorizationStatus determineAuthorizationStatus(String identifier) {
        if (identifier.length() < 20 || identifier.length() > 80) {
            return AuthorizationStatus.INVALID;
        }
       Optional<DriverAuthorization> result= driverAuthorizationRepository.findByIdentifier(identifier);
         
        if(result.isPresent()) {
          return result.get().isAllowed()?
        		  AuthorizationStatus.ACCEPTED:AuthorizationStatus.REJECTED;
         }else {
        	 return AuthorizationStatus.UNKNOWN;
         }
    }

}
