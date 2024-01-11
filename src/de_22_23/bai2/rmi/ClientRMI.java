package de_22_23.bai2.rmi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.StringTokenizer;

import de_22_23.model.Product;

public class ClientRMI {
	private BufferedReader userIn;
	private Registry client;
	private IProductService productService;

	public ClientRMI(String host, int port) throws NotBoundException, IOException {
		client = LocateRegistry.getRegistry(host, port);
		productService = (IProductService) client.lookup("PRODUCT_SERVICE");
		System.out.println(productService.greenting());
		userIn = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		run();
	}

	private void run() throws IOException {
		String line = null;
		while (true) {
			System.out.print("Mời bạn nhập lệnh của mình: ");
			line = userIn.readLine();
			if (line == null || line.toLowerCase().equals("exit"))
				break;
			action(line);
		}
		System.out.println(productService.exit());
	}

	private void action(String line) {
		StringTokenizer tk = new StringTokenizer(line, "\t");
		String command = tk.nextToken().toLowerCase();
		switch (command) {
		case "add"-> {
			if(tk.countTokens() != 4) {
				System.out.println("Lỗi cú pháp!");
				return;
			}
			try {
				int id = Integer.parseInt(tk.nextToken());
				String name = tk.nextToken();
				int quantity =  Integer.parseInt(tk.nextToken());
				double price = Double.parseDouble(tk.nextToken());
				Product product = new Product(id, name, quantity, price);
				System.out.println(productService.add(product));
			}catch (NumberFormatException e) {
				System.out.println("Lỗi ép kiểu dữ liệu");
			} catch (RemoteException e) {
				System.out.println(e.getMessage());
			}
		}
		case "sell"-> {
			if(tk.countTokens() != 1) {
				System.out.println("Lỗi cú pháp!");
				return;
			}
			try {
				System.out.println(productService.sell(Integer.parseInt(tk.nextToken())));
			}catch (NumberFormatException e) {
				System.out.println("Lỗi ép kiểu dữ liệu");
			} catch (RemoteException e) {
				System.out.println(e.getMessage());
			}
		}
		case "update"-> {
			if(tk.countTokens() != 2) {
				System.out.println("Lỗi cú pháp!");
				return;
			}
			try {
				int id = Integer.parseInt(tk.nextToken());
				double newPrice = Double.parseDouble(tk.nextToken());
				System.out.println(productService.update(id, newPrice));
			}catch (NumberFormatException e) {
				System.out.println("Lỗi ép kiểu dữ liệu");
			} catch (RemoteException e) {
				System.out.println(e.getMessage());
			}
		}
		case "find"-> {
			if(tk.countTokens() != 1) {
				System.out.println("Lỗi cú pháp!");
				return;
			}
			String name = tk.nextToken();
			try {
				List<Product> products = productService.find(name);
				if(!products.isEmpty()) for(Product product : products) System.out.println(product);
				else System.out.println("Không tìm thấy sản phẩm phù hợp!");
			} catch (RemoteException e) {
				System.out.println(e.getMessage());
			}
		}
		default ->{
			System.out.println("Lỗi cú pháp!");
		}
		}
	}
	
	public static void main(String[] args) throws NotBoundException, IOException {
		ClientRMI client = new ClientRMI("localhost", 1305);
	}
}
