public class Customer {
    /** customer number*/
    private int number;
    /** customer name*/
    private String firstName;
    /** customer name*/
    private String lastName;
    /** email address*/
    private String email;
    /** phone number
     */
    private String phoneNumber;

    /** the class constructor*/
    public Customer(int number,String firstName,String lastName, String email,String phoneNumber){
        this.number = number;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return " Customer: " +
                " " + number +
                "," + lastName
                ;
    }
}
