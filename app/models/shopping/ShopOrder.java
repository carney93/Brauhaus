package models.shopping;

import java.util.*;
import javax.persistence.*;

import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import java.util.Date;
import models.*;
import models.users.*;

import java.text.SimpleDateFormat;


// ShopOrder entity managed by Ebean
@Entity
public class ShopOrder extends Model {

    @Id
    private Long id;    
    private Calendar orderDate;
    
    @OneToMany(mappedBy="order", cascade = CascadeType.ALL)
    private List<OrderItem> items;
    
    @ManyToOne
    private Customer customer;
    
    private double orderTotal;
    
    


    // Default constructor
    public  ShopOrder() {
        orderDate = Calendar.getInstance();
    }
    	
    public static Finder<Long,ShopOrder> find = new Finder<Long,ShopOrder>(ShopOrder.class);

    //Find all Products in the database
    public static List<ShopOrder> findAll() {
        return ShopOrder.find.all();
    }
    
   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Calendar orderDate) {
        orderDate = orderDate;
    }
    
     public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
      public double getOrderTotal() {
        
        for (OrderItem i: items) {
            orderTotal += i.getItemTotal();
        }
        return orderTotal;
    }
    

    
    public String getOrderDateString() {
        if(orderDate == null) {
            return "No Date Availible";
        }
        String s = new SimpleDateFormat("dd-MMM-yyyy").format(orderDate.getTime());
        return s;
    }
    
    public void adjustStock(){
        for (OrderItem i : items) {
            ItemOnSale ios = ItemOnSale.find.byId(i.getItem().getId());
            if (i.getItem().getId() == ios.getId()) {
                int quantity = i.getQuantity();
                ios.incrementStockQty(quantity);
                ios.update();
            }
        }
    }
    
        
    
    


}