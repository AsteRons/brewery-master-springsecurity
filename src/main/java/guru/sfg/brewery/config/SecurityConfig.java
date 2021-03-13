package guru.sfg.brewery.config;

import guru.sfg.brewery.security.JpaUserDetailsService;
import guru.sfg.brewery.security.RestHeaderAuthFilter;
import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter{


        public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager) {

            RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));
            filter.setAuthenticationManager(authenticationManager);
            return filter;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.addFilterBefore(restHeaderAuthFilter(authenticationManager()),
                    UsernamePasswordAuthenticationFilter.class)
            .csrf().disable();

            http
                 .authorizeRequests(authorize -> {
                            authorize
                                    .antMatchers("/h2-console/**").permitAll() //do not use in production!
                                    .antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
                                    .antMatchers("/beers/find", "/beers*").permitAll()
                                    .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                                    .mvcMatchers(HttpMethod.DELETE, "/api/v1/beer/**").hasRole("ADMIN")
                                    .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll();
                        } )
                        .authorizeRequests()
                        .anyRequest()
                        .authenticated()
                        .and()
                        .formLogin()
                        .and()
                        .httpBasic();

            //h2 console config
           http.headers().frameOptions().sameOrigin();
            }

        @Bean
        PasswordEncoder passwordEncoder() {
            return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
        }


}
