package entity.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.Configs;

public class Order {
    
    private int shippingFees;
    private List listOrderMedia;
    private HashMap<String, String> deliveryInfo;

    public Order(){
        this.listOrderMedia = new ArrayList<>();
    }

    public Order(List lstOrderMedia) {
        this.listOrderMedia = lstOrderMedia;
    }

    public void addOrderMedia(OrderMedia om){
        this.listOrderMedia.add(om);
    }

    public void removeOrderMedia(OrderMedia om){
        this.listOrderMedia.remove(om);
    }

    public List getListOrderMedia() {
        return this.listOrderMedia;
    }

    public void setListOrderMedia(List lstOrderMedia) {
        this.listOrderMedia = lstOrderMedia;
    }

    public void setShippingFees(int shippingFees) {
        this.shippingFees = shippingFees;
    }

    public int getShippingFees() {
        return shippingFees;
    }

    public HashMap getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(HashMap deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public int getAmount(){
        double amount = 0;
        for (Object object : listOrderMedia) {
            OrderMedia om = (OrderMedia) object;
            amount += om.getPrice();
        }
        return (int) (amount + (Configs.PERCENT_VAT/100)*amount);
    }

}
