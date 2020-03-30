package tk.slaaavyn.slavikserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import tk.slaaavyn.slavikserver.security.jwt.JwtConfigurer;
import tk.slaaavyn.slavikserver.security.jwt.JwtTokenProvider;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                /* AUTH */
                .antMatchers(EndpointConstants.AUTH_ENDPOINT + "/**").permitAll()

                /* USER */
                .antMatchers(HttpMethod.PUT,EndpointConstants.USER_ENDPOINT + "/update-password/**").authenticated()
                .antMatchers(HttpMethod.PUT,EndpointConstants.USER_ENDPOINT + "/update-info/**").authenticated()
                .antMatchers(EndpointConstants.USER_ENDPOINT + "/**").hasRole("ADMIN")

                /* ROOM */
                .antMatchers(HttpMethod.GET,EndpointConstants.ROOM_ENDPOINT + "/**").authenticated()
                .antMatchers(EndpointConstants.ROOM_ENDPOINT + "/**").hasRole("ADMIN")

                /* DEVICE */
                .antMatchers(HttpMethod.GET,EndpointConstants.DEVICE_ENDPOINT + "/**").authenticated()
                .antMatchers(EndpointConstants.DEVICE_ENDPOINT + "/**").hasRole("ADMIN")

                /* TEMPERATURE */
                .antMatchers(HttpMethod.GET,EndpointConstants.TEMPERATURE_ENDPOINT + "/**").authenticated()
                .antMatchers(EndpointConstants.TEMPERATURE_ENDPOINT + "/**").hasRole("ADMIN")

                /* WS */
                .antMatchers(EndpointConstants.WS_TEMPERATURE+ "/**").permitAll()
                .antMatchers(EndpointConstants.WS_DEVICE + "/**").permitAll()
                .antMatchers(EndpointConstants.WS_CLIENT + "/**").permitAll()

                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}

