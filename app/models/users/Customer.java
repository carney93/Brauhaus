package models.users;


import java.util.*;
import javax.persistence.*;
import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;


import models.shopping.*;
import models.users.*;
import models.*;



@Table(name = "user")
@DiscriminatorValue("c")

// Customer inherits from the User class
@Entity
public class Customer extends User{
    
    @Constraints.Required
    private String street1;
    @Constraints.Required
    private String street2;
    @Constraints.Required
    private String town;
    @Constraints.Required
    private String postCode;

    
    @OneToOne(mappedBy="customer", cascade = CascadeType.ALL)
    private Basket basket;

    @OneToMany(mappedBy="customer", cascade = CascadeType.ALL)
    private List<ShopOrder> orders;
    
  

    

    public Customer(){

    }
	
   public Customer(String email, String role, String name, 
    String password, Date dateOfBirth,String street1, String street2, String town, String postCode)
	{
		super(email, role, name, password,dateOfBirth);
        this.street1 = street1;
        this.street2 = street2;
        this.town = town;
        this.postCode = postCode;
      
        
	}
    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

   
    public static final Finder<Long, Customer> find = new Finder<>(Customer.class);
			    
    public static final List<Customer> findAll() {
       return Customer.find.all();
    }
    
    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public List<ShopOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<ShopOrder> orders) {
        this.orders = orders;
}
    
    
    
      public Long getPayment() {
          
        long allCents = Math.round(getBasket().getBasketTotal() * 100);
          
          return allCents;
    }
    
}
