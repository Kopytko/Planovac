package controllers;

import models.User;
import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

	static Form<User> signInForm = form(User.class);
	static Form<User> registrationForm = form(User.class);

	public static Result index() {
		return ok(index.render(signInForm, "clearfix  ", ""));
	}

	public static Result signIn() {
		Form<User> filledForm = signInForm.bindFromRequest();
		User signingInUser = filledForm.get();
		if (User.signIn(signingInUser.email, signingInUser.password)) {
			return ok(calendar.render("Vitajte"));
		} else {
			return ok(index.render(filledForm, "control-group error",
					"The username or password you entered is incorrect."));
		}
	}

	public static Result signUpIndex() {
		return ok(signUp.render(registrationForm));
	}

	public static Result signUp() {
		Form<User> filledForm = registrationForm.bindFromRequest();
		User newUser = filledForm.get();
		if (!User.isEmailOk(newUser.email)) {
			return ok(signUp.render(filledForm));
		}
		if (!User.isEmailUnique(newUser.email)) {
			return ok(signUp.render(filledForm));
		}
		if (!User.isPasswordOk(newUser.password)) {
			return ok(signUp.render(filledForm));
		}
		if (!User.isRePasswordOk(newUser.password, newUser.rePassword)) {
			return ok(signUp.render(filledForm));
		}
		if (!User.isFirstNameOk(newUser.firstName)) {
			return ok(signUp.render(filledForm));
		}
		if (!User.isLastNameOk(newUser.lastName)) {
			return ok(signUp.render(filledForm));
		}
		if (!User.isBirthdayOk(newUser.birthday)) {
			return ok(signUp.render(filledForm));							
		}
		User.save(newUser);	
		return index();		
	}

}