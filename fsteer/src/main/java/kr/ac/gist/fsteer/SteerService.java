package kr.ac.gist.fsteer;

import org.onosproject.net.DeviceId;

import java.util.concurrent.ConcurrentMap;

/**
 * Created by netcsnuc on 5/7/17.
 */
public interface SteerService {
    public ConcurrentMap<DeviceId, ConcurrentMap<HostPair, Long>> getMap();
}
