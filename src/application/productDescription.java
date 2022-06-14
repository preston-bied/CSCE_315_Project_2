package application;

public class productDescription {
	String productName;
	String salePrice;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	public productDescription(String productName, String salePrice) {
		this.productName = productName;
		this.salePrice = salePrice;
	}
	
}
