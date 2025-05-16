package org.pchardwarestore.controller.accountController;

import lombok.RequiredArgsConstructor;
import org.pchardwarestore.service.accountService.userService.RegistrationUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/code")
public class CodeConfirmationController {

    private final RegistrationUserService userService;

    @GetMapping("/renew")
    public boolean codeRenew(@RequestParam String email) {
        return userService.renewCode(email);
    }

    @GetMapping("/confirmation")
    public ResponseEntity<String> confirmationEmail(@RequestParam String codeConfirmation) {
        try {
            userService.confirmationEmail(codeConfirmation);
            return new ResponseEntity<>("User confirmed successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Confirmation failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
