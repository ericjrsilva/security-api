package models.data;

/**
 * Data class to serialize deserialize password
 */
public class PasswordData {
    private String password;

    private PasswordData(){}

    public PasswordData(String password) {
        this.password = password;
    }

    /**
     * Password value.
     * @return
     */
    public String getPassword() {
        return password;
    }
}
