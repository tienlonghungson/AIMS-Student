package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;

/**
 * This class controls the flow of place order usecase in our AIMS project
 * @author nguyenlm
 */
public class PlaceOrderController extends BaseController{

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());

    /**
     * This method checks the availability of product when user click PlaceOrder button
     * @throws SQLException exception when call checkAvailabilityOfProduct()
     */
    public void placeOrder() throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method creates the new Order based on the Cart
     * @return Order
     */
    public Order createOrder(){
        Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(), 
                                                   cartMedia.getQuantity(), 
                                                   cartMedia.getPrice());    
            order.getListOrderMedia().add(orderMedia);
        }
        return order;
    }

    /**
     * This method creates the new Invoice based on order
     * @param order order
     * @return Invoice
     */
    public Invoice createInvoice(Order order) {
        return new Invoice(order);
    }

    /**
     * This method takes responsibility for processing the shipping info from user
     * @param info delivery information
     * @throws InterruptedException exception when call validateDeliveryInfo()
     * @throws IOException exception when call validateDeliveryInfo()
     */
    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException{
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        validateDeliveryInfo(info);
    }
    
    /**
   * The method validates the info
   * @param info delivery information
   * @throws InterruptedException exception when call validateDeliveryInfo()
   * @throws IOException exception when call validateDeliveryInfo()
   */
    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException{
    	
    }
    
    public boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.length()!=10){
            return false;
        }
        if (!phoneNumber.startsWith("0")){
            return false;
        }
        try{
            Integer.parseInt(phoneNumber);
        } catch (NumberFormatException numberFormatException){
            return false;
        }
    	return true;
    }
    
    public boolean validateName(String name) {
        // check special characters' appearance, numbers' appearance
        Pattern checkPattern=Pattern.compile("[^a-zA-Z ]");
        Matcher matcher = checkPattern.matcher(name);
        return !matcher.find();
    }
    
    public boolean validateAddress(String address) {
        Pattern checkPattern=Pattern.compile("[^a-zA-Z0-9/ ]");
        Matcher matcher = checkPattern.matcher(address);
        return !matcher.find();
    }
    

    /**
     * This method calculates the shipping fees of order
     * @param order order
     * @return shippingFee
     */
    public int calculateShippingFee(Order order){
        Random rand = new Random();
        int fees = (int)( ( (rand.nextFloat()*10)/100 ) * order.getAmount() );
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
        return fees;
    }
}
