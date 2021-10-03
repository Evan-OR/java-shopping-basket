import java.io.Serializable;

public class Product implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int productID;
	private String productName;
	private double productPrice;
	private int productQuantity;
	private String productType;
	
	
	public Product() {
		
	}

	public Product(int productID, String productName, String productType, double productPrice, int productQuantity) {
		this.productID = productID;
		this.productPrice = productPrice;
		this.productName = productName;
		this.productQuantity = productQuantity;
		this.productType = productType;
	}

	public int getProductID() {
		return productID;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public String getProductName() {
		return productName;
	}

	public int getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(int quantity) {
		productQuantity = quantity;
	}
	
	public String getProductType() {
		return productType;
	}

	public void printDetails() {
		System.out.println("ID: " + productID + " " + productName + " €" + productPrice + "\t Quantity: "+productQuantity);
	}
}
