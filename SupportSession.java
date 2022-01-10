import java.util.PriorityQueue;

/** @author Harry Rudolph
 * Class that creates a support session between
 * a caller and a tech
 */

public class SupportSession implements Comparable<SupportSession> {

    /** The customer in the call**/
    private Customer customer;
    /** The tech*/
    private Tech tech;
    /** generated call length**/
    private int callLength;
    /** finish time**/
    private int finishTime;
    /** the Queue**/
    private PriorityQueue<SupportSession> priorityQueue;


    /** constructor for the class
     *
     * @param customer the customer
     * @param tech     the tech
     * @param callLength  call length
     * @param finishTime  finish time
     */

    public SupportSession( Customer customer, Tech tech, int callLength,int finishTime){
        this.customer = customer;
        this.tech = tech;
        this.callLength = callLength;
        this.finishTime = finishTime;
    }
    /** compares the times left
     *
     * @param  otherSession the session to compare
     * @return standard comparable returns
     */
    public int compareTo(SupportSession otherSession) {
        if (otherSession.finishTime == this.finishTime){
            return 0;
        }else if(otherSession.finishTime > this.finishTime){
            return -1;
        }else {return 1;}
    }

    /** gets the finish time for the call
     * @return the finish time
     */
    public int getFinishTime() {
        return finishTime;
    }

    /** gets the tech
     *
     * @return the tech
     */
   public Tech getTech(){
        return tech;
   }

    /** to string method for the class
     * @return the class string
     */
    @Override
    public String toString() {
        return "Support Session with"+ customer.toString()+ ", and "+tech.toString()+ " is complete";
    }
}
