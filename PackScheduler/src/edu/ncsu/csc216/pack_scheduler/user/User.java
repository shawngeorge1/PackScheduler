package edu.ncsu.csc216.pack_scheduler.user;

/**
 * An abstract class which represents the minimum requirements for a user to be
 * saved within and authenticate with the system.
 *
 * @author Matt
 */
public abstract class User {

    /** Students first name */
    private String firstName;
    /** Students last name */
    private String lastName;
    /** Students id */
    private String id;
    /** Students email address */
    private String email;
    /** Students password */
    private String password;

    /**
     * Construct a user which has some holds some basic info like name, id, and
     * password
     * 
     * @param firstName users first name
     * @param lastName  users last name
     * @param id        users id to authenticate with
     * @param email     users email address
     * @param password  users hashed password
     */
    public User(String firstName, String lastName, String id, String email, String password) {
        super();
        setFirstName(firstName);
        setLastName(lastName);
        setId(id);
        setEmail(email);
        setPassword(password);
    }

    /**
     * Gets student's first name
     * 
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets student's last name
     * 
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets student's id
     * 
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Returns student's email
     * 
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets student's email
     * 
     * @param email the email to set
     * @throws IllegalArgumentException If email is null, or is an empty string, or
     *                                  doesn't contain @ or . symbols, in the
     *                                  correct order
     */
    public void setEmail(String email) {
        if (email == null || email.equals("")) {
            throw new IllegalArgumentException("Invalid email");
        }
        int atIndicator = email.indexOf('@');
        int dotIndicator = email.lastIndexOf('.');

        if (atIndicator == -1 || dotIndicator == -1 || dotIndicator < atIndicator) {
            throw new IllegalArgumentException("Invalid email");
        }

        this.email = email;
    }

    /**
     * Returns student's password
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets student's password
     * 
     * @param password the password to set
     */
    public void setPassword(String password) {
        if (password == null || password.equals("")) {
            throw new IllegalArgumentException("Invalid password");
        }
        this.password = password;
    }

    /**
     * Sets student's first name
     * 
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.equals("")) {
            throw new IllegalArgumentException("Invalid first name");
        }
        this.firstName = firstName;
    }

    /**
     * Sets student's last name
     * 
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        if (lastName == null || lastName.equals("")) {
            throw new IllegalArgumentException("Invalid last name");
        }
        this.lastName = lastName;
    }

    /**
     * Sets student's id
     * 
     * @param id the id to set
     */
    protected void setId(String id) {
        if (id == null || id.equals("")) {
            throw new IllegalArgumentException("Invalid id");
        }
        this.id = id;
    }

    /**
     * Get the hash code of the user
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        return result;
    }

    /**
     * True if both objects have the same fields
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        return true;
    }

}