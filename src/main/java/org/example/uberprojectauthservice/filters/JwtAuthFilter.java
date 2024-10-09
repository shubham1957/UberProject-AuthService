package org.example.uberprojectauthservice.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.uberprojectauthservice.services.JwtService;
import org.example.uberprojectauthservice.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// OncePerRequestFilter -> makes sures that the filter is executed once for every request
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private  final RequestMatcher uriMatcher = new AntPathRequestMatcher("/api/v1/auth/validate", HttpMethod.GET.name());

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    // FilterChain is multiple filters (that intercept the request) connected together like client->filter1->filter2->filter3->controller
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        if(request.getCookies() != null){
            for (Cookie cookie : request.getCookies()){
                if(cookie.getName().equals("JwtToken")){
                    token = cookie.getValue();
                }
            }
        }

        if(token==null){
            // user has not provided any jwt token and hence request should not go forward
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        System.out.println("Incoming token : "+token);

        // if token is present extract the email out of it using the method written inside jwtService class
        String email = jwtService.extractEmail(token);
        System.out.println("Incoming Email : "+email);

        // now check weather any user of this email exits or not and also validate the token
        if(email != null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            if(jwtService.validateToken(token,userDetails.getUsername())){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, null);
                // WebAuthenticationDetailsSource() converts the incoming request object to spring boot compatible object
                // bridge between servlet class and spring class
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //SecurityContextHolder will store userDetails
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request,response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException
    {
        RequestMatcher matcher = new NegatedRequestMatcher(uriMatcher);
        return matcher.matches(request);
    }
}
