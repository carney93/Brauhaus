package controllers;

import play.mvc.*;

import play.api.Environment;
import play.data.*;
import play.db.ebean.Transactional;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import models.*;
import models.users.*;


import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
    
    private FormFactory formFactory;
    
    @Inject
    public HomeController(FormFactory f) {
        this.formFactory = f;
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
                return ok(index.render(User.getUserById(session().get("email"))));
    }
    

    
    public Result store() {
   List<ItemOnSale> itemList = ItemOnSale.findAll();
   return ok(store.render(itemList, User.getUserById(session().get("email"))));
}
    
    public Result booking() {
    return ok(booking.render(User.getUserById(session().get("email"))));
    }
    
    public Result reservation() {
    return ok(reservation.render(User.getUserById(session().get("email"))));
    }
    
    
      public Result reviews(Long item) {
    List<Review> reviewList = null;
    List<ItemOnSale> itemList = ItemOnSale.findAll();
    Form<Review> addReview = formFactory.form(Review.class).bindFromRequest();
          
    if(item ==0){
        reviewList=Review.findAll();
    }else{
        reviewList = ItemOnSale.find.ref(item).getReviews();
    }

    return ok(reviews.render(User.getUserById(session().get("email")),reviewList, itemList, addReview));
 }
    
    
        
  
    
    
    
    @Security.Authenticated(Secured.class)
       @With(AuthAdmin.class)
    public Result additem() {
        Form<ItemOnSale> itemForm = formFactory.form(ItemOnSale.class);
    return ok(additem.render(itemForm, User.getUserById(session().get("email"))));
    }
    
    @Security.Authenticated(Secured.class)
    @With(AuthAdmin.class)
    @Transactional
    public Result addItemSubmit() {

        Form<ItemOnSale> newItemForm = formFactory.form(ItemOnSale.class).bindFromRequest();
    
    if (newItemForm.hasErrors()) {
           
            return badRequest(additem.render(newItemForm, User.getUserById(session().get("email"))));
        } else {
            // If no errors are found in the form data, we extract the data from the form.
            // Form objects have handy utility methods, such as the get() method we are using 
            // here to extract the data into an ItemOnSale object. This is possible because
            // we defined the form in terms of the model class ItemOnSale.
            ItemOnSale newItem = newItemForm.get();

            if (newItem.getId() == null) {
             
                newItem.save();
            } else {
          
                newItem.update();
            }

     
            flash("success", "Item " + newItem.getName() + " was added/updated.");
           
            return redirect(controllers.routes.HomeController.store());
        }
    }
    
    @Security.Authenticated(Secured.class)
           @With(AuthAdmin.class)
    @Transactional
    public Result deleteItem(Long id) {

   
        ItemOnSale.find.ref(id).delete();

       
        flash("success", "Item has been deleted.");
        
        return redirect(controllers.routes.HomeController.store());
    }
    
    

    @Security.Authenticated(Secured.class)
           @With(AuthAdmin.class)
    public Result updateItem(Long id) {
        ItemOnSale i;
        Form<ItemOnSale> itemForm;

        try {
            // Find the item by id
            i = ItemOnSale.find.byId(id);

            itemForm = formFactory.form(ItemOnSale.class).fill(i);
        } catch (Exception ex) {
            return badRequest("error");
        }

        return ok(additem.render(itemForm, User.getUserById(session().get("email"))));
    }
    
    public Result usersAdmin() {
    List<Administrator> userList = null;

    userList = Administrator.findAll();

    return ok(admin.render(userList,User.getUserById(session().get("email"))));

 }
    
@Security.Authenticated(Secured.class)
@Transactional
public Result addAdminSubmit()
 {
Form<Administrator> newUserForm = formFactory.form(Administrator.class).bindFromRequest();
if (newUserForm.hasErrors()) {
return badRequest(addAdmin.render(newUserForm,User.getUserById(session().get("email"))));
} else {
    Administrator newUser = newUserForm.get();
    
    if(User.getUserById(newUser.getEmail())==null){
        newUser.save();
    }else{
        newUser.update();
    }
    flash("success", "User " + newUser.getName() + " was added/updated.");

    
    }
    return redirect(controllers.routes.HomeController.usersAdmin()); 
}


    
    @Security.Authenticated(Secured.class)
public Result addAdmin() {
    Form<Administrator> userForm = formFactory.form(Administrator.class);
    return ok(addAdmin.render(userForm,User.getUserById(session().get("email"))));
}

    
    @Security.Authenticated(Secured.class)
@Transactional
@With(AuthAdmin.class)
public Result deleteAdmin(String email) {


        Administrator u = (Administrator) User.getUserById(email);
        u.delete();

    flash("success", "Admin has been deleted.");
    return redirect(controllers.routes.HomeController.usersAdmin());
}
    
@Security.Authenticated(Secured.class)
public Result updateAdmin(String email) {
    Administrator u;
    Form<Administrator> userForm;

    try {
       
        u = (Administrator)User.getUserById(email);
        u.update();

      
        userForm = formFactory.form(Administrator.class).fill(u);
    } catch (Exception ex) {
        return badRequest("error");
    }

   
    return ok(addAdmin.render(userForm,User.getUserById(session().get("email"))));
}
    
    
    
        public Result addReviewSubmit(){
            List<Review> reviewList = null;
    List<ItemOnSale> itemList = ItemOnSale.findAll();
Form<Review> addReview = formFactory.form(Review.class).bindFromRequest();
if (addReview.hasErrors()) {
return badRequest(reviews.render(User.getUserById(session().get("email")),reviewList, itemList, addReview));
} else {
    Review newReview = addReview.get();
    
    
        newReview.save();
    
    
    }
    return redirect(controllers.routes.HomeController.reviews(0));

}
    
      @Security.Authenticated(Secured.class)
           @With(AuthAdmin.class)
    @Transactional
    public Result deleteReview(Long id) {

   
        Review.find.ref(id).delete();

       
        flash("success", "Review has been deleted.");
        
        return redirect(controllers.routes.HomeController.reviews(0));
    }
    

 


    
    
    
}