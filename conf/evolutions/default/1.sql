# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table basket (
  id                            bigint auto_increment not null,
  customer_email                varchar(255),
  constraint uq_basket_customer_email unique (customer_email),
  constraint pk_basket primary key (id)
);

create table item_on_sale (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  description                   varchar(255),
  stock                         integer not null,
  price                         double not null,
  constraint pk_item_on_sale primary key (id)
);

create table order_item (
  id                            bigint auto_increment not null,
  quantity                      integer not null,
  price                         double not null,
  order_id                      bigint,
  basket_id                     bigint,
  item_id                       bigint,
  constraint pk_order_item primary key (id)
);

create table review (
  id                            bigint auto_increment not null,
  review_message                varchar(255),
  items_id                      bigint,
  reviewer_name                 varchar(255),
  constraint pk_review primary key (id)
);

create table shop_order (
  id                            bigint auto_increment not null,
  order_date                    timestamp,
  customer_email                varchar(255),
  order_total                   double not null,
  constraint pk_shop_order primary key (id)
);

create table user (
  type                          varchar(31) not null,
  email                         varchar(255) not null,
  date_of_birth                 date,
  name                          varchar(255),
  password                      varchar(255),
  role                          varchar(255),
  street1                       varchar(255),
  street2                       varchar(255),
  town                          varchar(255),
  post_code                     varchar(255),
  constraint pk_user primary key (email)
);

alter table basket add constraint fk_basket_customer_email foreign key (customer_email) references user (email) on delete restrict on update restrict;

alter table order_item add constraint fk_order_item_order_id foreign key (order_id) references shop_order (id) on delete restrict on update restrict;
create index ix_order_item_order_id on order_item (order_id);

alter table order_item add constraint fk_order_item_basket_id foreign key (basket_id) references basket (id) on delete restrict on update restrict;
create index ix_order_item_basket_id on order_item (basket_id);

alter table order_item add constraint fk_order_item_item_id foreign key (item_id) references item_on_sale (id) on delete restrict on update restrict;
create index ix_order_item_item_id on order_item (item_id);

alter table review add constraint fk_review_items_id foreign key (items_id) references item_on_sale (id) on delete restrict on update restrict;
create index ix_review_items_id on review (items_id);

alter table shop_order add constraint fk_shop_order_customer_email foreign key (customer_email) references user (email) on delete restrict on update restrict;
create index ix_shop_order_customer_email on shop_order (customer_email);


# --- !Downs

alter table basket drop constraint if exists fk_basket_customer_email;

alter table order_item drop constraint if exists fk_order_item_order_id;
drop index if exists ix_order_item_order_id;

alter table order_item drop constraint if exists fk_order_item_basket_id;
drop index if exists ix_order_item_basket_id;

alter table order_item drop constraint if exists fk_order_item_item_id;
drop index if exists ix_order_item_item_id;

alter table review drop constraint if exists fk_review_items_id;
drop index if exists ix_review_items_id;

alter table shop_order drop constraint if exists fk_shop_order_customer_email;
drop index if exists ix_shop_order_customer_email;

drop table if exists basket;

drop table if exists item_on_sale;

drop table if exists order_item;

drop table if exists review;

drop table if exists shop_order;

drop table if exists user;

