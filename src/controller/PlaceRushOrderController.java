package controller;

import entity.order.Order;
import utils.Utils;

import java.text.ParseException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import java.util.Date;

/**
 * Handle the flow of place rush order use case
 * @author tienlonghungson
 */
public class PlaceRushOrderController extends PlaceOrderController{
    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceRushOrderController.class.getName());

    /**
     * calculate the amount of time from now to the delivery date
     * @param expectedTime: expected delivery time, format: yy/MM/dd 00:00:00
     * @return amount of time
     */
    public long convertDateToDeliveryDay(String expectedTime) throws ParseException {
        String[] dateTime = expectedTime.split(" ");
        String date = dateTime[0];
        Date now = new Date();
        Date expectedDeliTime = Utils.DATE_FORMATER.parse(date);

        long diffInMillis = Math.abs(expectedDeliTime.getTime() - now.getTime());
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * check if the delivery time is in right format
     * @param expectedTime a string represents expected time
     * @return {@code true} if right format, {@code false} otherwise
     */
    public boolean validateExpectedTime(String expectedTime) {
        return expectedTime.matches("^[0-9]+/[0-9]+/[0-9]+ [0-9]+:[0-9]+:[0-9]+$");
    }
    /**
     * check duration of delivery time
     * must be in range [1,20]
     * @param duration: expected duration time
     * @return {@code true} if duration is in [1,20], {@code false} otherwise
     */
    public boolean validateRushDeliveryDay(String duration) {
        try {
            int deliveryDay = Integer.parseInt(duration);
            return deliveryDay >= 1 && deliveryDay <= 10;
        } catch (Exception e) {
            LOGGER.info("Delivery day must be an integer");
            return false;
        }
    }

    /**
     * calculate shipping fee for rush order
     * @param order order
     * @param deliveryDay: amount of time to the delivery day
     * @return fee
     */
    public int calculateShippingFee(Order order, String deliveryDay) {
        int fees = calculateShippingFee(order);

        // extra fee for rush order
        Random rand = new Random();
        int additionalFee = (int)( ( (rand.nextFloat()*10)/100 ) * (20 - Integer.parseInt(deliveryDay)));
        fees += additionalFee;
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
        return fees;
    }
}
