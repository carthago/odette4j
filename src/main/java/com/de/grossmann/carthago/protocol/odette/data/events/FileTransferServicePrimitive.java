package com.de.grossmann.carthago.protocol.odette.data.events;

/**
 * User Monitor Input Events:<br/>
 * F_DATA_RQ   F_CONNECT_RQ   F_START_FILE_RQ      F_CLOSE_FILE_RQ
 * F_EERP_RQ   F_CONNECT_RS   F_START_FILE_RS(+)   F_CLOSE_FILE_RS(+)
 * F_NERP_RQ   F_ABORT_RQ     F_START_FILE_RS(-)   F_CLOSE_FILE_RS(-)
 * F_CD_RQ     F_RELEASE_RQ   F_RTR_RS
 * <p/>
 * User Monitor Output Events:<br/>
 * F_DATA_IND  F_CONNECT_IND  F_START_FILE_IND     F_CLOSE_FILE_IND
 * F_EERP_IND  F_CONNECT_CF   F_START_FILE_CF(+)   F_CLOSE_FILE_CF(+)
 * F_CD_IND    F_ABORT_IND    F_START_FILE_CF(-)   F_CLOSE_FILE_CF(-)
 * F_NERP_IND  F_RELEASE_IND  F_DATA_CF            F_RTR_CF
 * <p/>
 */
public interface FileTransferServicePrimitive {
}
