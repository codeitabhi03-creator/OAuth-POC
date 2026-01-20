package com.example.OAuth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    @GetMapping("/")
    public String home() {
        return "Home Page — Not Logged In"; // public page
    }

    @GetMapping("/login")
    public String login() {
        return "<a href=\"/oauth2/authorization/google\">Login with Google</a>"; // login link
    }

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal OAuth2User user) {
        return """
               <h3>Login Successful</h3>
               <p>Name: %s</p>
               <p>Email: %s</p>
               <img src="%s" width="80"/>
               <br><a href="/logout\">Logout</a>
               """.formatted(
                user.getAttribute("name"),   // google name
                user.getAttribute("email"),  // google email
                user.getAttribute("picture") // profile pic
        );
    }

    @GetMapping("/token")
    public Map<String, Object> token(@AuthenticationPrincipal OidcUser user) {
        Map<String, Object> map = new HashMap<>();
        map.put("id_token", user.getIdToken().getTokenValue()); // raw JWT
        map.put("claims", user.getClaims()); // claims map
        return map;
    }
}
