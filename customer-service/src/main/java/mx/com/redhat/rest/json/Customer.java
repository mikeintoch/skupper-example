package mx.com.redhat.rest.json;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Cacheable
public class Customer extends PanacheEntity {
    
    public String accountId;
    public String name;
    public String lastName;
    public double balance;

    public Customer(){

    }

}