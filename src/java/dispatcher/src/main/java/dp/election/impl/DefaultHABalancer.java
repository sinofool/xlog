package dp.election.impl;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.zookeeper.KeeperException;

import xlog.proto.Xlog.ItemInfo;
import xlog.slice.DispatcherPrx;
import xlog.slice.DispatcherPrxHelper;

import com.renren.dp.xlog.config.Configuration;

import dp.election.HABalancerI;
import dp.zk.ZkConn;

public class DefaultHABalancer implements HABalancerI {

	private int replication;
	private int slotSize;
	private final ZkConn conn;
	private final String clusterPath;
	private Ice.Communicator ic;

	public DefaultHABalancer(ZkConn conn, String clusterPath, int slotSize) {
		replication = Configuration.getInt("slot.replication", 3);
		this.slotSize = slotSize;
		this.conn = conn;
		this.clusterPath = clusterPath;
		this.ic = Ice.Util.initialize();
	}

	@Override
	public boolean checkBalanceCondition(int slotID) throws KeeperException,
			InterruptedException, IOException {
		synchronized (this) {
			List<String> list = conn.get().getChildren(
					clusterPath + "/" + slotID, false);
			if (list == null || list.isEmpty()) {
				return false;
			}
			int size = list.size();
			list.removeAll(list);
			if (size < replication) {
				return false;
			}
			return true;
		}
	}

	@Override
  public void doBalance(int slotID) throws KeeperException, InterruptedException, IOException {
    synchronized (this) {
      //current slot znode count add maxcount,to make order at last
      int maxCount=10000;
      // key-slots children znode;value-znode repeat count
      Map<ItemInfo, Integer> slotZnodeMap = new HashMap<ItemInfo, Integer>();
      int localSoltZnodeCount = 0;
      List<String> children = null;
      int count;
      ItemInfo itemInfo = null;
      // to find all znodes of slots
      String path;
      for (int i = 0; i < slotSize; i++) {
        path = clusterPath + "/" + i;
        children = conn.get().getChildren(path, false);
        if (i == slotID) {
          localSoltZnodeCount=children.size();
          for (String znode : children) {
            itemInfo = convertToItemInfo(path + "/" + znode);
            if (slotZnodeMap.containsKey(itemInfo)) {
                  count = slotZnodeMap.get(itemInfo);
                  count += maxCount;
                  slotZnodeMap.put(itemInfo, count);
             } else {
               slotZnodeMap.put(itemInfo, maxCount);
             }
           }
        } else {
          for (String znode : children) {
            itemInfo = convertToItemInfo(path + "/" + znode);
            if (slotZnodeMap.containsKey(itemInfo)) {
              count = slotZnodeMap.get(itemInfo);
              count += 1;
              slotZnodeMap.put(itemInfo, count);
            } else {
            	slotZnodeMap.put(itemInfo, 1);
            }
          }
        }
      }
     
     Map.Entry<ItemInfo, Integer>[] slotZnodeMapEntryArr = getSortedHashtableByValue(slotZnodeMap);
     int len = slotZnodeMap.size();
     count = replication - localSoltZnodeCount;
     for (int i = 0;i < count; i++) {
    	 createZNode(slotID, slotZnodeMapEntryArr[i%len].getKey());
      }
    }
  }

	private static Map.Entry<ItemInfo, Integer>[] getSortedHashtableByValue(
			Map<ItemInfo, Integer> map) {
		Set<Map.Entry<ItemInfo, Integer>> set = map.entrySet();
		Map.Entry<ItemInfo, Integer>[] entries = (Map.Entry<ItemInfo, Integer>[]) set
				.toArray(new Map.Entry[set.size()]);
		Arrays.sort(entries, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				Integer key1 = Integer.valueOf(((Map.Entry) arg0).getValue()
						.toString());
				Integer key2 = Integer.valueOf(((Map.Entry) arg1).getValue()
						.toString());
				return key1.compareTo(key2);
			}
		});

		return entries;
	}

	private ItemInfo convertToItemInfo(String znode) {
		DataInputStream ds = null;
		ByteArrayInputStream bis = null;
		ItemInfo itemInfo = null;
		try {
			byte[] data = conn.get().getData(znode, false, null);
			itemInfo = ItemInfo.parseFrom(data);
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (ds != null) {
				try {
					ds.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return itemInfo;
	}

	private void createZNode(int slot, ItemInfo itemInfo) {
		try {
			DispatcherPrx prx = DispatcherPrxHelper.uncheckedCast(ic
					.stringToProxy(itemInfo.getLocation()));

			prx.createZNode(slot);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
