package com.gj.cpe.bean;

/**
 * @author guojie
 * <p>
 */
public class BookBean {

    private String bookName;
    private double price;

    public BookBean(String bookName, double price) {
        this.bookName = bookName;
        this.price = price;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        BookBean comBookBean = (BookBean) obj;

        return comBookBean.bookName.equals(bookName);
    }

    @Override
    public int hashCode() {
        return bookName.hashCode();
    }

    @Override
    public String toString() {
        return "bookName:" + bookName + "  price:" + price;
    }
}
