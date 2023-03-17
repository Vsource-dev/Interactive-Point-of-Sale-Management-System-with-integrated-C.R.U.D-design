package withJOption;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class POSMachine extends Inventory{

		/*
		 * 
		 * "Interactive Point of Sale Management System with Integrated C.R.U.D Design"
		 * 	- A case study presented by GROUP 5: Charlie_Cesario, Jhon_Mark_Flores, Stanley_Gonzales, Christian_Petilo, R-Jay_Santos, Genaro_Verastigue 
		 *   Copyright (C) 2023 Stanley Gonzales - All Rights Reserved
		 *   You may use, distribute and modify this code 
		 *   //Presented in: TSU - CCS S.I | March 16, 2023  
		 *   
		 */
		
		private static int choices;
		private static boolean condition;
		private final static String adminPassword = "admin";
		private final static String adminUsername = "admin";
		private static int ATTEMPTS = 3;
		
		public static void main(String[] args) { 
			
			JOptionPane.showMessageDialog(null, "Opening Program");
	
			do {
				
				//Security Verification			
				String masterUser =  JOptionPane.showInputDialog(null, "Enter username");
				String masterPass =  JOptionPane.showInputDialog(null, "Enter password");
				if(masterUser == null||masterPass == null) return;
				
				if(masterUser.equals(adminUsername) && masterPass.equals(adminPassword)) { 
					
						condition = true;
				
						do{	
							
							switch(mainWizardInterface()) {
							
							case 1: 
								
								transactionWizardInterface();
								
								break;
							case 2: 
								
								inventoryWizardInterface();
								
								break;
							case 3: 
								
								promptExit();
								
								break;
						
							default:
								
								error404();
								
								break;
							}
							
						}while(true);
					
				}else{
					
					ATTEMPTS--;
					errPrint("WRONG CREDENTIALS | TRY AGAIN | ATTEMPTS LEFT: " + ATTEMPTS);
					
				}
				
			}while(ATTEMPTS != 0);
			
		}//Main method ends
		
		//Interface Methods ------------------------------------------------------------------------------------------------------------------------>
		
		protected static int mainWizardInterface() {
			
		    String[] options = { "Transaction", "Inventory", "Exit" };
		    choices = JOptionPane.showOptionDialog(null, "Welcome admin!", "Interactive point of sale management system with integrated C.R.U.D design", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
		    return choices + 1;
			
		}

		protected static void inventoryWizardInterface() {

			condition = true;
			
			do {	
				
				String[] options = { "add", "view", "delete","search","update","exit"};
				choices = JOptionPane.showOptionDialog(null, "Product Created: " + productCount , "P.O.S Management System", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
				
				switch (choices+1) {
				case 1:
					
					addProduct();
					
					break;
				case 2:
					
					displayProduct();
					
					break;
				case 3:
					
					 deleteProduct();
					
					break;
				case 4:
					
					searchProduct();
					
					break;
				case 5:
					
					updateProduct();
					
					break;
				case 6:
					
					exit();
					
					break;
				default: 
					
					error404();
					
					break;
				}

			}while(condition);

		}
		
		protected static void transactionWizardInterface() {
				
				condition = true;
				
				do {
					
				String[] options = { "sell", "view", "process","exit" };
				choices = JOptionPane.showOptionDialog(null, "Products in cart: " + Transaction.index , "P.O.S Management System", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
							
					switch (choices+1) {
					case 1:
						
						addToCart();
						
						break;
					case 2:
						
						viewCart();
						
						break;
					case 3:
						
						processPayment();
						
						break;
					case 4:
						
						exit();
						
						break;
						
					default: 
						
						error404();
						
						break;
					}
					
			}while(condition);
		
		} //transactionInterface() ends ==============================================================================>
		
		protected static void promptExit() {
			
			Transaction.salesForTheDay(); //Method computes for the whole sales for the day
			JOptionPane.showMessageDialog(null, "Program shutting down, Have a great day..." , "Bye-bye", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0); //Ends the program as a whole
			
		}
		
		public static void error404() {
			
			System.exit(0); 
			
		}
		
		//Transaction Methods ------------------------------------------------------------------------------------------------------------------------>
		
		private static void addToCart() {
			
			int productSold = 0;
			
	        JComboBox<String> comboBox = new JComboBox<String>();
	        
	        for (int i = 0; i < productCount; i++) comboBox.addItem(productList[i][0]);
	        
	        int result = JOptionPane.showConfirmDialog(null, comboBox, "Select a product", JOptionPane.OK_CANCEL_OPTION);
	        
	        if (result == JOptionPane.OK_OPTION) {
	        	
	            String selectedProduct = (String) comboBox.getSelectedItem();
	            
	            String inputProductSold = JOptionPane.showInputDialog(null, "Quantity of product: ");
	            
				if(inputProductSold != null && !inputProductSold.isEmpty())productSold = Integer.parseInt(inputProductSold);
				else return;
				
	        	Transaction.selectProduct(selectedProduct ,productSold);
	        	
	        }
				
		} //addToCart() ends =========================================================================================>
		
		private static void viewCart() {
			
			Transaction.viewProductSelected();
			
		} //viewCart() ends ==========================================================================================>
		
		private static void processPayment() {
			
			double payment = 0;
			
			String inputPayment = JOptionPane.showInputDialog(null, "Enter money amount");
					
			if(inputPayment != null && !inputPayment.isEmpty())payment = Double.parseDouble(inputPayment);
			else return;
			
			boolean hasDiscountCard = JOptionPane.showConfirmDialog(null, "Has Discount Card","Discount Manager",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
			
			Transaction.processPayment(payment, hasDiscountCard);
			
			
		} //processPayment() ends ======================================================================================>
		
		//Inventory Methods -------------------------------------------------------------------------------------------------------------------------->
		
		private static void addProduct() {
			
			int numOfProducts = 0;
			int quantity = 0;
			double price = 0;
			
			String input =JOptionPane.showInputDialog(null,"Quantity of products: ");
			
			if(input != null && !input.isEmpty()) {
				
				numOfProducts = Integer.parseInt(input);
				
			}else return;
			
			for(int i = 0; i < numOfProducts; i++) {
				
				String name = JOptionPane.showInputDialog(null,"Enter product name");
				
				if(name == null) return;
				
				String inputQuantity = JOptionPane.showInputDialog(null, "Enter product quantity");
				
				if(inputQuantity != null) quantity = Integer.parseInt(inputQuantity);
				else return;
				
				String inputPrice =JOptionPane.showInputDialog(null, "Enter amount: ");;
				
				if(inputPrice != null)  price = Double.parseDouble(inputPrice);
				else return;
				
				add(name, quantity, price);

			}
			
		} //addProduct() ends ========================================================================================>
		
		private static void displayProduct() {
			
			display();
		
		} //displayProduct() ends ====================================================================================>
		
		private static void deleteProduct() {
		
			String name = JOptionPane.showInputDialog(null,"Enter data to be deleted");
			
			if(name == null) return;
			
			delete(name);
			
		} //deleteProduct() ends =====================================================================================>
		
		private static void searchProduct() {
			
			String name = JOptionPane.showInputDialog(null,"Enter data to be searched");
			
			if(name == null) return;
			
			search(name);
			
		} //searchProduct() ends =====================================================================================>
		
		private static void updateProduct() {

			int quantity = 0;
			double price = 0;
			
			String name = JOptionPane.showInputDialog(null,"Enter product to be updated");
			
			if(name == null) return;
			
			String inputQuantity = JOptionPane.showInputDialog(null, "Enter new quantity");
			
			if(inputQuantity != null && !inputQuantity.isEmpty()) quantity = Integer.parseInt(inputQuantity);
			else return;
			
			String inputPrice = JOptionPane.showInputDialog(null, "Enter new price");
			
			if (inputPrice != null && !inputPrice.isEmpty()) price = Double.parseDouble(inputPrice);
			else return;
			
			update(name, quantity, price);

		} //updateProduct() ends =====================================================================================>
		
		// Additional Methods ------------------------------------------------------------------------------------------------------------------------->
		
		static boolean exit() {condition = false; return condition;}
		
	} //End of class
