package org.fasttrackit.onlineshop.domain;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cart {

    @Id
    private long id;

    //    relatie one-to-on mai jos -> adica un customer pentru un cos de cumparaturi (cosul ii ia ID-ul)
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Customer customer;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "cart_products",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))

    private Set<Product> products = new HashSet<>();

//    metoda de adaugare a unui produs in Cart.
    public void addToCart(Product product) {
        products.add(product);
//        associating current cart with the received product
        product.getCarts().add(this);
    }

//    aici removal pentru product din cart
    public void removeFromCart(Product product) {
        products.remove(product);
        product.getCarts().remove(this);
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cart cart = (Cart) o;

        return id == cart.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
