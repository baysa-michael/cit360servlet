package model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int userID;
	
	@Column(name = "username")
    private String username;
	
	@Column(name = "hashedPassword")
    private String hashedPassword;
	
	@Column(name = "email")
    private String email;
	
	@Column(name = "string")
    private String displayName;
	
	@Column(name = "userSalt")
    private int userSalt;

    public UserAccount(String username,
                       String hashedPassword,
                       String email,
                       String displayName,
                       int userSalt) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.email = email;
        this.displayName = displayName;
        this.userSalt = userSalt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getUserSalt() {
        return userSalt;
    }

    public void setUserSalt(int userSalt) {
        this.userSalt = userSalt;
    }
}
