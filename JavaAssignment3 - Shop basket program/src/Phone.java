
public class Phone extends Product{
	
	private double phoneSize;
	private String phoneBrand;
	private String phoneColour;
	
	public Phone() {
		super();
	}
	
	public Phone(int productID, String productName, String productType, double productPrice, int productQuantity, double phoneSize, String phoneBrand, String phoneColour) {
		super(productID, productName, productType, productPrice, productQuantity);
		
		this.phoneSize = phoneSize;
		this.phoneBrand = phoneBrand;
		this.phoneColour = phoneColour;
	}

	public double getPhoneSize() {
		return phoneSize;
	}

	public String getPhoneBrand() {
		return phoneBrand;
	}

	public String getPhoneColour() {
		return phoneColour;
	}
	
}
