package models.entities;

import javax.persistence.*;

@Entity
@Table(name = "auth")
public class Auth {
    /**
     * User login
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * User login
     */
    @Column(unique = true, name = "login")
    private String login;

    /**
     * User password
     */
    @Column(name = "password")
    private String password;

    /**
     * User type
     */
    @Column(name = "type")
    private String userType;

    /**
     * Auth object constructor
     */
    public Auth(){}

    /**
     * Auth object constructor
     * @param login user login
     * @param password user password
     * @param type user type
     */
    public Auth(String login, String password, String type){
        this.login = login;
        this.password = password;
        this.userType = type;
    }

    /**
     * Get login
     * @return login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Get user password
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get user Type
     * @return user type
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Set user login
     * @param login user login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Set user password
     * @param password user password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set user type
     * @param userType user type
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * Get object as string
     * @return string
     */
    @Override
    public String toString() {
        return String.format("Authorization: { login: %s, password: %s, user_type: %s };", login, password, userType);
    }
}
