package com.shopKpr.shopKprRegister.controller;

import com.shopKpr.security.model.AuthenticationResponse;
import com.shopKpr.shopKprRegister.model.ChangePin;
import com.shopKpr.shopKprRegister.model.ShopKpr;
import com.shopKpr.shopKprRegister.model.UserAuthenticate;
import com.shopKpr.shopKprRegister.service.AzureBlobService;
import com.shopKpr.shopKprRegister.service.ShopKprRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/shopKpr")
public class LoginAndRegisterController {

    private final ShopKprRegistrationService shopKprRegistrationService;
    private final AzureBlobService azureBlobService;

    public LoginAndRegisterController( ShopKprRegistrationService shopKprRegistrationService,
                                       AzureBlobService azureBlobService ) {
        this.shopKprRegistrationService = shopKprRegistrationService;
        this.azureBlobService = azureBlobService;
    }

    @PostMapping("registerNewUser")
    public void registerShopKpr( @RequestBody ShopKpr shopKpr ) {
        shopKprRegistrationService.registerNewUser(shopKpr);
    }

    @GetMapping("authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateShopKpr(
            @RequestBody UserAuthenticate userAuthenticate ) {

        return shopKprRegistrationService.authenticate(userAuthenticate);

    }

    @PostMapping("uploadProfileImage")
    @PreAuthorize("hasRole('ROLE_SHOPKPR')")
    public void uploadProfileImage( @RequestParam MultipartFile multipartFile, Authentication authentication ) {
        azureBlobService.uploadProfileImage(multipartFile, authentication.getName());
    }

    @PutMapping("changePinNumber")
    public void changePinNumber( @RequestBody ChangePin changePin ) {
        shopKprRegistrationService.changePinNumber(changePin.getPin(), changePin.getMobileNumber());
    }

}
