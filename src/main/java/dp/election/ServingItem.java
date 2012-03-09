package dp.election;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;

import xlog.proto.Xlog.ItemInfo;
import dp.zk.ZkConn;

public class ServingItem implements Comparable<ServingItem>, Watcher {
    private final ZkConn conn;
    private final String clusterpath;
    private final int pos;

    private ItemInfo item;
    private boolean avaliable = false;

    public ServingItem(ZkConn conn, String clusterpath, int pos) {
        this.conn = conn;
        this.clusterpath = clusterpath;
        this.pos = pos;
    }

    public synchronized void initialize() throws KeeperException, InterruptedException, IOException {
        try {
            byte[] data = conn.get().getData(clusterpath + "/" + pos, this, null);
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));
            ItemInfo.Builder itemBuilder = ItemInfo.newBuilder();
            itemBuilder.setType(in.readInt());
            itemBuilder.setLocation(in.readUTF());
            item = itemBuilder.build();
            this.notifyAll();
        } catch (KeeperException e) {
            if (e instanceof KeeperException.NoNodeException) {
                avaliable = false;
            } else {
                throw e;
            }
        }
    }

    public int getType() throws InterruptedException {
        synchronized (this) {
            if (!avaliable) {
                this.wait();
            }
        }
        return item.getType();
    }

    public String getLocation() throws InterruptedException {
        synchronized (this) {
            if (!avaliable) {
                this.wait();
            }
        }
        return item.getLocation();
    }

    @Override
    public int compareTo(ServingItem o) {
        return this.clusterpath.compareTo(o.clusterpath);
    }

    @Override
    public void process(WatchedEvent event) {
        EventType type = event.getType();
        switch (type) {
        case NodeCreated:
        case NodeDataChanged:
            try {
                initialize();
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
            break;
        case NodeDeleted:
            synchronized (this) {
                avaliable = false;
            }
            break;
        default:
            // TODO, this should never happened.
        }

    }

}
