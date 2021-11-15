package org.launchcode.javawebdevtechjobsauthentication;

import org.launchcode.javawebdevtechjobsauthentication.controllers.AuthenticationController;
import org.launchcode.javawebdevtechjobsauthentication.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class JavaWebDevTechjobsAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaWebDevTechjobsAuthenticationApplication.class, args);
	}

	public static class AuthenticationFilter extends HandlerInterceptorAdapter {

		private  final List<String> whitelist = Arrays.asList("/login", "/register", "/logout", "/css");

		private  boolean isWhitelisted(String path) {
			for (String pathRoot : whitelist) {
				if (path.startsWith(pathRoot)) {
					return true;
				}
			}
			return false;
		}




		@Autowired
		User.UserRepository userRepository;

		@Autowired
		AuthenticationController authenticationController;

		@Override
		public boolean preHandle(HttpServletRequest request,
								 HttpServletResponse response,
								 Object handler) throws IOException {

			// Don't require sign-in for whitelisted pages
			if (isWhitelisted(request.getRequestURI())) {
				// returning true indicates that the request may proceed
				return true;
			}

			HttpSession session = request.getSession();
			User user = authenticationController.getUserFromSession(session);

			// The user is logged in
			if (user != null) {
				return true;
			}

			// The user is NOT logged in
			response.sendRedirect("/login");
			return false;
		}
	}




}
