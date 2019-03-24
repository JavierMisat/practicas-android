package com.rodrigomisat.justjava;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    double coffeePrice = 5;
    double wippedCreamPrice = 1.5;
    double chocolatePrice = 2.0;
    boolean hasWippedCream = false;
    boolean hasChocolate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckBox checkWippedCream = (CheckBox) findViewById(R.id.wipped_cream);
        checkWippedCream.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    hasWippedCream = true;
                    Log.i("Añadió crema", "marcada");
                    initialize();
                } else {
                    hasWippedCream = false;
                    Log.i("Se quitó la crema", "desmarcada");
                    initialize();
                }
            }
        });

        CheckBox checkChocolate = (CheckBox) findViewById(R.id.chocolate);
        checkChocolate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    hasChocolate = true;
                    Log.i("Añadió Chocolate", "marcada");
                    initialize();
                } else {
                    hasChocolate = false;
                    Log.i("Se quitó el Chocolate", "desmarcada");
                    initialize();
                }
            }
        });

        initialize();
    }

    private void initialize() {
        displayOrder(quantity);
        displayPrice();
    }

    public void addWippedCream(View check) {
        Log.i("ENTRO Y FUNCIONANDO  : ", "OHH YEAHH FUNCIONA");
        boolean isTrue = ((CheckBox) findViewById(R.id.wipped_cream)).isChecked();
        Log.i("checado : ", String.valueOf(isTrue));
        if (isTrue == true) {
            hasWippedCream = true;
        } else {
            hasWippedCream = false;
        }
        displayOrder(quantity);
        displayPrice();
    }

    public void increment(View view) {
        if (quantity < 100) {
            quantity++;
            displayOrder(quantity);
            displayPrice();
        } else {
            Toast.makeText(MainActivity.this, R.string.more100, Toast.LENGTH_SHORT).show();
        }
    }

    public void decrement(View view) {
        if (quantity > 0) {
            quantity--;
            displayOrder(quantity);
            displayPrice();
        } else {
            Toast.makeText(MainActivity.this, R.string.less1, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        summaryOder();
        quantity = 0;
        displayOrder(quantity);
        displayPrice();
    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private double calculatePrice(int quantity) {
        double price = quantity * coffeePrice;
        return price;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayOrder(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private double displayPrice() {
        double price;
        if (hasWippedCream && !hasChocolate) {
            price = quantity * (coffeePrice + wippedCreamPrice);
        } else if (hasChocolate && !hasWippedCream) {
            price = quantity * (coffeePrice + chocolatePrice);
        } else if (hasChocolate && hasWippedCream) {
            price = quantity * (coffeePrice + chocolatePrice + wippedCreamPrice);
        } else {
            price = quantity * coffeePrice;
        }
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(price));
        return price;
    }

    /**
     * This method send email for order
    * */

    public void sendEmail(String[] addresses, String subject, String bodyEmail) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, bodyEmail);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    /**
     * This method display info summary to order
     */
    private void summaryOder() {

        String userMessage = "Error...";

        TextView viewCustomer = (TextView) findViewById(R.id.customer_name);
        TextView viewMailCustomer = (TextView) findViewById(R.id.mail_customer);
        CheckBox viewWippedCream = (CheckBox) findViewById(R.id.wipped_cream);
        CheckBox viewChocolate = (CheckBox) findViewById(R.id.chocolate);

        String customerName = viewCustomer.getText().toString();
        String mailCustomer[] = {viewMailCustomer.getText().toString()};

        if (quantity < 1) {
            Toast.makeText(MainActivity.this, R.string.less1, Toast.LENGTH_SHORT).show();
            return;
        }

        if (viewWippedCream.isChecked() && !viewChocolate.isChecked()) {
            hasWippedCream = true;
            hasChocolate = false;
            double totalOrder = displayPrice();
            userMessage = "Thanks for your purchase sr(a): " + customerName + "\n" +
                    "Wipped Cream: Yes" + "\n" +
                    "Chocolate: No" + "\n" +
                    "Wipped Cream Price:" + wippedCreamPrice + "\n" +
                    "Chocolate Price:" + chocolatePrice + "\n" +
                    "Quantity coffee: " + quantity + "\n" +
                    "Total order: $" + totalOrder + "\n";
        } else if (viewChocolate.isChecked() && !viewWippedCream.isChecked()) {
            hasWippedCream = false;
            hasChocolate = true;
            double totalOrder = displayPrice();
            userMessage = "Thanks for your purchase sr(a): " + customerName + "\n" +
                    "Wipped Cream: No" + "\n" +
                    "Chocolate: Yes" + "\n" +
                    "Wipped Cream Price:" + wippedCreamPrice + "\n" +
                    "Chocolate:" + chocolatePrice + "\n" +
                    "Quantity coffee: " + quantity + "\n" +
                    "Total order: $" + totalOrder + "\n";
        } else if (viewChocolate.isChecked() && viewWippedCream.isChecked()) {
            hasWippedCream = true;
            hasChocolate = true;
            double totalOrder = displayPrice();
            userMessage = "Thanks for your purchase sr(a): " + customerName + "\n" +
                    "Wipped Cream: Yes" + "\n" +
                    "Chocolate: Yes" + "\n" +
                    "Wipped Cream Price:" + wippedCreamPrice + "\n" +
                    "Chocolate Price:" + chocolatePrice + "\n" +
                    "Quantity coffee: " + quantity + "\n" +
                    "Total order: $" + totalOrder + "\n";
        } else {
            hasWippedCream = false;
            hasChocolate = false;
            double totalOrder = displayPrice();
            userMessage = "Thanks for your purchase sr(a): " + customerName + "\n" +
                    "Wipped Cream: no " + "\n" +
                    "Chocolate: no " + "\n" +
                    "Quantity coffee: " + quantity + "\n" +
                    "Total order: $" + totalOrder + "\n";
        }

        sendEmail(mailCustomer, "Order coffee for Mr." + customerName, userMessage);

//        Toast.makeText(MainActivity.this, userMessage, Toast.LENGTH_LONG).show();
        Log.i("Crema S/N ", String.valueOf(hasWippedCream));
        Log.i("Chocolate S/N ", String.valueOf(hasChocolate));
    }

}