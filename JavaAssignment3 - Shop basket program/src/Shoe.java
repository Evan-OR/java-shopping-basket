
public class Shoe extends Product{
	
	private double shoeSize;
	private String shoeBrand;
	private String shoeColour;
	
	public Shoe() {
		super();
	}
	public Shoe(int productID, String productName, String productType, double productPrice, int productQuantity, double shoeSize, String shoeBrand, String shoeColour) {
		super(productID, productName, productType, productPrice, productQuantity);

		this.shoeSize = shoeSize;
		this.shoeBrand = shoeBrand;
		this.shoeColour = shoeColour;
	}
	
	public double getShoeSize() {
		return shoeSize;
	}
	public String getShoeBrand() {
		return shoeBrand;
	}
	
	public String getShoeColour() {
		return shoeColour;
	}
}
