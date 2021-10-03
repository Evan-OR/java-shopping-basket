import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Application {
	
	//Application 
	static int centralProductID = 100;
	static ArrayList<Product> productList = new ArrayList<Product>();
	static ArrayList<Product> cartItems = new ArrayList<Product>();
	static DecimalFormat moneyFormat = new DecimalFormat("€0.00");
	
	//Input & Output
	static Scanner scan = new Scanner(System.in);
	static InputStreamReader textInput = new InputStreamReader(System.in);
	static BufferedReader br = new BufferedReader(textInput);
	
	//Statistics
	static int numOfTransactions = 0;
	static double averageSaleValue = 0;
	static double totalVauleOfSales = 0;
	
	public static void main(String[] args) {
		
		File f = new File("data.ser");
		if(f.exists()) {
			try {
				loadData();
			} catch (Exception e) {
				System.out.println("Error loading file");
				//if file fails to load just use populateShop() instead
				populateShop();
			}
			Menu();
		}else {
			populateShop();
			Menu();
		}
	}


	static void populateShop() {
		//Shoes
		Shoe s1 = new Shoe(centralProductID, "Yeezy", "Shoe", 29.99, 10, 10, "Adidas", "Black");
		productList.add(s1);
		centralProductID++;
		centralProductID++;
		Shoe s2 = new Shoe(centralProductID, "SBs", "Shoe", 29.99, 10, 10, "Nike", "White");
		productList.add(s2);
		centralProductID++;
		
		//Phones
		Phone p1 = new Phone(centralProductID, "S20", "Phone", 849.99, 40, 6, "Samsung", "White");
		productList.add(p1);
		centralProductID++;
		Phone p2 = new Phone(centralProductID, "Pixel 3", "Phone", 899.99, 30, 5.8, "Google", "Black");
		productList.add(p2);
		centralProductID++;
		
		//Shirts
		Shirt sh1 = new Shirt(centralProductID, "Logo Shirt", "Shirt", 120, 40, "Medium", "Supreme", "White");
		productList.add(sh1);
		centralProductID++;
		Shirt sh2 = new Shirt(centralProductID, "Tie dye Shirt", "Shirt", 199.99, 40, "Large", "Gucci", "Green");
		productList.add(sh2);
		centralProductID++;
	}
	
	static void ViewProductsMenu() {
		System.out.println("-----------------------------------------------");
		boolean found = false;
		
		viewProducts(productList);
		
		System.out.println("");
		System.out.println("Select the item you would like to add to cart by ID");
		System.out.println("Press 1 to return to main menu");
		
		int choice = scan.nextInt();
		//Check if user just wants return to main menu
		if(choice == 1) {
			Menu();
		}
		
		for (Product p : productList) {
			if(p.getProductID() == choice) {
				found = true;
				System.out.println("You selected " + p.getProductName());
				
				System.out.println("Please Enter the Quantity you would like to purchase");
				choice = scan.nextInt();
				
				if(choice > p.getProductQuantity()) {
					System.out.println("The Quantity you selected was more than amount we have in stock please try again");
					ViewProductsMenu();
				}else {
					//Create the product to add to the cart
					Product newProduct = new Product();
					
					if (p.getProductType().equals("Phone")) {
						newProduct = copyPhone(p, choice);
					}else if (p.getProductType().equals("Shoe")) {
						newProduct = copyShoe(p, choice);
					}else if (p.getProductType().equals("Shirt")) {
						newProduct = copyShirt(p, choice);
					}
					
					cartItems.add(newProduct);
					//Remove quantity from stock
					p.setProductQuantity(p.getProductQuantity() - choice);
					
					System.out.println(newProduct.getProductQuantity() + " Item(s) Added to Cart!");
					ViewProductsMenu();
				}
			}
		}
		
		if(!found) {
			System.out.println(choice + " is an invalid inout");
			System.out.println("Please enter a valid input");
			ViewProductsMenu();
		}
	}
	
	static Phone copyPhone(Product toCopy, int quantity) {
		Phone phoneToCopy = (Phone)toCopy;
		Phone newPhone = new Phone(phoneToCopy.getProductID(), phoneToCopy.getProductName(), "Phone", phoneToCopy.getProductPrice(), quantity, phoneToCopy.getPhoneSize(), phoneToCopy.getPhoneBrand(), phoneToCopy.getPhoneColour());
		return newPhone;
	}
	static Shoe copyShoe(Product toCopy, int quantity) {
		Shoe shoeToCopy = (Shoe)toCopy;
		Shoe newShoe = new Shoe(shoeToCopy.getProductID(), shoeToCopy.getProductName(), "Shoe", shoeToCopy.getProductPrice(), quantity, shoeToCopy.getShoeSize(), shoeToCopy.getShoeBrand(), shoeToCopy.getShoeColour());
		return newShoe;
	}
	static Shirt copyShirt(Product toCopy, int quantity) {
		Shirt shirtToCopy = (Shirt)toCopy;
		Shirt newShirt = new Shirt(shirtToCopy.getProductID(), shirtToCopy.getProductName(), "Shirt", shirtToCopy.getProductPrice(), quantity, shirtToCopy.getShirtSize(), shirtToCopy.getShirtBrand(), shirtToCopy.getShirtColour());
		return newShirt;
	}
	
	static void ViewCart() {
		System.out.println("-----------------------------------------------");

		if (cartItems.size() > 0) {
			viewProducts(cartItems);
		}else {
			System.out.println("Your Cart is Empty");
		}
		
		//Return to Menu
		System.out.println("Press 1 to return to main menu");
		int choice = scan.nextInt();
		if(choice == 1) {
			Menu();
		}
	}
	
	static void Checkout() {
		
		System.out.println("-----------------------------------------------");
		System.out.println("These are the items your are purchasing");
		if (cartItems.size() > 0) {
			//Print items
			viewProducts(cartItems);
			
		}else {
			System.out.println("Your Cart is Empty");
		}
		
		System.out.println("Press 1 to Proceed to payment");
		System.out.println("Press 2 to Return to main menu");
		
		int choice = scan.nextInt();
		
		switch(choice) {
			case 1 :
				Payment(getTotalCartPrice());
				break;
			case 2 :
				Menu();
				break;
			default:
				System.out.println("Please Enter a valid input");
				Checkout();
				break;
		}
	}
	
	static void Payment(double price) {
		double amountPayed = 0;
		System.out.println("-----------------------------------------------");
		
		while(amountPayed < price) {
			System.out.println("Please enter amount to pay | Remaining Cost is €" + (moneyFormat.format(price-amountPayed)));
			double amount = scan.nextDouble();
			amountPayed += amount;
		}
		//Check if user needs change
		if(amountPayed-price > 0) {
			System.out.println("Your change is " + moneyFormat.format(amountPayed - price));
		}
		System.out.println("Thank you for your pruchase");
		
		//Statistics
		numOfTransactions++;
		totalVauleOfSales += price;
		
		//Clear all item from cart and bring user back to main menu
		cartItems.clear();
		Menu();
	}
	
	static double getTotalCartPrice() {
		int totalPrice = 0;
		for (Product p : cartItems) {
			p.printDetails();
			totalPrice += p.getProductPrice() * p.getProductQuantity();
		}
		return totalPrice;
	}
	
	static void viewProducts(ArrayList<Product> list) {
		for (Product p : list) {
			System.out.println("");
			System.out.println("ID 	\t : " + p.getProductID());
			
			//Child information
			if(p.getProductType().equals("Shoe")) {
				Shoe sh = (Shoe)p;
				System.out.println("Name 	\t : " + sh.getShoeBrand() + " - " + p.getProductName());
				System.out.println("Shoe Size \t : " + sh.getShoeSize() + " UK");
				System.out.println("Colour 	\t : " + sh.getShoeColour());
			}else if (p.getProductType().equals("Phone")) {
				Phone ph = (Phone)p;
				System.out.println("Name 	\t : " + ph.getPhoneBrand() + " " + p.getProductName());
				System.out.println("Screen Size \t : " + ph.getPhoneSize() + "\"");
				System.out.println("Colour 	\t : " + ph.getPhoneColour());
			}else if (p.getProductType().equals("Shirt")) {
				Shirt sh = (Shirt)p;
				System.out.println("Name 	\t : " + sh.getShirtBrand() + " " + p.getProductName());
				System.out.println("Size 	\t : " + sh.getShirtSize());
				System.out.println("Colour 	\t : " + sh.getShirtColour());
			}
			
			System.out.println("Price 	\t : " + moneyFormat.format(p.getProductPrice()));
			System.out.println("Quantity \t : " + p.getProductQuantity());
		}
	}
	
	static void Statistics() {
		System.out.println("-----------------------------------------------");
		System.out.println("Total Number of transactions : \t" + numOfTransactions);
		System.out.println("Average Number of transactions : \t" + totalVauleOfSales/numOfTransactions);
		System.out.println("Total value of transactions : \t" + totalVauleOfSales);
		
		//Return to Menu
		System.out.println("Press 1 to return to main menu");
		int choice = scan.nextInt();
		if(choice == 1) {
			Menu();
		}
	}
	
	static void ProductMenu() {
		System.out.println("-----------------------------------------------");
		System.out.println("Press 1 to Create New Product");
		System.out.println("Press 2 to Add Restock to an Existing Product");
		System.out.println("Press 3 to Return to Main Menu");
		
		String choice = scan.next();
		
		switch(choice) {
			case "1":
				productToCreate();
				break;
			case "2":
				restockItem();
				break;
			case "3":
				Menu();
				break;
				default:
					System.out.println("Plesae enter a vaild input");
					ProductMenu();
					break;
		}
	}
	
	static void productToCreate() {
		System.out.println("-----------------------------------------------");
		System.out.println("Please Choose the type of product you want to create");
		System.out.println("Press 1 to create a New PHONE Product");
		System.out.println("Press 2 to create a New SHOE Product");
		System.out.println("Press 3 to create a New SHIRT Product");
		String choice = scan.next();
		switch(choice) {
		case "1":
			creatingProduct("Phone");
			break;
		case "2":
			creatingProduct("Shoe");
			break;
		case "3":
			creatingProduct("Shirt");
			break;
			default:
				System.out.println("Please enter a valid input");
				productToCreate();
				break;
		}
	}
	
	static void creatingProduct(String type) {
		try {
			System.out.println("Please enter the name of the product");
			String productName = br.readLine();
			System.out.println("Please enter the name of the brand");
			String brandName = br.readLine();
			System.out.println(sizeType(type));
			String size= br.readLine();
			System.out.println("Please Enter the colour of the product");
			String colour = br.readLine();
			System.out.println("Enter a price for the product");
			double price = scan.nextDouble();
			System.out.println("Enter how much stock you want this item to have");
			int stock = scan.nextInt();
			
			//Check what object type it is to see if the size variable needs to be parsed to a double or not
			if(type.equals("Phone")) {
				Phone phone = new Phone(centralProductID, productName, type, price, stock, Double.parseDouble(size), brandName, colour);
				centralProductID++;
				productList.add(phone);
			}else if (type.equals("Shoe")) {
				Shoe shoe = new Shoe(centralProductID, productName, type, price, stock, Double.parseDouble(size), brandName, colour);
				centralProductID++;
				productList.add(shoe);
			}else {
				Shirt shirt = new Shirt(centralProductID, productName, type, price, stock, size, brandName, colour);
				centralProductID++;
				productList.add(shirt);
			}
			System.out.println("Product Added Was Added To Shop!");
			Menu();
		} catch (Exception e) {
			System.out.println("Error making product. Please try again");
			Menu();
		}
	}
	
	static String sizeType(String type) {
		if(type.equals("Phone")) {
			return "Please enter a Integer or Decimal number (e.g 5.5, 6.2, etc) for the screen size";
		}else if (type.equals("Shoe")) {
			return "Please enter a Integer number (e.g 8, 11, etc) for the shoe size";
		}else{
			return "Please enter a clothing size (e.g small, medium, large)";
		}
	}
	
	static void restockItem() {
		System.out.println("-----------------------------------------------");
		viewProducts(productList);
		
		System.out.println("Please Enter the ID of the item you would like to add stock to");
		boolean found = false;
		int choice = scan.nextInt();
		
		for (Product p : productList) {
			if(choice == p.getProductID()) {
				found = true;
				System.out.println("Enter how much more stock would you like to add");
				int amount = scan.nextInt();
				p.setProductQuantity(p.getProductQuantity() + amount);
				System.out.println(amount + " added to stock");
			}
		}
		if(!found) {
			System.out.println("No product found with that id. Please try again");
			restockItem();
		}
		Menu();
	}
	
	static void Menu() {
		System.out.println("-----------------------------------------------");
		System.out.println("Press 1 to Create or Restock a Product");
		System.out.println("Press 2 to View/Buy Products");
		System.out.println("Press 3 to View Cart");
		System.out.println("Press 4 to Checkout");
		System.out.println("Press 5 to View Statistics");
		System.out.println("Press 6 to Save Product Information");
		System.out.println("Press X to Close Application");
		
		String choice = scan.next();

		switch (choice) {
			case "1":
				ProductMenu();
				break;
			case "2":
				ViewProductsMenu();
				break;
			case "3":
				ViewCart();
				break;
			case "4":
				Checkout();
				break;
			case "5":
				Statistics();
				break;
			case "6":
				try {
					saveData();
				} catch (Exception e) {
					System.out.println("Error saving file");
				}
				break;
			case "x": case "X":
				System.out.println("Closing Application");
				System.exit(0);
				break;
			default:
				System.out.println("Please Enter a Valid Input!");	
		}
		Menu();
	}


	static void saveData() throws Exception {
		FileOutputStream exportFile = new FileOutputStream("data.ser");
		ObjectOutputStream writer = new ObjectOutputStream(exportFile);
		
		writer.writeObject(cartItems);
		System.out.println("File Saved");
		writer.close();
	}
	
	private static void loadData() throws Exception {
		FileInputStream importFile = new FileInputStream("data.ser");
		ObjectInputStream reader = new ObjectInputStream(importFile);
		
		productList = (ArrayList<Product>) reader.readObject();
		centralProductID += productList.size();
		System.out.println("File Loaded");
		reader.close();
	}
	
}