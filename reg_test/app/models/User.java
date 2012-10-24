package models;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import play.db.ebean.*;
import javax.persistence.*;

@Entity
public class User extends Model {

	@Id
	public int id;

	@Column(nullable = false, unique = true)
	public String email;

	@Column(nullable = false)
	public String password;

	@Transient
	public String rePassword;

	@Column(nullable = false)
	public String firstName;

	@Column(nullable = false)
	public String lastName;

	@Column(nullable = false)
	public String birthday;

	public static boolean isEmailOk(String email) {		
		Pattern p = Pattern
				.compile("[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})");		
		if (p.matcher(email).matches()) {
			return true;
		} else {			
			return false;
		}			
	}
	
	public static boolean isEmailUnique(String email) {				
		if (find.where().like("email", email).findUnique() == null) {
			return true;
		} else {			
			return false;
		}			
	}
	
	public static boolean isPasswordOk(String password) {
		Pattern p = Pattern.compile("^(?=.*[0-9])(?=.*[a-z@#$%^&+=])(?=.*[A-Z])(?=\\S+$).{8,}$");		
		if(p.matcher(password).matches()) {
			return true;
		} else {			
			return false;
		}
	}
	
	public static boolean isRePasswordOk(String password, String rePassword) {		
		if(password.equals(rePassword)) {
			return true;
		} else {			
			return false;
		}
	}
	
	public static boolean isFirstNameOk(String firstName) {
		Pattern p = Pattern.compile("[A-Z][a-z]+");		
		if (p.matcher(firstName).matches()) {
			return true;
		} else {
			System.out.println("first");
			return false;
		}	
	}
	
	public static boolean isLastNameOk(String lastName) {
		Pattern p = Pattern.compile("[A-Z][a-z]+");		
		if (p.matcher(lastName).matches()) {
			return true;
		} else {
			System.out.println("last");
			return false;
		}	
	}
	
	public static boolean isBirthdayOk(String birthday) {
        try {        	
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            format.setLenient(false);
            format.parse(birthday);
        } catch (Exception e) {
        	System.out.println("datum");
            return false;
        } 
        return true;
    }	

	public static Finder<Integer, User> find = new Finder(Integer.class, User.class);

	public static void save(User user) {
		user.save();
	}

	public static boolean signIn(String email, String password) {
		User user = find.where().like("email", email)
				.like("password", password).findUnique();
		if (user == null) {
			return false;
		} else {
			return true;
		}
	}

}
