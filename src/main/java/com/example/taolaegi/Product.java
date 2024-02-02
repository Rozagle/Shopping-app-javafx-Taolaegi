package com.example.taolaegi;

public class Product extends MenuPageCollector{

    private String Id ;
    private String Name ;
    private String Itemscode;
    private double Price ;
    private int qty ;
    private double Total;


    public Product() {}
    public Product(String Id , String Name , double Price , int qty , double Total){
        this.Id=Id;
        this.Name = Name;
        this.Price=Price;
        this.qty = qty;
        this.Total= Total;
    }
    public Product(String Id , String Name , double Price , int qty , double Total, String itemscode) {
        this.Id = Id;
        this.Name = Name;
        this.Price = Price;
        this.qty = qty;
        this.Total= Total;
        this.Itemscode = itemscode;

    }


    public String getId() {
        return Id ;
    }

    public void setId(String Id) {
        this. Id =  Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {

        this.qty = qty;

    }

    public double getTotal (){
        return qty * Price;
    }

    public void setTotal(int qty , double price){
        this.qty= qty;
        this.Price= price;
    }
    public String getItemscode(String Name ,String Id) {
        return Name.substring(0,2) + Id ;
    }

    public void setItemscode(String Name ,String Id) {
        this.Id=Id;
        this.Name = Name;
    }


    @Override
    public String toString() {

        return  "List Of Product {" +
                "Name='" + this.Name + '\'' +
                ", item ID=" + this.getItemscode(this.Name,this.Id)+
                ", Price=" + this.Price +
                ", qty=" + this.qty +
                ", Total=" + this.getTotal()
                ;
    }
}

