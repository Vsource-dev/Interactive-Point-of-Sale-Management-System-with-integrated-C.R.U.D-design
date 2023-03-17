package withJOption;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Inventory {

	final protected static int MAX_PRODUCTS  = 100;	
	final protected static int MAX_PRODUCTS_FIELDS = 3;	
	
	protected static String [][] productList = new String[MAX_PRODUCTS][MAX_PRODUCTS_FIELDS]; //Main storage
	
	protected static int productCount = 0; 
	protected static int productIndex; //Variable for the product index when needed
	
		protected static void add(String productName, int productQuantity, double productPrice) {
			
			if (productCount < MAX_PRODUCTS) {
				
				productList[productCount][0] = productName;
				productList[productCount][1] = Integer.toString(productQuantity);
				productList[productCount][2] = Double.toString(productPrice);
				
				productCount++;
				
				JOptionPane.showMessageDialog(null, "Product #" + productCount + " created", "Product created sucessfully", JOptionPane.INFORMATION_MESSAGE);
				
			}else errPrint("CREATION FAILED | INSUFICIENT STORAGE | ERROR 404");
			
		} //addProduct(); ends ===============================================================================>
		
		protected static void delete(String productName) {
			
			productIndex = findProductIndexOf(productName);
			
			if (productIndex != -1) {
				
				for(int row = productIndex; row < productCount-1; row++) {
					
					productList[row][0] = productList[row + 1][0];
					productList[row][1] = productList[row + 1][1];
					productList[row][2] = productList[row + 1][2];
					
				}
				
				productCount--;
				JOptionPane.showMessageDialog(null, "Product \'" + productName + "\' deleted", "Product deleted sucessfully", JOptionPane.INFORMATION_MESSAGE);
				
			}else errPrint("DELETION FAILED | PRODUCT NOT FOUND | ERROR 404");
			
		} //deleteProduct(); ends =============================================================================>
		
		protected static void display() {
			
			StringBuilder container = new StringBuilder();
			
			if (productCount > 0) {
				
				container.append("Name\tQuantity\tPrice\n");
				
				for(int row = 0; row < productCount; row++) {
					
					container.append(productList[row][0]).append("\t")
							 .append(productList[row][1]).append("\t")
							 .append(productList[row][2]).append("\t").append("\n");
						
				}
				
				 JOptionPane.showMessageDialog(null, new JTextArea(container.toString()), "Current Available Products", JOptionPane.PLAIN_MESSAGE);

			}else errPrint("VIEW FAILED | NO PRODUCTS FOUND | ERROR 404");
			
		} //viewProducts(); ends ==============================================================================>
		
		protected static void search(String productName) {
			
			productIndex = findProductIndexOf(productName);
			
			if(productIndex != -1) {
				
				String quantity = productList[productIndex][1];
				String price = productList[productIndex][2];
				
				JOptionPane.showMessageDialog(null, "Product " + productName + " found" + " - Quantity: " + quantity + ", Price: " + price, "Product search sucessfully", JOptionPane.INFORMATION_MESSAGE);
				
			}else errPrint("SEARCH FAILED | NO PRODUCT FOUND | ERROR 404");
			
		} //searchProduct() ends ==============================================================================>
		
		protected static void update(String productName, int newQuantity, double newPrice) {
			
			productIndex = findProductIndexOf(productName);
			
			if(productIndex != -1) {
				
				if(newQuantity >= 0) productList[productIndex][1] = Integer.toString(newQuantity);
				if(newPrice >= 0) productList[productIndex][2] = Double.toString(newPrice);
				
				JOptionPane.showMessageDialog(null, "Product " + productName + " edited", "Product edited sucessfully", JOptionPane.INFORMATION_MESSAGE);
		
			}else errPrint("UPDATE FAILED | NO PRODUCT FOUND | ERROR 404");
			
		} //updateProduct() ends ==============================================================================>
		
		protected static int findProductIndexOf(String productName) {
			
			for(int row = 0; row < productCount; row++) {
				
				if(productList[row][0].equalsIgnoreCase(productName)) return row;
				
			}
			
			return -1; //if method does not exist in the array returns this value
			
		} //searcProductIndex(); ends =========================================================================>
		
		static void errPrint(String word) { JOptionPane.showMessageDialog(null, word, "ERROR", JOptionPane.ERROR_MESSAGE);
			
	}
		
} //End of class 
