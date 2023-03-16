package org.example;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class UpperToLow {

        public static void main(String[] args) throws IOException {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the amount to convert: ");
            double amount = scanner.nextDouble();

            System.out.print("Enter the currency to convert from (e.g. RUB): ");
            String fromCurrency = scanner.next().toLowerCase();

            System.out.print("Enter the currency to convert to (e.g. USD): ");
            String toCurrency = scanner.next().toLowerCase();

            String url = "https://www.cbr.ru/scripts/XML_daily.asp?date_req=17/03/2023";
            URLConnection connection = new URL(url).openConnection();

            Scanner responseScanner = new Scanner(connection.getInputStream());
            String response = responseScanner.useDelimiter("\\A").next();

            double fromRate = 0;
            double toRate = 0;

            String[] lines = response.split("\n");
            for (String line : lines) {
                if (line.contains("<charcode>" + fromCurrency + "</charcode>")) {
                    fromRate = Double.parseDouble(line.split("<value>")[1].split("</value>")[0].replace(",", "."));
                } else if (line.contains("<charcode>" + toCurrency + "</charcode>")) {
                    toRate = Double.parseDouble(line.split("<value>")[1].split("</value>")[0].replace(",", "."));
                }
            }

            if (fromRate == 0 || toRate == 0) {
                System.out.println("Invalid currency codes.");
                return;
            }

            double convertedAmount = amount * (toRate / fromRate);

            System.out.printf("%.2f %s = %.2f %s", amount, fromCurrency.toUpperCase(), convertedAmount, toCurrency.toUpperCase());
        }
    }


