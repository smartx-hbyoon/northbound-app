package kr.ac.gist.fsteer;

import org.onosproject.net.HostId;

/**
 * Created by netcsnuc on 5/7/17.
 */
public class HostPair {
    private HostId src,dst;

    public HostPair(HostId src, HostId dst){
        this.src = src;
        this.dst = dst;
    }

    public HostId getSrc() {
        return src;
    }

    public HostId getDst() {
        return dst;
    }

    public void setDst(HostId dst) {
        this.dst = dst;
    }

    public void setSrc(HostId src) {
        this.src = src;
    }

    @Override
    public boolean equals(Object obj) {
        HostPair hp = (HostPair) obj;
        return hp.getSrc().mac().equals(src.mac())
                && hp.getDst().mac().equals(dst.mac());
    }

    @Override
    public int hashCode() {
        String ttl = src.mac().toString()+ "_"+ dst.mac().toString();
        return ttl.hashCode();
    }
}
