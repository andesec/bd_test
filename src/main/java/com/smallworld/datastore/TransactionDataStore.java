package com.smallworld.datastore;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smallworld.data.Transaction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionDataStore {

  private static final String JSON_FILEPATH = System.getProperty("user.dir") + "/transactions.json";
  private static List<Transaction> transactions;

  public static List<Transaction> getTransactions() throws Exception {
    try {
      if (transactions == null) {
        transactions = new Gson().fromJson(getJsonString(), new TypeToken<List<Transaction>>() {}.getType());
      }
    } catch (Exception e) {
      throw new Exception("An error occurred. Could not retrieve transactions", e);
    }

    return transactions;
  }

  private static String getJsonString() throws IOException {
    return Files.readString(Path.of(JSON_FILEPATH));
  }
}
