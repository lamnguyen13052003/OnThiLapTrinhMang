package de_22_23.bai2.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.List;
import java.util.StringTokenizer;

import de_22_23.model.Product;

public class ThreadClient extends Thread {
	private BufferedReader reader;
	private PrintWriter writer;
	private ProductDAO productDAO;
	private Socket client;

	public ThreadClient(Socket client, ProductDAO productDAO) throws UnsupportedEncodingException, IOException {
		this.client = client;
		reader = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
		writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"), true);
		this.productDAO = productDAO;
	}

	@Override
	public void run() {
		writer.println("Welcome to my server...");
		String line;
		try {
			while (true) {
				line = reader.readLine();
				if (line == null || line.toLowerCase().equals("exit"))
					break;
				action(line);
			}

			reader.close();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void action(String line) {
		StringTokenizer tk = new StringTokenizer(line, "\t");
		String command = tk.nextToken().toLowerCase();
		switch (command) {
		case "add" -> {
			if (tk.countTokens() != 4) {
				writer.println("Lỗi cú pháp!");
				return;
			}
			try {
				int id = Integer.parseInt(tk.nextToken());
				String name = tk.nextToken();
				int quantity = Integer.parseInt(tk.nextToken());
				double price = Double.parseDouble(tk.nextToken());
				Product product = new Product(id, name, quantity, price);
				writer.println(productDAO.add(product));
			} catch (NumberFormatException e) {
				writer.println("Lỗi ép kiểu dữ liệu");
			}
		}
		case "sell" -> {
			if (tk.countTokens() != 1) {
				writer.println("Lỗi cú pháp!");
				return;
			}
			try {
				writer.println(productDAO.sell(Integer.parseInt(tk.nextToken())));
			} catch (NumberFormatException e) {
				writer.println("Lỗi ép kiểu dữ liệu");
			}
		}
		case "update" -> {
			if (tk.countTokens() != 2) {
				writer.println("Lỗi cú pháp!");
				return;
			}
			try {
				int id = Integer.parseInt(tk.nextToken());
				double newPrice = Double.parseDouble(tk.nextToken());
				writer.println(productDAO.update(id, newPrice));
			} catch (NumberFormatException e) {
				writer.println("Lỗi ép kiểu dữ liệu");
			}
		}
		case "find" -> {
			if (tk.countTokens() != 1) {
				writer.println("Lỗi cú pháp!");
				return;
			}
			String name = tk.nextToken();
			List<Product> products = productDAO.find(name);
			if (!products.isEmpty())
				for (Product product : products)
					writer.println(product);
			else
				writer.println("Không tìm thấy sản phẩm phù hợp!");
		}
		default -> {
			writer.println("Lỗi cú pháp!");
		}
		}
	}
}
