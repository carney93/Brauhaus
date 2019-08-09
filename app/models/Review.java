package models;
import java.util.*;
import javax.persistence.*;
import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import models.*;
import models.users.*;
import models.shopping.*;

@Entity
public class Review extends Model {
    
    @Id
    private Long id;
    
    @Constraints.Required
    private String reviewMessage;
    
 
    @ManyToOne
    private ItemOnSale items;
    
    @Constraints.Required
    private String reviewerName;

 
  
    
    public static final Finder<Long, Review> find = new Finder<>(Review.class);
			    
    public static final List<Review> findAll() {
       return Review.find.all();
    }
    
    
     public Review(){

    }
    
    public Review(Long id, String reviewMessage, String reviewerName)
	{
        this.id = id;
        this.reviewMessage = reviewMessage;
        this.reviewerName = reviewerName;

   }
	
    
    
    
    public String getReviewMessage() {
        return reviewMessage;
    }
    public void setReviewMessage(String reviewMessage) {
        this.reviewMessage = reviewMessage;
    }
    
    
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
     public ItemOnSale getItems() {
        return items;
    }
    public void setItems(ItemOnSale items) {
        this.items = items;
    }
    
      public String getReviewerName() {
        return reviewerName;
    }
    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }
    
  
    
 
    
  
    
    
    
    
    
}