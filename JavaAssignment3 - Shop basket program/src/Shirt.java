
public class Shirt extends Product{
	private String shirtSize;
	private String shirtBrand;
	private String shirtColour;
	
	public Shirt() {
		super();
	}
	
	public Shirt(int productID, String productName, String productType, double productPrice, int productQuantity, String shirtSize, String shirtBrand, String shirtColour) {
		super(productID, productName, productType, productPrice, productQuantity);
		
		this.shirtSize = shirtSize;
		this.shirtBrand = shirtBrand;
		this.shirtColour = shirtColour;
	}

	public String getShirtSize() {
		return shirtSize;
	}

	public String getShirtBrand() {
		return shirtBrand;
	}

	public String getShirtColour() {
		return shirtColour;
	}
}
