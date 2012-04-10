package dp.election;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

public interface HABalancerI {

  /**
   * check whether balance or not
   * @return true - already balance;flase - need to balance
   */
  public boolean checkBalanceCondition(int slotID) throws KeeperException, InterruptedException, IOException;
  
  public void doBalance(int slotID) throws KeeperException, InterruptedException, IOException;
}
