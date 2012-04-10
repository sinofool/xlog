package dp.election.impl;

import java.util.List;
import java.util.Random;

import dp.election.NodeChooseAlgorithm;
import dp.election.ServingItem;

public class RandomChooseAlgorithm implements NodeChooseAlgorithm{

  @Override
  public ServingItem doChoose(List<ServingItem> list) {
    if(list==null || list.isEmpty()){
      return null;
    }
    Random random=new Random();
    int num=random.nextInt(list.size());
    
    return list.get(num);
  }

}
