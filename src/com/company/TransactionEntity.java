
import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable{
	private String name;
	private String description;
	private double price;
	private int quantity;
	private Date time;
	
	//create a new transaction
	public Transaction(String name, String description, double price,int quantity, Date time){
		this.name=name;
		this.description=description;
		this.price=price;
		this.quantity=quantity;
		this.time =time;
		
	}

	public String toString() {
        return "Transaction [name=" + name + ", description=" + description + ", price=" + price +", quantity="+quantity+ ", Date=" + time +"]";
	}
	
	
	//return name of the transaction
	public String getName() {
		return name;
	}
	
	//return description of the transaction
	public String getDescription() {
		return description;
	}
	
	//return price of the transaction
	public double getPrice() {
		return price;
	}
	
	//return quantity of the transaction
	public int getQuantity() {
		return quantity;
	}
	
	//return date of the transaction
	public Date getTime() {
		return time;
	}
	
}
