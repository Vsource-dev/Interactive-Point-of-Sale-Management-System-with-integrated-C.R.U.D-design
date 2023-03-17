package withJOption;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Transaction extends Inventory {

		private final static int MAX_CART_SLOTS= 50;
		private final static int MAX_CART_FIELDS = 4;
		
		protected static String[][] cart = new String[MAX_CART_SLOTS][MAX_CART_FIELDS]; 
		protected static double[] salesTotalAmount = new double[MAX_PRODUCTS];
		protected static double[] soldTotalQuantity = new double[MAX_PRODUCTS];
		
		private static int  quantitySoldForTheDay = 0; 
		private static double totalSalesForTheDay = 0;
		
		protected static int index = 0;
		private static double total = 0;
		private static double change = 0;
		private static double subtotal;
		private static double discount;
		private static double price;

		protected static String header = "Name\tPrice\tQty\tSubtotal"; 
		
		protected static void selectProduct(String productName, int quantitySold) {
			
			productIndex = findProductIndexOf(productName);
			
			if(productIndex != -1) {
				
				int currentQuantity = Integer.parseInt(productList[productIndex][1]);
				
				if(currentQuantity >= quantitySold) {
					
					price = Double.parseDouble(productList[productIndex][2]);
					productList[productIndex][1] = Integer.toString(currentQuantity - quantitySold);
					
					subtotal = (quantitySold * price);
					total += subtotal;
					
				     // add products to cart
	                cart[index][0] = productName;
	                cart[index][1] = Double.toString(price);
	                cart[index][2] = Integer.toString(quantitySold);
	                cart[index][3] = Double.toString(subtotal);
	                index++;
					
					updateSales(productName, quantitySold);
					JOptionPane.showMessageDialog(null, "Product added to cart" , "Success", JOptionPane.INFORMATION_MESSAGE);
					
				}else errPrint("CANNOT SOLD | INSUFICIENT QUANTITY | ERROR 404");
				
			}else errPrint("CANNOT SOLD | NO PRODUCT FOUND | ERROR 404");
			
		} //selectProduct() ends ============================================================================>
		
		protected static void viewProductSelected() {

			StringBuilder container = new StringBuilder();
			
			container.append(header + "\n");
			
			for(int i = 0; i < cart.length; i++) {
				
			        if (cart[i][0] != null) {
			        	
			        	container.append(cart[i][0]).append("\t")
			                   .append(cart[i][1]).append("\t")
			                   .append(cart[i][2]).append("\t")
			                   .append(cart[i][3]).append("\t").append("\n");
			    
			        }
			        
			    }

			    container.append("Total: ").append(total);

			    JOptionPane.showMessageDialog(null, new JTextArea(container.toString()), "Selected Products", JOptionPane.PLAIN_MESSAGE);
			    
			} //viewProductSelected() ends ======================================================================>

		protected static void processPayment(double payment, boolean hasDiscountCard) {

			if(payment > total) {
				
				if(hasDiscountCard)discount = (0.1/total)*100;
				else discount = 0;	
				
				change = payment-(total-discount);
				
				JOptionPane.showMessageDialog(null, "OPENING CASH REGISTER");
				
				reciept(payment);
				
				reset();
				
			}else errPrint("INSUFICIENT AMOUNT");
			
		}
		
		protected static void reciept(double payment) {
			
			double valueAddedTax = ((0.12/total )*100);
			
			StringBuilder container = new StringBuilder();
			
			String asteriskLine = "************************************************************";
			String dashedLines = "---------------------------------------------------------------------------";
			
			container.append("------------------------Tindahan ni Charlie-------------------------\n");
			container.append(dashedLines+"\n").append(header + "\n");
			
		    for(int i = 0; i < cart.length; i++) {
		    	
		        if(cart[i][0] != null) {
		        	
		        	container.append(cart[i][0]).append("\t").append(cart[i][1]).append("php\t")
		                     .append(cart[i][2]).append("\t").append(cart[i][3]).append("\n");
		            
		        }
		        
		    }
		    
		    container.append(asteriskLine+"\n").append("V.A.T:\t").append(String.format("%.2f\n", valueAddedTax))
		    							  	   .append("Discount%:\t").append(String.format("%.2f\n", discount))
		    							  	   .append("Amount due:\t").append(String.format("%.2f\n", (total-discount)))
		    							  	   .append("Tendered:\t").append(String.format("%.2f\n", payment))
		    							  	   .append("Change:\t").append(String.format("%.2f\n", change));
		    
		    container.append(dashedLines+"\n").append("Thank you for purchasing!\n").append(dashedLines);
		    
		    JOptionPane.showMessageDialog(null, new JTextArea(container.toString()), "Printing receipt", JOptionPane.PLAIN_MESSAGE);

		} //printReceipt(); ends ============================================================================>
		
		public static void reset() {
			
			    for (int i = 0; i < cart.length; i++) {
			    	
			        cart[i] = new String[4];
			        
			    }
			    
			    index = 0;
			    subtotal = 0;
			    total  = 0;
			    change = 0;
			    
		} //reset(); ends ====================================================================================>
		
		protected static void salesForTheDay() {
			
		    if (productCount > 0) {
		    	
		    StringBuilder container = new StringBuilder();
		    	
		      container.append("Name\tsales\tsold" + "\n");
		        
		        for (int i = 0; i < productCount; i++) {
		        	
		        	container.append(productList[i][0]).append("\t")
		        			 .append(salesTotalAmount[i]).append("\t")
		        			 .append(soldTotalQuantity[i]).append("\t").append("\n");
		        	
		            quantitySoldForTheDay += soldTotalQuantity[i];
		            totalSalesForTheDay += salesTotalAmount[i];
		            
		        }
		        
		        container.append("Total Quantity sold: \t" + quantitySoldForTheDay).append("\nTotal Sales for the Day: \t" + totalSalesForTheDay);
		        JOptionPane.showMessageDialog(null, new JTextArea(container.toString()), "Sales audit for the day", JOptionPane.PLAIN_MESSAGE);
		        
		    } else  errPrint("No product found");
		    
		} //viewSaleForTheDay(); ends ========================================================================>
		
		private static void updateSales(String productName, int quantitySold) {
			
		    int index = findProductIndexOf(productName);
		    
		    if (index != -1) {
		    	
		        price = Double.parseDouble(productList[index][2]);
		        salesTotalAmount[index] += price * quantitySold;
		        soldTotalQuantity[index] += quantitySold;
		        
		    }
		    
		} //updateSales() ends ===============================================================================>
		
	}//End of class
