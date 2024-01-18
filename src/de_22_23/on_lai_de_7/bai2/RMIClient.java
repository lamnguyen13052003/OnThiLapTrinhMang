package de_22_23.on_lai_de_7.bai2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class RMIClient {
	public static void main(String[] args) throws NotBoundException, IOException {
		Registry client = LocateRegistry.getRegistry(54321);
		ProductService productService = (ProductService) client.lookup("PRODUCT_SERVICE");
		System.out.println(productService.greeting());
		BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));

		String line;
		while (true) {
			System.out.print("Nhập lệnh của bạn: ");
			line = userIn.readLine();
			if (line == null || line.equals("EXIT"))
				break;

			StringTokenizer tk = new StringTokenizer(line, "\t");
			switch (tk.nextToken()) {
			case "ADD" -> {
				if (tk.countTokens() != 4) {
					System.out.println("Lệnh không phù hợp");
					continue;
				}
				try {
					int id = Integer.parseInt(tk.nextToken());
					String name = tk.nextToken();
					int quantity = Integer.parseInt(tk.nextToken());
					double price = Double.parseDouble(tk.nextToken());
					Product product = new Product();
					product.id = id;
					product.name = name;
					product.quantity = quantity;
					product.price = price;
					System.out.println(productService.add(product));
				} catch (NumberFormatException e) {
					System.out.println("Lệnh không phù hợp");
				}
			}
			case "SELL" -> {
				try {
					List<Integer> listId = new ArrayList<>();
					while(tk.countTokens() > 0) {
						int id = Integer.parseInt(tk.nextToken());
						listId.add(id);
					}
					productService.sell(listId);
				} catch (NumberFormatException e) {
					System.out.println("Lệnh không phù hợp");
				}
			}
			case "UPDATE" -> {
				if (tk.countTokens() != 2) {
					System.out.println("Lệnh không phù hợp");
					continue;
				}
				try {
					int id = Integer.parseInt(tk.nextToken());
					double price = Double.parseDouble(tk.nextToken());
					System.out.println(productService.update(id, price));
				} catch (NumberFormatException e) {
					System.out.println("Lệnh không phù hợp");
				}

			}
			case "FIND" -> {
				if (tk.countTokens() != 1) {
					System.out.println("Lệnh không phù hợp");
					continue;
				}
				String name = tk.nextToken();
				System.out.println(productService.find(name));
			}
			default -> System.out.println("Lệnh không phù hợp");
			}
		}
	}
}
