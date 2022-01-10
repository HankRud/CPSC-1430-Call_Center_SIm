/** @author harry rudolph
*
 */


public class Tech {
    private int number;
    private String firstName;
    private String lastName;
    private String userName;


    /** constructor for the clas
     *
     * @param number  number
     * @param firstName  first name
     * @param lastName   last name
     * @param userName   username
     */
    public Tech(int number, String firstName, String lastName, String userName){
        this.number = number;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName= userName;
    }

    /** to string method
     *
     * @return  a string
     */
    @Override
    public String toString() {
        return "Tech: " +
                " " + number +
                "," + userName
                ;
    }
}
