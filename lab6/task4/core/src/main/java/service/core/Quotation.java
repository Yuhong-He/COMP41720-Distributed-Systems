package service.core;

import service.message.MySerializable;

/**
 * Class to store the quotations returned by the quotation services
 * 
 * @author Rem
 *
 */
public class Quotation implements MySerializable {
	public Quotation(String company, String reference, double price) {
		this.company = company;
		this.reference = reference;
		this.price = price;
	}

	public Quotation() {}

	public String company;
	public String reference;
	public double price;

	@Override
	public String toString() {
		return "Quotation{" +
				"company='" + company + '\'' +
				", reference='" + reference + '\'' +
				", price=" + price +
				'}';
	}
}
