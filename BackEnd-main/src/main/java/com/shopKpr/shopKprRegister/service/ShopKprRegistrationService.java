package com.shopKpr.shopKprRegister.service;

import com.shopKpr.exceptions.AlreadyExists;
import com.shopKpr.exceptions.MobileNumberMisMatch;
import com.shopKpr.shopKprRegister.model.ShopKpr;
import com.shopKpr.shopKprRegister.model.ShopKprUser;
import com.shopKpr.security.model.*;
import com.shopKpr.shopKprRegister.model.UserAuthenticate;
import com.shopKpr.shopKprRegister.repository.elasticSearchRepo.ShopKprRegElasticRepo;
import com.shopKpr.shopKprRegister.repository.jpaRepo.ShopKprRegistrationRepository;
import com.shopKpr.security.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Optional;

import static com.shopKpr.security.constants.ApplicationUserRole.ROLE_SHOPKPR;

@Service
@Slf4j
public class ShopKprRegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final ShopKprRegElasticRepo shopKprRegElasticRepo;
    private final ShopKprRegistrationRepository shopKprRegistrationRepository;

    public ShopKprRegistrationService( PasswordEncoder passwordEncoder,
                                       AuthenticationManager authenticationManager, JwtUtils jwtUtils, ShopKprRegElasticRepo shopKprRegElasticRepo, ShopKprRegistrationRepository shopKprRegistrationRepository ) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.shopKprRegElasticRepo = shopKprRegElasticRepo;
        this.shopKprRegistrationRepository = shopKprRegistrationRepository;
    }


    @Transactional
    public void registerNewUser( ShopKpr shopKpr ) {

        Optional<ShopKpr> shopKprOptional =
                shopKprRegistrationRepository.findByMobileNumber(shopKpr.getMobileNumber());

        if (shopKprOptional.isPresent()) {
            log.info("User with mobile number: " + shopKpr.getMobileNumber() + " already exists");
            throw new AlreadyExists("User already exists");
        } else {
            shopKpr.setEnabled(true);
            shopKpr.setPin(passwordEncoder.encode(shopKpr.getPin()));
            shopKpr.setUserCreationDate(new Date(System.currentTimeMillis()));

            Roles roles = new Roles();
            roles.setRole(ROLE_SHOPKPR.name());
            roles.setShopKpr(shopKpr);

            shopKpr.addNewRole(roles);

            ShopKprUser shopKprUser = new ShopKprUser();
            shopKprUser.setMobileNumber(shopKpr.getMobileNumber());
            shopKprUser.setFullName(shopKpr.getFullName());
            shopKprUser.setImageUrl(shopKpr.getImageUrl());
            shopKprUser.setEnabled(shopKpr.isEnabled());
            shopKprUser.setGender(shopKpr.getGender());
            shopKprUser.setUserCreationDate(shopKpr.getUserCreationDate());

            shopKprRegElasticRepo.save(shopKprUser);
            shopKprRegistrationRepository.save(shopKpr);
        }
    }

    public ResponseEntity<AuthenticationResponse> authenticate( UserAuthenticate userAuthenticate ) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userAuthenticate.getMobileNumber(),
                        userAuthenticate.getPin());
        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (DisabledException disabledException) {
            log.info("User with phone number : " + userAuthenticate.getMobileNumber() + " is disabled");
            throw new DisabledException("User is disabled, contact with admin");
        } catch (BadCredentialsException badCredentialsException) {
            log.info("User mobile or pin does not match");
            throw new BadCredentialsException("Bad credential");
        }

        Optional<ShopKpr> shopKprOptional =
                shopKprRegistrationRepository.findByMobileNumber(userAuthenticate.getMobileNumber());

        if (shopKprOptional.isPresent()) {
            final UserDetails userDetails = shopKprOptional.get();
            String token = jwtUtils.generateToken(userDetails);
            return ResponseEntity.ok(new AuthenticationResponse(token));
        } else {
            log.info("User with mobile: " + userAuthenticate.getMobileNumber() + " not found");
            throw new BadCredentialsException("User not found");
        }
    }

    public void changePinNumber( String pinNumber, String mobileNumber ) {

        Optional<ShopKpr> shopKprOptional = shopKprRegistrationRepository.findByMobileNumber(mobileNumber);

        if (shopKprOptional.isPresent()) {
            ShopKpr shopKpr = shopKprOptional.get();
            shopKpr.setPin(passwordEncoder.encode(pinNumber));
            shopKprRegistrationRepository.save(shopKpr);
        } else {
            log.info("Wrong mobile number provied by user, Mobile number is: " + mobileNumber);
            throw new MobileNumberMisMatch("No Such Mobile Number");
        }

    }
}
