package dp.election;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;

import xlog.proto.Xlog.ItemInfo;
import dp.zk.ZkConn;

public class HABalancer implements Watcher {
    public static final String WAIT_LOOP = "__hacluster_waiting_loop__";

    private final ZkConn conn;
    private final String parent;
    private final int size;

    private ItemInfo[] slots;
    private String[] slotNames;
    private List<ItemInfo> waits = new ArrayList<ItemInfo>();
    private Thread balancerThread = new Thread(new Runnable() {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000L);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }
    });

    public HABalancer(ZkConn conn, String parent, int size) {
        this.conn = conn;
        this.parent = parent;
        this.size = size;
    }

    public boolean initialize() throws KeeperException, InterruptedException, IOException {
        slots = new ItemInfo[size];
        slotNames = new String[size];
        for (int p = 0; p < size; ++p) {
            slotNames[p] = String.valueOf(p);
        }
        Arrays.sort(slotNames);

        initItems();

        balancerThread.setDaemon(true);
        balancerThread.start();
        return true;
    }

    private synchronized void balance() throws KeeperException, InterruptedException, IOException {
        System.out.println("TODO: Balancing..." + Arrays.toString(slots) + " with " + waits.size()
                + " idles.");
        if (waits.isEmpty()) {
            return;
        }

        int waitPos = 0;

        // 1 calculate unassigned slots.
        Map<Integer, String> servingMap = new HashMap<Integer, String>();
        for (int pos = 0; pos < this.size; ++pos) {
            if (slots[pos] == null) {
                ItemInfo item = waits.get((waitPos++) % waits.size());
                writeServingItem(pos, item);
                servingMap.put(pos, item.getLocation());
            } else {
                String s = slots[pos].getLocation();
                servingMap.put(pos, s);
            }
        }
        // 2 balance workloads
        Map<String, Integer> servingLoads = new HashMap<String, Integer>();
        String maxLoadLocation = null;
        int maxLoadPos = -1;
        int maxLoad = 2;
        for (Map.Entry<Integer, String> entry : servingMap.entrySet()) {
            String location = entry.getValue();
            int load = 1;
            if (servingLoads.containsKey(location)) {
                load = servingLoads.get(location);
            }
            servingLoads.put(location, load);
        }
    }

    private synchronized void initItems() throws KeeperException, InterruptedException, IOException {
        System.out.println("initItems");
        List<String> children = conn.get().getChildren(parent, this);
        waits.clear();
        for (String child : children) {
            System.out.println("HABalancer initItem: " + child);
            if (child.startsWith(WAIT_LOOP)) {
                waits.add(readItemInfo(child));
            }
            int pos = Arrays.binarySearch(slotNames, child);
            System.out.println(pos);
            if (pos >= 0 && pos < size) {
                int slot = Integer.valueOf(child);
                slots[slot] = readItemInfo(child);
            }
        }
    }

    private ItemInfo readItemInfo(String child) throws KeeperException, InterruptedException,
            IOException {
        byte[] data = conn.get().getData(this.parent + "/" + child, false, null);
        ItemInfo item = ItemInfo.parseFrom(data);
        return item;
    }

    private void writeServingItem(int pos, ItemInfo item) throws KeeperException,
            InterruptedException, IOException {
        conn.get().setData(this.parent + "/" + String.valueOf(pos), item.toByteArray(), -1);
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println(event.getType());
        try {
            initItems();
        } catch (KeeperException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
