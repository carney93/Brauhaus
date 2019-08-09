package models;



import java.util.*;
import javax.persistence.*;
import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import models.*;
import models.users.*;

@Entity
public class ItemOnSale extends Model {

    // Properties
    @Id
    private Long id;
    @Constraints.Required
    private String name;
    @Constraints.Required
    private String description;
    @Constraints.Required
    private int stock;
    @Constraints.Required
    private double price;
    
  @OneToMany(mappedBy="items", cascade = CascadeType.ALL)
   private List<Review> reviews;
    
    
 
    
public static Finder<Long,ItemOnSale> find = new Finder<Long,ItemOnSale>(ItemOnSale.class);

    //Find all Departments in the database
public static List<ItemOnSale> findAll() {
 return ItemOnSale.find.query().where().orderBy("name asc").findList();
}

    
     public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap();

        // Get all the categories from the database and add them to the options hash map
        for (ItemOnSale i: ItemOnSale.findAll()) {
            options.put(Long.toString(i.getId()), i.getName());
        }
        return options;
    }
    
    
    // Default Constructor
    public ItemOnSale() {
    }

    // Constructor to initialise object
    public ItemOnSale(Long id, String name, String description, int stock, double price, List<Review> reviews) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.reviews = reviews;
        
    }
     

  
    
    public List<Review> getReviews() {
        return reviews;
    }
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    // Accessor methods
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
     public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    
     public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public void decrementStock(){
        stock -= 1;
    }

     public void incrementStock(){
        stock += 1;
    }

    public void incrementStockQty(int qty){
    stock +=qty;
}

}