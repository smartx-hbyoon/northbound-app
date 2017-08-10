package kr.ac.gist.fsteer.gui;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableSet;
import kr.ac.gist.fsteer.HostPair;
import kr.ac.gist.fsteer.SteerService;
import org.onosproject.net.DeviceId;
import org.onosproject.ui.RequestHandler;
import org.onosproject.ui.UiMessageHandler;
import org.onosproject.ui.table.TableModel;
import org.onosproject.ui.table.TableRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by netcsnuc on 5/7/17.
 */
public class AppUiMessageHandler extends UiMessageHandler {
    private static final String SAMPLE_CUSTOM_DATA_REQ = "sampleCustomDataRequest";
    private static final String SAMPLE_CUSTOM_DATA_RESP = "sampleCustomDataResponse";
    private static final String STEER = "sampleCustoms";

    private static final String ID = "id";
    private static final String SRC = "src";
    private static final String DST = "dst";
    private static final String CNT = "cnt";
    private final String[] COL_IDS = { ID, SRC, DST, CNT };
    private static final String NO_ROWS_MESSAGE = "No rules found";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private ConcurrentMap<DeviceId, ConcurrentMap<HostPair, Long>> map;


    @Override
    protected Collection<RequestHandler> createRequestHandlers() {
        return ImmutableSet.of(
                new SampleCustomDataRequestHandler()
        );
    }
    // handler for sample data requests
    private final class SampleCustomDataRequestHandler extends TableRequestHandler {

        private SampleCustomDataRequestHandler() {
            super(SAMPLE_CUSTOM_DATA_REQ,SAMPLE_CUSTOM_DATA_RESP,STEER);
        }

        @Override
        protected String[] getColumnIds() {
            return COL_IDS;
        }

        @Override
        protected String noRowsMessage(ObjectNode objectNode) {
            return NO_ROWS_MESSAGE;
        }

        @Override
        protected void populateTable(TableModel tm, ObjectNode payload) {
            map = get(SteerService.class).getMap();
            for (DeviceId devId : map.keySet()){
                map.get(devId).forEach((k,v)->populateRow(tm.addRow(),devId,k,v));
            }
            // iterate map and extract device id
            // for each device id need to further extract the host pair,
            // counter and add to TableModel...
            // hint: you can make use of pupulateRow method

        }
        private void populateRow(TableModel.Row row, DeviceId devId,
                                 HostPair hp, Long cnt) {
            // add each cell value, constructs a row value
            row.cell(ID,devId).cell(SRC,hp.getSrc()).cell(DST,hp.getDst()).cell(CNT,cnt);
        }
    }
}
