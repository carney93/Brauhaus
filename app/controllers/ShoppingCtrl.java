package controllers;

import play.mvc.*;
import play.data.*;
import javax.inject.Inject;

import views.html.*;
import play.db.ebean.Transactional;
import play.api.Environment;

// Import models
import models.users.*;
import models.*;
import models.shopping.*;

import play.data.format.*;
import play.data.validation.*;
import java.util.Date;

import java.util.ArrayList;
import java.util.List;

import java.util.Calendar;


import java.text.SimpleDateFormat;


import views.html.*;

@Security.Authenticated(Secured.class)
// Authorise user (check if user is a customer)
@With(CheckIfCustomer.class)

public class ShoppingCtrl extends Controller {

    private FormFactory formFactory;
    private Environment env;

    @Inject
    public ShoppingCtrl(Environment e, FormFactory f) {
        this.env = e;
        this.formFactory = f;
    }
    // View an individual order
    @Transactional
    public Result viewOrder(long id) {
        ShopOrder order = ShopOrder.find.byId(id);
        return ok(orderConfirmed.render((Customer)User.getUserById(session().get("email")), order));
    }
    
    // Add item to customer basket
    @Transactional
    public Result addToBasket(Long id) {
        
        // Find the item on sale
        ItemOnSale item = ItemOnSale.find.byId(id);
        
        // Get basket for logged in customer
        Customer customer = (Customer)User.getUserById(session().get("email"));
        
        // Check if item in basket
        if (customer.getBasket() == null) {
            // If no basket, create one
            customer.setBasket(new Basket());
            customer.getBasket().setCustomer(customer);
            customer.update();
        }
        // Add product to the basket and save
        customer.getBasket().addItemOnSale(item);
        customer.update();

        item.decrementStock();
        item.update();
        
         List<ItemOnSale> itemList = ItemOnSale.findAll();
        return ok(store.render(itemList, User.getUserById(session().get("email"))));
        
        
    }
    
    @Transactional
    public Result emptyBasket() {
        
        Customer c = (Customer)User.getUserById(session().get("email"));
        c.getBasket().removeAllItems();
        c.getBasket().update();
        
        return ok(basket.render(c));
    }
    
    @Transactional
    public Result placeOrder() {
        Customer c = (Customer)User.getUserById(session().get("email"));
        
        // Create an order instance
        ShopOrder order = new ShopOrder();
        
        // Associate order with customer
        order.setCustomer(c);
        
        // Copy basket to order
        order.setItems(c.getBasket().getBasketItems());
        
        // Save the order now to generate a new id for this order
        order.save();
       
       // Move items from basket to order
        for (OrderItem i: order.getItems()) {
            // Associate with order
            i.setOrder(order);
            // Remove from basket
            i.setBasket(null);
            // update item
            i.update();
        }
        
        // Update the order
        order.update();
        
        // Clear and update the shopping basket
        c.getBasket().setBasketItems(null);
        c.getBasket().update();
        
        // Show order confirmed view
        return ok(orderConfirmed.render(c, order));
    }
    
    @Transactional
    public Result showBasket() {
        
           Customer customer = (Customer)User.getUserById(session().get("email"));
        
        // Check if item in basket
        if (customer.getBasket() == null) {
            // If no basket, create one
            customer.setBasket(new Basket());
            customer.getBasket().setCustomer(customer);
            customer.update();
        }
        
        return ok(basket.render((Customer)User.getUserById(session().get("email"))));
    }
    
    // Add an item to the basket
    @Transactional
    public Result addOne(Long itemId, Long pid) {
        
        
        OrderItem item = OrderItem.find.byId(itemId);
        ItemOnSale ios = ItemOnSale.find.byId(pid);

        if(ios.getStock()>0){
        // Increment quantity
        item.increaseQty();
        // Save
        item.update();
        ios.decrementStock();
        ios.update();
        }else{
            flash("error", "Sorry, no more of these items are in stock");
        }
        // Show updated basket
        return redirect(routes.ShoppingCtrl.showBasket());
    }

    @Transactional
    public Result removeOne(Long itemId,Long pid) {
        
        // Get the order item
        OrderItem item = OrderItem.find.byId(itemId);
        ItemOnSale ios = ItemOnSale.find.byId(pid);
        // Get user
        Customer c = (Customer)User.getUserById(session().get("email"));
        // Call basket remove item method
        c.getBasket().removeItem(item,ios);
        c.getBasket().update();
        // back to basket
        return ok(basket.render(c));
    }
    
    @Transactional
    public Result viewOrders() {       
        return ok(viewOrders.render((Customer)User.getUserById(session().get("email"))));
    }
    
    @Transactional    
public Result cancelOrder(Long orderId){
        ShopOrder order = ShopOrder.find.byId(orderId);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        
        c1=order.getOrderDate();
        if(compareDates(c1,c2)){
           // order.removeAllItems(orderId);
           order.adjustStock();
           order.delete();
           
            flash("success", "Your order has been cancelled");
        }else {
            flash("success", "Sorry, it is too late to cancel this order");
        }
        return ok(viewOrders.render((Customer)User.getUserById(session().get("email"))));
    }
    
public boolean compareDates(Calendar c1, Calendar c2){
        boolean allowed = true;
        long miliSecondForDate1 = c1.getTimeInMillis();
        long miliSecondForDate2 = c2.getTimeInMillis();
        // Calculate the difference in millisecond between two dates
        long diffInMilis = miliSecondForDate2 - miliSecondForDate1;
        long diffInMinutes = diffInMilis / (60 * 1000);
        if(diffInMinutes >60){
            allowed=false;
        }
        return allowed;
    }



}