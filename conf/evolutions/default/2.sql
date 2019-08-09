

# --- !Ups

insert into item_on_sale (id,name,description,stock,price) values ( 1, 'Brauhaus Ale','Superbly balanced Ale. Aromas of citrus, grapefruit and pine pip. Flavour follows from the nose adding some malt and spicy dry hop notes. Bitterness subsides for a long smooth finish.', 6, 5.00 );
insert into item_on_sale (id,name,description,stock,price) values ( 2, 'Brauhaus Larger','Brauhaus Lager is a traditional Czech style pilsner. The bitterness, which is provided by the hops, are a little higher than the usual commercial lagers but are still relatively soft and rounded.', 50, 4.90 );
insert into item_on_sale (id,name,description,stock,price) values ( 3, 'Brauhaus Pale Ale','A gently hopped pale ale using Irish lager malt to give a light body but retaining hop flavour and aroma with smooth citrus, peach and tropical notes.', 100, 5.50 );
insert into item_on_sale (id,name,description,stock,price) values ( 4, 'Brauhaus Blonde','A classic Belgian blonde ale. Fruity and floral with some yeasty notes. Smooth medium body with well integrated alcohol. Flavour follows from the nose joined by some pepper and herbal notes and a light touch of grassy hops.', 40, 5.90 );
insert into item_on_sale (id,name,description,stock,price) values ( 5, 'Brauhaus Stout','Balanced brew, smoothed and rounded without being bland. Sweetness derived from fresh oysters chucked into the conditioning tank. ', 100, 4.80 );
insert into item_on_sale (id,name,description,stock,price) values ( 6, 'Brauhaus IPA','Brauhaus IPA featuring fruity hop flavours balanced with lush Irish malt to create a complex, sessionable IPA.', 70, 6.00 );

insert into user (type,email,role,name,password) values ( 'a','admin@brauhaus.com','admin','Graham Carney', 'password');
insert into user (type,email,role,name,password) values ( 'c','user@brauhaus.com','customer','Shane Byrne', 'password');

insert into review (id,items_id,review_message,reviewer_name) values ( 1,1,'Very Tasty. Worth the price. 5*','Paul Byrne');
insert into review (id,items_id,review_message,reviewer_name) values ( 2,1,'Wouldnt reccomend. Tasted foul','Mick Hynes');
insert into review (id,items_id,review_message,reviewer_name) values ( 3,2,'Not the best larger Ive had but it does the job','Paul Byrne');
insert into review (id,items_id,review_message,reviewer_name) values ( 4,3,'First Time trying pale ale. Not a fan ','Lisa C');
insert into review (id,items_id,review_message,reviewer_name) values ( 5,4,'Beautiful. These guys make the best beers in Dublin.','Dylan Scully');
insert into review (id,items_id,review_message,reviewer_name) values ( 6,4,'Really pleasant to drink. Was nicer on tap in the bar but I was still happy with the purchase','Susan OReilly');
insert into review (id,items_id,review_message,reviewer_name) values ( 7,4,'Probaly my favourite beverage that Brahaus has to offer','Paul Byrne');
insert into review (id,items_id,review_message,reviewer_name) values ( 8,5,'Better than guinness','Glen Dunne');
insert into review (id,items_id,review_message,reviewer_name) values ( 9,5,'Very strong stout. Its worth your time','Ross Geller');
insert into review (id,items_id,review_message,reviewer_name) values ( 10,6,'Not usually a fan of the IPAS but this one is quite pleasant','Paul Byrne');
insert into review (id,items_id,review_message,reviewer_name) values ( 11,6,'I dont know how people drink this stuff','Mick Hynes');
insert into review (id,items_id,review_message,reviewer_name) values ( 112,6,'Tastes like a hangover','Joe T');

