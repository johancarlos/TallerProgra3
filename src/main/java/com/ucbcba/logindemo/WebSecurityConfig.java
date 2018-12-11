package com.ucbcba.logindemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
// este es el que le da autoridad a los usuarios, sea este user o administrador
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO ESTO SOLO ES PARA GET REQUESTS
        http
                .authorizeRequests()//autoriza que todos los requests de abajo sean acceesibles
                .antMatchers("/user/posts","/user/profile").permitAll()
                .antMatchers("/category", "/index").permitAll()
                .antMatchers("/showPost").permitAll()
                .antMatchers("/list_categories").anonymous()
                //.anonymous sirve para dar permisos solo a usuario no logueado , restringe al user y al admin
                .antMatchers("/post").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/lastcomment").permitAll()
                .antMatchers("/post/upload-image").hasAuthority("USER")
                .antMatchers("/user/posts").hasAuthority("USER")
                .antMatchers("/post/authors/**").hasAuthority("ADMIN")
                .antMatchers("/user/comment").hasAuthority("USER")
                .antMatchers("/resources/**", "/register", "/", "/post" , "/home", "/user/profile").anonymous()// el request /register es solo para el usuario no loggeado
                .antMatchers("/admin/**", "/createCategoryForm").hasAuthority("ADMIN")// el request /admin/** es accesible para un usuarion con el rol ADMIN
                .antMatchers("/user/**").permitAll()
                .antMatchers("/user/editPost").hasAuthority("USER")
                .antMatchers("/postlike-rest/**").hasAuthority("USER")
                .antMatchers("/postunlike-rest/**").hasAuthority("USER")

                .anyRequest().authenticated() // cualquier request necesita ser autenticado
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .failureUrl("/login")
                        .defaultSuccessUrl("/welcome")//si es que el usuario se loggeo bien se le redirige a esta pagina
                        .permitAll()
                .and()
                .logout().permitAll().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.POST, "/categories/createCategory");
        web.ignoring().antMatchers(HttpMethod.POST, "/categories/editCategory");
        web.ignoring().antMatchers(HttpMethod.POST, "/posts", "/post");
        web.ignoring().antMatchers(HttpMethod.POST, "/createCategory");
        web.ignoring().antMatchers(HttpMethod.POST, "/comment");
        web.ignoring().antMatchers(HttpMethod.POST, "/post/reportpost");
        web.ignoring().antMatchers(HttpMethod.POST, "/post/upload-image");
        web.ignoring().antMatchers(HttpMethod.POST, "/post/hidepost");
        web.ignoring().antMatchers(HttpMethod.POST, "/comment-rest");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}