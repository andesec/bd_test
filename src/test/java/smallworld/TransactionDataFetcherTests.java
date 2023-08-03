package smallworld;

import com.smallworld.TransactionDataFetcher;
import com.smallworld.data.Transaction;
import com.smallworld.datastore.TransactionDataStore;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class TransactionDataFetcherTests {

  TransactionDataFetcher tdf = new TransactionDataFetcher();

  @Test
  public void testGetTotalTransactionAmount() throws Exception {
    Assert.assertEquals(2889.17, tdf.getTotalTransactionAmount(), 0);
  }

  @Test
  public void testGetTotalTransactionAmountSentBy() throws Exception {
    Assert.assertEquals(678.06, tdf.getTotalTransactionAmountSentBy("Tom Shelby"), 0);
    Assert.assertEquals(666.0, tdf.getTotalTransactionAmountSentBy("Grace Burgess"), 0);
  }

  @Test
  public void testGetMaxTransactionAmount() throws Exception {
    Assert.assertEquals(985.0, tdf.getMaxTransactionAmount().getAsDouble(), 0);
  }

  @Test
  public void testCountUniqueClients() throws Exception {
    Assert.assertEquals(14L, tdf.countUniqueClients());
  }

  @Test
  public void testHasOpenComplianceIssues() throws Exception {
    //These clients have at least one compliance issue.
    Assert.assertTrue(tdf.hasOpenComplianceIssues("Tom Shelby"));
    Assert.assertTrue(tdf.hasOpenComplianceIssues("Arthur Shelby"));
    //These clients don't have any unsolved compliance issues.
    Assert.assertFalse(tdf.hasOpenComplianceIssues("Billy Kimber"));
    Assert.assertFalse(tdf.hasOpenComplianceIssues("Major Campbell"));
  }

  @Test
  public void testGetTransactionsByBeneficiaryName() throws Exception {
    Map<String, Transaction> transactionByBeneficiaryName = TransactionDataStore.getTransactions().stream()
        .distinct()
        .collect(Collectors.toMap(Transaction::getBeneficiaryFullName, transaction -> transaction));

    Assert.assertEquals(transactionByBeneficiaryName, tdf.getTransactionsByBeneficiaryName());
  }

  @Test
  public void testGetUnsolvedIssueIds() throws Exception {
    Assert.assertEquals(new HashSet<Integer>(Arrays.asList(1, 3, 15, 54, 99)), tdf.getUnsolvedIssueIds());
  }

  @Test
  public void testGetAllSolvedIssueMessages() throws Exception {
    List<String> solvedIssueMessages = new ArrayList<>(Arrays.asList("Never gonna give you up", "Never gonna let you down", "Never gonna run around and desert you"));
    Assert.assertEquals(solvedIssueMessages, tdf.getAllSolvedIssueMessages());
  }

  @Test
  public void testGetTop3TransactionsByAmount() throws Exception {
    List<Transaction> top3Transactions = TransactionDataStore.getTransactions().stream()
        .distinct()
        .sorted(Comparator.comparing(Transaction::getAmount).reversed())
        .limit(3)
        .collect(Collectors.toList());

    Assert.assertEquals(top3Transactions, tdf.getTop3TransactionsByAmount());
  }

  @Test
  public void testGetTopSender() throws Exception {
    Assert.assertEquals("Arthur Shelby", tdf.getTopSender());
  }

}
